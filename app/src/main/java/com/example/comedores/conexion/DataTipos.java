package com.example.comedores.conexion;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.example.comedores.entidades.Tipo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataTipos extends AsyncTask<String, Void, String> {

    private String mensaje;
    private Context context;

    private ArrayList<Tipo> TiposList;
    Spinner spnTipos;

    public DataTipos(Context context) {
        this.context = context;
    }

    public DataTipos(Spinner spn, Context context) {
        this.context = context;
        this.spnTipos = spn;
        this.TiposList = new ArrayList<Tipo>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        String Tabla = "";

        //elegir el tipo y cambiar la tabla para la consulta
        switch (strings[0]) {
            case "1"://Usuarios
            {

            }
            break;

            case "2": //Reportes
            {

                Tabla = "tipos_reporte";

            }
            break;
        }

        response = TiposObtener(Tabla);

        return response;

    }

    @Override
    protected void onPostExecute(String Response) {
        if (Response == "") {

            ArrayAdapter<Tipo> arrayAdapterTipo = new ArrayAdapter<Tipo>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, TiposList);
            spnTipos.setAdapter(arrayAdapterTipo);
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
            while (resultSet.next()) {
                Tipo Rtipo = new Tipo();
                Rtipo.setId(resultSet.getInt("id"));
                Rtipo.setDescripcion(resultSet.getString("descripcion"));
                TiposList.add(Rtipo);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response = "Error de conexion";
        }

        return response;
    }

}
