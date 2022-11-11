package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.adapters.ListViewComedoresAdapter;
import com.example.comedores.adapters.ListViewReportesAdapter;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Reporte;
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
    private Context context;

    public DataComedores(Context context, List<Comedor> listaComedores, ListView ComedoresLV) {
        this.usuario = usuario;
        this.context = context;
        this.lvComedores = ComedoresLV;
        this.comedores = listaComedores;
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

        String query="SELECT * " +
                "FROM comedores ";

        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            comedores=new ArrayList<Comedor>();
            ResultSet rs= pst.executeQuery();


            while(rs.next()){
                Comedor c= new Comedor();

                c.setId(rs.getLong("id"));
                c.setRenacom(rs.getLong("renacom"));
                c.setNombre(rs.getString("nombre"));
                c.setDireccion(rs.getString("direccion"));
                c.setLocalidad(rs.getString("localidad"));
                c.setProvincia(rs.getString("provincia"));
                c.setTelefono(rs.getString("telefono"));
                c.setNombreResponsable(rs.getString("nombre_responsable"));
                c.setApellidoResponsable(rs.getString("apellido_responsable"));
                c.setEstado(new Estado(rs.getInt("estado_id"),null));
                c.setIdResponsable(rs.getLong("usuario_id"));

                comedores.add(c);
            }
            if (comedores.size() > 0) {
                mensaje = "Reportes cargados";
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

        if (mensaje.compareTo("") != 0)
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();

        if (mensaje.compareTo("Reportes cargados") == 0) {
            ListViewComedoresAdapter adapter = new ListViewComedoresAdapter(context, R.layout.item_row_comedores, comedores);
            lvComedores.setAdapter(adapter);

        }
    }


}
