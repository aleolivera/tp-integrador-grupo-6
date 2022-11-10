package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.comedores.R;
import com.example.comedores.adapters.ListViewComedoresAdapter;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Usuario;

import java.security.AccessControlContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataComedores extends AsyncTask<String,Void,String> {

    private Usuario usuario;
    private ListView lvComedores;
    private List<Comedor> comedores;
    private String mensaje="";

    public DataComedores(List<Comedor> comedores, ListView ComedorLV) {
        this.usuario = usuario;
        this.lvComedores = ComedorLV;
        this.comedores = comedores;
    }

    public List<Comedor> getComedores(){
        return comedores;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (strings[0]) {
            case "listarComedores":
                listarComedores();
                break;

            case "AltaComedores":
                //modificarSolicitud();


                break;

            default:
                break;
        }
        return mensaje;
    }

    private void listarComedores() {

        String query="SELECT c.id,c.renacom,c.nombre,c.direccion, "+
                "c.localidad,c.provincia,c.telefono,c.nombre_responsable, "+
                "c.apellido_responsable,c.estado_id,c.usuario_id"+
                "FROM comedores c ";

        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            comedores=new ArrayList<Comedor>();
            ResultSet rs= pst.executeQuery();


            while(rs.next()){
                Comedor c= new Comedor();

                c.setId(rs.getLong(4));
                c.setRenacom(rs.getLong(5));
                c.setNombre(rs.getString(6));
                c.setDireccion(rs.getString(7));
                c.setLocalidad(rs.getString(8));
                c.setProvincia(rs.getString(9));
                c.setTelefono(rs.getString(10));
                c.setNombreResponsable(rs.getString(11));
                c.setApellidoResponsable(rs.getString(12));
                c.setEstado(new Estado(rs.getInt(13),null));
                c.setIdResponsable(rs.getLong(14));

                comedores.add(c);
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar las solicitudes";
        }
    }

    @Override
    protected void onPostExecute(String Response) {

            ListViewComedoresAdapter adapter = new ListViewComedoresAdapter(null, R.layout.item_row_reportes, comedores);
            lvComedores.setAdapter(adapter);
    }


}
