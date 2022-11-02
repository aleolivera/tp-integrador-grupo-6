package com.example.comedores.conexion;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.comedores.PrincipalAdminComedor;
import com.example.comedores.PrincipalSupervisor;
import com.example.comedores.PrincipalUsuarioFinal;
import com.example.comedores.entidades.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataLogin extends AsyncTask<String,Void,String> {
    private String mensaje;
    private Usuario usuario;
    private Context context;

    public DataLogin(Usuario usuario, Context context) {
        this.usuario= usuario;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
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

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        if(usuario.getId()!=0){
            redirigir();
        }
    }
    private void redirigir() {
        Intent intent;
        switch(usuario.getTipo()){
            case 1:
                intent= new Intent(context, PrincipalUsuarioFinal.class);
                break;
            case 2:
                intent= new Intent(context, PrincipalAdminComedor.class);
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
