package com.example.comedores.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Tipo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataEstados extends AsyncTask<String, Void, String> {

    private String mensaje;
    private Context context;

    private ArrayList<Estado> EstadoList;
    Spinner spnEstado;

    public DataEstados(Context context) {
        this.context = context;
    }

    public DataEstados(Spinner spn, Context context) {
        this.context = context;
        this.spnEstado = spn;
        this.EstadoList = new ArrayList<Estado>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        String Tabla = "";

        //elegir el Estado y cambiar la tabla para la consulta
        switch (strings[0]) {
            case "1"://Usuarios
            {

            }
            break;

            case "2": //Reportes
            {

                Tabla = "estados_reporte";

            }
            break;
        }

        response = TiposObtener(Tabla);

        return response;

    }

    @Override
    protected void onPostExecute(String Response) {
        if (Response == "") {

            ArrayAdapter<Estado> arrayAdapterTipo = new ArrayAdapter<Estado>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, EstadoList);
            spnEstado.setAdapter(arrayAdapterTipo);
        }
    }

    private String TiposObtener(String Tabla) {
        String response = "";

        String query = "select id, descripcion from " + Tabla;


        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Estado vacioestado = new Estado();
            vacioestado.setId(0);
            vacioestado.setDescripcion("Todos");
            EstadoList.add(vacioestado);

            while (resultSet.next()) {
                Estado estado = new Estado();
                estado.setId(resultSet.getInt("id"));
                estado.setDescripcion(resultSet.getString("descripcion"));
                EstadoList.add(estado);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response = "Error al Cargar Estados" + Tabla;
        }

        return response;
    }

}