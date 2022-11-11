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

import com.example.comedores.R;
import com.example.comedores.adapters.ListViewReportesAdapter;
import com.example.comedores.adapters.ListViewSolicitudesAdapter;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Solicitud;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DataReporte extends AsyncTask<String, Void, String> {

    private String mensaje;
    private Usuario usuario;
    private Context context;
    private Reporte reporte;

    private ListView lvReportes;
    private List<Reporte> listaReportes;

    public DataReporte(Usuario usuario, Context context) {
        this.usuario = usuario;
        this.context = context;
    }

    public DataReporte(Usuario usuario, Context context, Reporte reporte) {
        this.usuario = usuario;
        this.context = context;
        this.reporte = reporte;
    }

    public DataReporte(Context context, List<Reporte> listaReportes, ListView ReportesLV) {
        this.usuario = usuario;
        this.context = context;
        this.lvReportes = ReportesLV;
        this.listaReportes = listaReportes;
    }


    public DataReporte(Usuario usuario, Context context, List<Reporte> listaReportes, ListView ReportesLV) {
        this.usuario = usuario;
        this.context = context;
        this.lvReportes = ReportesLV;
        this.listaReportes = listaReportes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... strings) {

        switch (strings[0]) {
            case "ListarReporte":
                ReportesListar(strings);
                break;

            case "AltaReporte":
                Reporte_Alta();
                break;

            default:
                break;
        }
        return mensaje;

    }

    protected void ReportesListar(String... strings) {

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
                "\t(u.id = " + strings[1] + " or  " + strings[1] + " = 0)\n" +
                "\tand (r.Id = " + strings[2] + " or  " + strings[2] + " = 0)\n" +
                "\tand (er.Id = " + strings[3] + " or " + strings[3] + " = 0)\n" +
                "\tand (tr.id = " + strings[4] + " or " + strings[4] + " = 0)" +
                "\t order by r.id desc ";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            listaReportes = new ArrayList<Reporte>();

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

            if (listaReportes.size() > 0) {
                mensaje = "Reportes cargados";
            }
            resultSet.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al cargar los Reportes";
        }
    }

    protected void Reporte_Alta() {

        if (VerificarReporteExistente(reporte.getUsuario().getId(), reporte.getTipo().getId(), reporte.getEstado().getId())) {

            mensaje = "";
            String Query = "INSERT INTO reportes ( tipo_id, estado_id, fecha_alta, descripcion,  reportado_id) \n" +
                    "values (?,?,?,?,?)";
            try {
                Class.forName(DataDB.DRIVER);
                Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);

                PreparedStatement pst = con.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, (int) reporte.getTipo().getId());
                pst.setInt(2, reporte.getEstado().getId());

                pst.setDate(3, java.sql.Date.valueOf(String.valueOf(java.time.LocalDate.now())));

                pst.setString(4, reporte.getDescripcion());
                //pst.setString(5, reporte.getRespuesta());

                if ((int) reporte.getIdReportado() != 0) {
                    pst.setInt(5, (int) reporte.getIdReportado());
                } else {
                    //Long nullLong = null;
                    pst.setNString(5, null);
                }

                int FilasInsertadas = pst.executeUpdate(); //
                int IdReprote = 0;
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    IdReprote = rs.getInt(1);
                }

                if (FilasInsertadas > 0 && IdReprote > 0) {

                    mensaje = "AltaReporteExitosa";
                    ReportePorUsuarioAlta(IdReprote, (int) reporte.getUsuario().getId());

                } else {
                    mensaje = "ReporteAltaError";
                }


            } catch (Exception e) {
                e.printStackTrace();
                mensaje = e.getMessage();
            }
        } else {
            mensaje = "El reporte ya existe";
        }

    }

    protected boolean VerificarReporteExistente(long UsuarioAltaId, int ReportadoId, int TipoId) {
        //Si es app que no busque nada
        if (TipoId == 1) {
            return true;
        }

        String query = "select count(r.id)\n" +
                "from reportes r\n" +
                "  inner join usuarios_x_reportes uxr on uxr.reporte_id  = r.id \n" +
                "  inner join tipos_reporte tr on tr.id = r.tipo_Id \n" +
                "where \n" +
                "\t(uxr.Usuario_Id = " + UsuarioAltaId + ")\n" +
                "\tand (r.reportado_id = " + ReportadoId + ")\n" +
                "\tand (tr.id = " + TipoId + ")";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al buscar Reporte existente";
        }

        return true;

    }

    protected void ReportePorUsuarioAlta(int IdReporte, int UsuarioAltaId) {

        String Query = "insert into `usuarios_x_reportes` (usuario_id, reporte_id)\n" +
                "values (" + UsuarioAltaId + ", " + IdReporte + " )";

        try {
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL, DataDB.USER, DataDB.PASS);
            PreparedStatement pst = con.prepareStatement(Query);

            int Id = pst.executeUpdate(); //
            if (Id > 0) {
                mensaje = "AltaReporteExitosa";
            } else {
                mensaje = "ReporteAltaError";
            }
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String Response) {

        if (mensaje.compareTo("") != 0)
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();

        if (mensaje.compareTo("Reportes cargados") == 0) {
            ListViewReportesAdapter adapter = new ListViewReportesAdapter(context, R.layout.item_row_reportes, listaReportes);
            lvReportes.setAdapter(adapter);
        }

        if (mensaje.compareTo("AltaReporteExitosa") != 0)
            Toast.makeText(context, "El reporte se dio de alta.", Toast.LENGTH_SHORT).show();

//        if (Response == "") {
//
//            ArrayAdapter<Reporte> adapter = new ArrayAdapter<Reporte>(context, android.R.layout.simple_list_item_1, listaReportes);
//
//            lvReportes.setAdapter(adapter);
//
//            lvReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    String Info = "Reporte Id: " + listaReportes.get(i).getId() + "\n";
//                    Info += " Fecha: " + listaReportes.get(i).getFechaAlta().toString() + "\n";
//                    Info += " Estado: " + listaReportes.get(i).getEstado().getDescripcion().toString() + "\n";
//                    Info += " Tipo: " + listaReportes.get(i).getTipo().getDescripcion().toString() + "\n";
//                    Info += " Usuario Alta: " + listaReportes.get(i).getUsuario().getEmail().toString() + " " + listaReportes.get(i).getUsuario().getApellido().toString() + " - " + listaReportes.get(i).getUsuario().getNombre().toString() + "\n";
//
//
//                    Toast.makeText(context, Info, Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        } else {
//            Toast.makeText(context, Response, Toast.LENGTH_SHORT).show();
//
//        }

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
