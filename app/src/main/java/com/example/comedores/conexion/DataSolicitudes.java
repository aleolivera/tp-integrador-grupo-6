package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.adapters.ListViewSolicitudesAdapter;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Solicitud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataSolicitudes extends AsyncTask<String,Void,String> {
    private Context context;
    private List<Solicitud> solicitudes;
    private ListView lvSolicitudes;
    private String mensaje="";
    private Solicitud solicitud;
    private Comedor comedor;

    public DataSolicitudes(Context context, List<Solicitud> solicitudes, ListView lvSolicitudes) {
        this.context = context;
        this.solicitudes = solicitudes;
        this.lvSolicitudes = lvSolicitudes;
    }

    public DataSolicitudes(Context context, Solicitud solicitud, Comedor comedor) {
        this.context = context;
        this.solicitud = solicitud;
        this.comedor = comedor;
    }

    @Override
    protected String doInBackground(String... strings) {


        switch(strings[0]){
            case "listarSolicitudes":
                listarSolicitudes(strings[1]);
                break;

            case "modificarSolicitud":
                modificarSolicitud();
                if(mensaje=="")
                    modificarComedor();
                break;

            default:
                break;
        }
        return mensaje;
    }

    private void listarSolicitudes(String estado) {

        String query="SELECT s.id,s.fecha_alta,s.estado,c.id,c.renacom,c.nombre,c.direccion, "+
                "c.localidad,c.provincia,c.telefono,c.nombre_responsable, "+
                "c.apellido_responsable,c.estado_id,c.usuario_id,s.supervisor_id "+
                "FROM solicitudes s "+
                "INNER JOIN comedores c ON c.id=s.comedor_id "+
                "WHERE s.estado= ?";

        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setBoolean(1,Boolean.parseBoolean(estado));
            solicitudes=new ArrayList<Solicitud>();
            ResultSet rs= pst.executeQuery();


            while(rs.next()){
                Solicitud s= new Solicitud();
                Comedor c= new Comedor();

                s.setId(rs.getLong(1));
                s.setFechaAlta(LocalDate.parse(rs.getString(2)));
                s.setEstado(rs.getBoolean(3));
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
                s.setIdSupervisor(rs.getLong(15));

                s.setComedor(c);
                solicitudes.add(s);
            }
            if(solicitudes.size()>0)
                mensaje="solicitudes cargadas";
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar las solicitudes";
        }
    }

    private void modificarSolicitud() {
        String query="UPDATE solicitudes s SET s.estado= ? ,  s.supervisor_id= ? WHERE s.id= ?";
        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setBoolean(1,solicitud.isEstado());
            pst.setLong(2,solicitud.getIdSupervisor());
            pst.setLong(3,solicitud.getId());
            int filas= pst.executeUpdate();

            if(filas<1)
                mensaje="No se pudo modificar la solicitud";
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error de conexion al modificar la solicitud";
        }
    }

    private void modificarComedor() {
        String query="UPDATE comedores c SET c.estado_id= ? WHERE c.id= ?";
        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setInt(1,solicitud.getComedor().getEstado().getId());
            pst.setLong(2,solicitud.getComedor().getId());
            int filas= pst.executeUpdate();

            if(filas<1)
                mensaje="No se pudo modificar el comedor";
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error de conexion al modificar el comedor";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(mensaje.compareTo("")!=0)
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();

        if(mensaje.compareTo("solicitudes cargadas")==0){
            ListViewSolicitudesAdapter adapter= new ListViewSolicitudesAdapter(context, R.layout.item_row_solicitudes,solicitudes);
            lvSolicitudes.setAdapter(adapter);
        }

    }
}
