package com.example.comedores.conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.Comedores;
import com.example.comedores.R;
import com.example.comedores.adapters.ListViewComedoresAdapter;
import com.example.comedores.adapters.ListViewReportesAdapter;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;

import java.security.AccessControlContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataComedores extends AsyncTask<String,Void,String> {

    private Usuario usuario;
    private Context context;
    private ListView lvComedores;
    private Comedor comedor;
    private List<Comedor> comedores;
    private String mensaje="";

    public DataComedores(Context context, List<Comedor> listaComedores, ListView ComedoresLV) {
        this.usuario = usuario;
        this.context = context;
        this.lvComedores = ComedoresLV;
        this.comedores = listaComedores;
    }

    public DataComedores(Comedor comedor, Context context) {
        this.comedor = comedor;
        this.context = context;
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
                agregarComedor();


                break;
            case "buscarComedorPorIdUsuario":
                buscarComedorPorIdUsuario();
                cargarEstadoComedor();
                break;
            default:
                break;
        }
        return mensaje;
    }

    private void cargarEstadoComedor() {
        String query="select * from estados_comedor where id=?";

        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setLong(1,comedor.getEstado().getId());
            ResultSet rs= pst.executeQuery();
            Estado e= new Estado();

            if(rs.next()){
                e.setId(rs.getInt(1));
                e.setDescripcion(rs.getString(2));
                comedor.setEstado(e);
            }
            else{
                mensaje="Estado incorrecto";
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar El Estado";
        }
    }

    private void buscarComedorPorIdUsuario() {
        String query="select * from comedores where usuario_id=?";

        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setLong(1,comedor.getIdResponsable());
            ResultSet rs= pst.executeQuery();
            Comedor c= new Comedor();

            if(rs.next()){
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
            }
            else{
                mensaje="Usuario no posee  comedor";
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar El comedor";
        }
    }

    private void listarComedores() {

        String query="SELECT * " +
                "FROM comedores "
                 + "WHERE estado_id = 2";

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

    private void agregarComedor() {

        if(verificarComedorExistente()) {

        mensaje  = "";
        String query = "INSERT INTO `comedores` (`usuario_id`, `renacom`, `nombre`, `direccion`, `localidad`, `provincia`, `telefono`, `nombre_responsable`, `apellido_responsable`, `estado_id`) \n" +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, (int) comedor.getIdResponsable());
            pst.setInt(2, (int) comedor.getRenacom());
            pst.setString(3, comedor.getNombre());
            pst.setString(4, comedor.getDireccion());
            pst.setString(5, comedor.getLocalidad());
            pst.setString(6, comedor.getProvincia());
            pst.setString(7, comedor.getTelefono());
            pst.setString(8, comedor.getNombreResponsable());
            pst.setString(9, comedor.getApellidoResponsable());
            pst.setInt(10, 1);

            int filas = pst.executeUpdate();
            if(filas>0) {
                agregarSolicitud();
            } else {
                mensaje= "Ocurrio un error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            mensaje = e.getMessage();
        }
        } else {
            mensaje = "Ya se encuentra un comedor con este usuario";
        }
    }

    private void agregarSolicitud() {
        int id_comedor = obtenerIdComedor();
        mensaje  = "";
        String query = "INSERT INTO `solicitudes` (`comedor_id`, `fecha_alta`, `estado`) \n" +
                "VALUES (?,?,?)";

        java.util.Date fecha_alta = Calendar.getInstance().getTime();

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_comedor);
            pst.setDate(2, new java.sql.Date(fecha_alta.getTime()));
            pst.setInt(3,0);


            int filas = pst.executeUpdate();
            if(filas>0) {
                mensaje= "Solicitud de comedor enviado con exito";
            } else {
                mensaje= "Ocurrio un error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            mensaje = e.getMessage();
        }
    }

    private int obtenerIdComedor() {
        String query = "SELECT id FROM comedores WHERE usuario_id = ? ";
        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setInt(1, (int) comedor.getIdResponsable());
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
            mensaje="Error al encontrar usuario";
        }
        return 0;
    }

    private boolean verificarComedorExistente() {
        String query = "SELECT id FROM comedores WHERE usuario_id = ? ";
        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setInt(1, (int) comedor.getIdResponsable());
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            mensaje="Error al buscar comedor";
        }
        return true;
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
            pst.setLong(1, comedor.getId());
            ResultSet rs = pst.executeQuery();
            comedor.setListaNecesidades(new ArrayList<Necesidad>());

            while (rs.next()) {
                Necesidad n = new Necesidad(
                        rs.getLong(1),
                        rs.getString(6),
                        new Tipo(rs.getInt(2), rs.getString(3)),
                        new Estado(rs.getInt(4), rs.getString(5)),
                        rs.getInt(7)
                );
                comedor.getListaNecesidades().add(n);
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al cargar las necesidades";
        }
    }

    @Override
    protected void onPostExecute(String Response) {



        if (mensaje.compareTo("Reportes cargados") == 0) {
            ListViewComedoresAdapter adapter = new ListViewComedoresAdapter(context, R.layout.item_row_comedores, comedores);
            lvComedores.setAdapter(adapter);

        }
    }


}
