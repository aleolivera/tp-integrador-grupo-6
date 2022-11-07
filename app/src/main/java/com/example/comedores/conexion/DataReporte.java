package com.example.comedores.conexion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataReporte extends AsyncTask<String, Void, String> {

    private String mensaje;
    private Usuario usuario;
    private Context context;

    private ListView ReportesLV;
    private ArrayList<Reporte> listaReportes;

    public DataReporte(Usuario usuario, Context context) {
        this.usuario = usuario;
        this.context = context;
    }


    public DataReporte(Usuario usuario, Context context, ListView ReportesLV) {
        this.usuario = usuario;
        this.context = context;
        this.ReportesLV = ReportesLV;
        this.listaReportes = new ArrayList<Reporte>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... strings) {
        String response = "";

        String query = "select r.id, r.fecha_Alta, tr.id as TipoId, tr.descripcion as Tipo, er.id as EstadoId, er.descripcion as Estado, r.descripcion,r.respuesta, IFNULL(r.reportado_id,0) as IdReportado, \n" +
                "\tu.id as usuarioId, u.email , u.nombre , u.apellido\n" +
                "from \n" +
                "reportes r\n" +
                "  inner join tipos_reporte tr on tr.id = r.tipo_Id \n" +
                "  inner join estados_reporte er on er.id = r.estado_Id\n" +
                "  inner join usuarios_x_reportes uxr on uxr.reporte_id  = r.id \n" +
                "  inner join usuarios u on u.id = uxr.usuario_id \n" +
                "\n" +
                "where \n" +
                "\t(u.id = " + strings[0] + " or  " + strings[0] + " = 0)\n" +
                "\tand (r.Id = " + strings[1] + " or  " + strings[1] + " = 0)\n" +
                "\tand (er.Id = " + strings[2] + " or " + strings[2] + " = 0)\n" +
                "\tand (tr.id = " + strings[3] + " or " + strings[3] + " = 0)";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {

                //Cargo los datos devueltos
                int id = resultSet.getInt("id");

                Date fecha_Alta = resultSet.getDate("fecha_Alta");

                //LocalDate.parse(FechaAltaString, formatter);

                String DescripcionReporte = resultSet.getString("descripcion");

                Tipo TipoRep = new Tipo();
                TipoRep.setId(resultSet.getInt("TipoId"));
                TipoRep.setDescripcion(resultSet.getString("Tipo"));

                Estado EstadoRep = new Estado();
                EstadoRep.setId(resultSet.getInt("EstadoId"));
                EstadoRep.setDescripcion(resultSet.getString("Estado"));

                Usuario UsuarioAlta = new Usuario();
                UsuarioAlta.setId(resultSet.getInt("Id"));
                UsuarioAlta.setNombre(resultSet.getString("Nombre"));
                UsuarioAlta.setApellido(resultSet.getString("Apellido"));
                UsuarioAlta.setEmail(resultSet.getString("Email"));

                int IdReportado = resultSet.getInt("IdReportado");
                String Respuesta = resultSet.getString("Respuesta");

                Reporte reporte = new Reporte(id, UsuarioAlta, TipoRep, fecha_Alta, DescripcionReporte, IdReportado, Respuesta, EstadoRep);

                //Busco si lo reportado es un 1 Aplicacion, 2 usuario , 3 Necesidad

                Usuario UsaurioReportado = new Usuario();
                Necesidad NecesidadReportada = new Necesidad();

                switch (TipoRep.getId()) {
                    //Aplicacion, no ahcer nada
                    case 0: {

                    }
                    break;
                    //usuario Buscar Usaurio Reportado
                    case 2: {

                        UsaurioReportado = BusacarUsuarioReportado(IdReportado);
                        reporte.setUsuarioRep(UsaurioReportado);
                    }
                    break;
                    //Necesidad, Buscar Necesidad Reportado
                    case 3: {
                        NecesidadReportada = BusacarNecesidadReportado(IdReportado);
                        reporte.setNecesidadRep(NecesidadReportada);
                    }
                    break;
                }

                listaReportes.add(reporte);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response = "Error de conexion";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String Response) {
        if (Response == "") {

            ArrayAdapter<Reporte> adapter = new ArrayAdapter<Reporte>(context, android.R.layout.simple_list_item_1, listaReportes);

            ReportesLV.setAdapter(adapter);

            ReportesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String Info = "Reporte Id: " + listaReportes.get(i).getId() + "\n";
                    Info += " Fecha: " + listaReportes.get(i).getFechaAlta().toString() + "\n";
                    Info += " Estado: " + listaReportes.get(i).getEstado().getDescripcion().toString() + "\n";
                    Info += " Tipo: " + listaReportes.get(i).getTipo().getDescripcion().toString() + "\n";
                    Info += " Usuario Alta: " + listaReportes.get(i).getUsuario().getEmail().toString() + " " + listaReportes.get(i).getUsuario().getApellido().toString() + " - " + listaReportes.get(i).getUsuario().getNombre().toString() + "\n";


                    Toast.makeText(context, Info, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context, Response, Toast.LENGTH_SHORT).show();

        }

    }

    private Usuario BusacarUsuarioReportado(Integer IdReportado) {
        Usuario usuarioReportado = new Usuario();


        String query =
                "Select  u.id , u.email, u.nombre, u.apellido,u.dni, u.estado\n" +
                        "from usuarios u \n" +
                        "where u.id =?";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, IdReportado);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                usuarioReportado.setId(rs.getInt("Id"));
                usuarioReportado.setNombre(rs.getString("nombre"));
                usuarioReportado.setApellido(rs.getString("Apellido"));
                usuarioReportado.setEmail(rs.getString("Email"));
                usuarioReportado.setDni(rs.getString("DNI"));
                usuarioReportado.setId(rs.getInt("Estado"));

            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al Buscar el Usaurio Reportado";
        }


        return usuarioReportado;
    }

    private Necesidad BusacarNecesidadReportado(Integer IdReportado) {
        Necesidad NecesidadReportada = new Necesidad();

        return NecesidadReportada;
    }


}
