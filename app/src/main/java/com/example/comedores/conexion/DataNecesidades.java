package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataNecesidades extends AsyncTask<String, Void, String> {

    private Necesidad necesidad;
    private int idComedor;
    private String mensaje="";
    private Context context;

    public DataNecesidades(Context context, int idComedor, Necesidad necesidad) {
        this.context = context;
        this.necesidad = necesidad;
        this.idComedor = idComedor;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (strings[0]) {
            case "agregarNecesidad":
                agregarNecesidad();
                break;
            default:
                break;
        }
        return mensaje;
    }

    private void agregarNecesidad() {
        String query = "INSERT INTO `necesidades` (`tipo_id`, `estado_id`, `descripcion`, `prioridad`) \n" +
                "VALUES (?,?,?,?)";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1,necesidad.getTipo().getId());
            pst.setInt(2,1);
            pst.setString(3, necesidad.getDescripcion());
            pst.setInt(4, necesidad.getPrioridad());

            int filas = pst.executeUpdate();
            int idNecesidad = 0;
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                idNecesidad = rs.getInt(1);
            }
            if(filas>0) {
                necesidad.setId(idNecesidad);
                agregarRelacion();
            } else {
                mensaje = "Ocurrio un error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al intentar agregar";
        }
    }

    private void agregarRelacion() {
        String query = "INSERT INTO `comedores_x_necesidades` (`comedor_id`, `necesidad_id`) \n" +
                "VALUES (?,?)";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idComedor);
            pst.setInt(2, (int) necesidad.getId());

            int filas = pst.executeUpdate();
            if(filas>0) {
                mensaje = "Necesidad agregado con exito";
            } else {
                mensaje = "Ocurrio un error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            mensaje = e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(mensaje.compareTo("")!=0){
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        }
    }
}
