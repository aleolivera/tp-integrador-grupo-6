package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.adapters.ListViewComedoresAdapter;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataBuscarComedor extends AsyncTask<String,Void,String> {
    private Context context;
    private List<Comedor>listaComedor;
    private ListView lvComedores;
    String mensaje="";

    public DataBuscarComedor(Context context, List<Comedor> listaComedor, ListView lvComedores) {
        this.context = context;
        this.listaComedor = listaComedor;
        this.lvComedores = lvComedores;
    }

    @Override
    protected String doInBackground(String... strings) {
        buscarComedor(strings[0]);
        return mensaje;
    }

    private void buscarComedor(String buscar){
        listaComedor= new ArrayList<Comedor>();
        String query="select c.id,c.estado_id, e.descripcion,c.usuario_id,c.renacom,"+
                "c.nombre,c.direccion,c.localidad,c.provincia,c.telefono,c.nombre_responsable,"+
                "c.apellido_responsable "+
                "from comedores c inner join estados_comedor e on e.id=c.estado_id "+
                "where c.nombre like '%"+buscar+"%' or c.localidad like '%"+buscar+"%'";
        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            ResultSet rs= pst.executeQuery();

            while(rs.next()){
                Comedor comedor= new Comedor();
                comedor.setId(rs.getLong(1));
                comedor.setEstado(new Estado(rs.getInt(2),rs.getString(3)));
                comedor.setIdResponsable(rs.getLong(4));
                comedor.setRenacom(rs.getLong(5));
                comedor.setNombre(rs.getString(6));
                comedor.setDireccion(rs.getString(7));
                comedor.setLocalidad(rs.getString(8));
                comedor.setProvincia(rs.getString(9));
                comedor.setTelefono(rs.getString(10));
                comedor.setNombreResponsable(rs.getString(11));
                comedor.setApellidoResponsable(rs.getString(12));

                listaComedor.add(comedor);
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar comedor";
        }
    }
    @Override
    protected void onPostExecute(String s) {
        if(listaComedor.size()<1){
            Toast.makeText(context, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
        }
        ListViewComedoresAdapter adapter = new ListViewComedoresAdapter(context, R.layout.item_row_comedores, listaComedor);
        lvComedores.setAdapter(adapter);
    }

}
