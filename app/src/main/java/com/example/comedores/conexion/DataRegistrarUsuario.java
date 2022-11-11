package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.os.DropBoxManager;
import android.widget.Toast;

import com.example.comedores.entidades.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataRegistrarUsuario extends AsyncTask<String, Void, String> {
    private Context context;
    private Usuario usuario;

    public DataRegistrarUsuario(Context context,Usuario usuario){
        this.context= context;
        this.usuario=usuario;
    }
    @Override
    protected String doInBackground(String... strings) {
        String response="";
        String query="insert into usuarios(nombre, apellido,email,password,tipo_id,dni,direccion,localidad,provincia,telefono,estado)\n" +
                "values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Class.forName(DataDB.DRIVER);
            Connection con= DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER,DataDB.PASS);

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getApellido());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getPassword());
            pst.setInt(5, usuario.getTipo());
            pst.setString(6, usuario.getDni());
            pst.setString(7, usuario.getDireccion());
            pst.setString(8, usuario.getLocalidad());
            pst.setString(9, usuario.getProvincia());
            pst.setString(10, usuario.getTelefono());
            pst.setBoolean(11, usuario.isEstado());
            int filas= pst.executeUpdate();
            if(filas>0)
                response="Usuario Registrado con Exito";
            else
                response="No se pudo registrar el usuario";
        }
        catch (Exception e) {
            e.printStackTrace();
            response=e.getMessage();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(this.context, response, Toast.LENGTH_SHORT).show();
    }
}
