package com.example.comedores.conexion;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.comedores.PrincipalComedorAdmin;
import com.example.comedores.PrincipalSupervisor;
import com.example.comedores.PrincipalUsuario;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DataLogin extends AsyncTask<String,Void,String> {

    private Usuario usuario;
    private Context context;

    public DataLogin(Usuario usuario, Context context) {
        this.usuario= usuario;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response="";
        response=cargarUsuario(response);
        if(usuario.getTipo()==2)
            cargarComedor(response);
        if(usuario.getComedor()!=null)
            cargarNecesidades(response);
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        if(usuario.getId()!=0){
            redirigir();
        }
    }
    private String cargarUsuario(String mensaje){
        String query="SELECT * FROM usuarios WHERE email = ? and password=?";
        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,usuario.getEmail());
            pst.setString(2,usuario.getPassword());
            ResultSet rs= pst.executeQuery();

            if(rs.next()){
                usuario.setId(rs.getLong(1));
                usuario.setEmail(rs.getString(2));
                usuario.setPassword(rs.getString(3));
                usuario.setTipo(rs.getInt(4));
                usuario.setNombre(rs.getString(5));
                usuario.setApellido(rs.getString(6));
                usuario.setDni(rs.getString(7));
                usuario.setDireccion(rs.getString(8));
                usuario.setLocalidad(rs.getString(9));
                usuario.setProvincia(rs.getString(10));
                usuario.setTelefono(rs.getString(11));
                usuario.setEstado(rs.getBoolean(12));
                mensaje="Bienvenido/a "+ usuario.getNombre() +" "+usuario.getApellido();
            }
            else
                mensaje="Correo o password incorrectos";
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="No se pudo efectuar la conexion";
        }
        finally {
            return mensaje;
        }
    }

    private String cargarComedor(String mensaje){
        String query=
                "SELECT c.id,c.usuario_id,c.renacom,c.nombre,c.direccion,c.localidad,c.provincia,"+
                "c.telefono,c.nombre_responsable,c.apellido_responsable,c.estado_id,e.descripcion "+
                "FROM comedores c "+
                "INNER JOIN estados_comedor e on e.id=c.estado_id "+
                "WHERE usuario_id=?";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setLong(1,usuario.getId());
            ResultSet rs= pst.executeQuery();

            if(rs.next()){
                usuario.setComedor(new Comedor());
                usuario.getComedor().setEstado(new Estado());
                usuario.getComedor().setId(rs.getLong(1));
                usuario.getComedor().setIdResponsable(rs.getLong(2));
                usuario.getComedor().setRenacom(rs.getLong(3));
                usuario.getComedor().setNombre(rs.getString(4));
                usuario.getComedor().setDireccion(rs.getString(5));
                usuario.getComedor().setLocalidad(rs.getString(6));
                usuario.getComedor().setProvincia(rs.getString(7));
                usuario.getComedor().setTelefono(rs.getString(8));
                usuario.getComedor().setNombreResponsable(rs.getString(9));
                usuario.getComedor().setApellidoResponsable(rs.getString(10));
                usuario.getComedor().getEstado().setId(rs.getInt(11));
                usuario.getComedor().getEstado().setDescripcion(rs.getString(12));
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar el comedor";
        }
        finally {
            return mensaje;
        }
    }
    private String cargarNecesidades(String mensaje){
        String query=
                "SELECT n.id,n.tipo_id,t.descripcion,n.estado_id,e.descripcion,n.descripcion,n.prioridad FROM necesidades n "+
                "INNER JOIN comedores_x_necesidades cxn ON n.id=cxn.necesidad_id "+
                "INNER JOIN tipos_necesidad t ON t.id=n.tipo_id "+
                "INNER JOIN estados_necesidad e ON e.id=n.estado_id "+
                "WHERE comedor_id=?" ;

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setLong(1,usuario.getComedor().getId());
            ResultSet rs= pst.executeQuery();
            usuario.getComedor().setListaNecesidades(new ArrayList<Necesidad>());

            while(rs.next()){
                Necesidad n= new Necesidad(
                        rs.getLong(1),
                        rs.getString(6),
                        new Tipo(rs.getInt(2),rs.getString(3)),
                        new Estado(rs.getInt(4),rs.getString(5)),
                        rs.getInt(7)
                );
                usuario.getComedor().getListaNecesidades().add(n);
            }
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar las necesidades";
        }
        finally {
            return mensaje;
        }
    }

    private void redirigir() {
        Intent intent;
        switch(usuario.getTipo()){
            case 1:
                intent= new Intent(context, PrincipalUsuario.class);
                break;
            case 2:
                intent= new Intent(context, PrincipalComedorAdmin.class);
                break;
            default:
                intent= new Intent(context,PrincipalSupervisor.class);
                break;
        }
        intent.putExtra("usuario",usuario);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
