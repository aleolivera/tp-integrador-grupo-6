package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.adapters.ListViewMisNecesidadesAdapter;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Tipo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataMisNecesidadesDialog extends AsyncTask<String,Void,String> {
    private Context context;
    private ListView lvNecesidades;
    private List<Necesidad> listaNecesidades;
    private long idNecesidad;
    private long idComedor;

    public DataMisNecesidadesDialog(Context context, ListView lvNecesidades, long idNecesidad,long idComedor) {
        this.context = context;
        this.lvNecesidades = lvNecesidades;
        this.idNecesidad = idNecesidad;
        this.idComedor=idComedor;
    }

    @Override
    protected String doInBackground(String... strings) {
        String mensaje="";
        if(eliminarComedorXNecesidad())
            mensaje=eliminarNecesidad(mensaje);
        //cargarNecesidades();
        //cargarListView();
        return mensaje;
    }

    private String eliminarNecesidad(String mensaje) {
        String query = "DELETE FROM necesidades " +
                "WHERE id=?";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query);

            pst.setLong(1,idNecesidad);
            int filas = pst.executeUpdate();

            mensaje=(filas>0)?"":"No se pudo eliminar la necesidad";

        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al intentar eliminar Necesidad";
        }
        return mensaje;
    }
    private boolean eliminarComedorXNecesidad(){
        String query = "DELETE FROM comedores_x_necesidades" +
                " WHERE necesidad_id=?";
        boolean response=false;

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query);

            pst.setLong(1,idNecesidad);
            int filas = pst.executeUpdate();

            response=(filas>0)?true:false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void cargarNecesidades() {
        String query =
                "SELECT n.id,n.tipo_id,t.descripcion,n.estado_id,e.descripcion,n.descripcion,n.prioridad FROM necesidades n " +
                        "INNER JOIN comedores_x_necesidades cxn ON n.id=cxn.necesidad_id " +
                        "INNER JOIN tipos_necesidad t ON t.id=n.tipo_id " +
                        "INNER JOIN estados_necesidad e ON e.id=n.estado_id " +
                        "WHERE comedor_id=?";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, idComedor);
            listaNecesidades = new ArrayList<Necesidad>();
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Necesidad n = new Necesidad(
                        rs.getLong(1),
                        rs.getString(6),
                        new Tipo(rs.getInt(2), rs.getString(3)),
                        new Estado(rs.getInt(4), rs.getString(5)),
                        rs.getInt(7)
                );
                listaNecesidades.add(n);
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarListView(){
        ListViewMisNecesidadesAdapter adapter = new ListViewMisNecesidadesAdapter(context, R.layout.item_row_mis_necesidades, listaNecesidades);
        lvNecesidades.setAdapter(adapter);
    }

    @Override
    protected void onPostExecute(String mensaje) {
        if(mensaje.compareTo("")==0){
            mensaje="Necesidad Eliminada";
        }
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }
}
