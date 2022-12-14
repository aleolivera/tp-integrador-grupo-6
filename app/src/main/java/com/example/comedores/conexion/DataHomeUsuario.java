package com.example.comedores.conexion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.PerfilUser;
import com.example.comedores.R;
import com.example.comedores.adapters.ListViewComedoresAdapter;
import com.example.comedores.adapters.ListViewNecesidadesHomeAdapter;
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
import java.util.List;

public class DataHomeUsuario extends AsyncTask<String,Void,String> {
    private Context context;
    private ListView lvNecesidades;
    private Comedor comedor;
    private long idNecesidad;
    private List<Necesidad> listaNecesidades;
    private String mensaje;
    private String email;

    public DataHomeUsuario(Context context, ListView lvNecesidades, List<Necesidad> listaNecesidades) {
        this.context = context;
        this.lvNecesidades = lvNecesidades;
        this.listaNecesidades = listaNecesidades;
    }

    public DataHomeUsuario(Context context, Comedor comedor, long idNecesidad) {
        this.context = context;
        this.comedor = comedor;
        this.idNecesidad = idNecesidad;
    }
    @Override
    protected String doInBackground(String... strings) {
        mensaje="";
        switch (strings[0]) {
            case "listarNecesidades":
                listarNecesidades();
                break;

            case "buscarComedorPorIdNecesidad":
                buscarComedorPorIdNecesidad();
                buscarEmail();
                break;

            case "listarNecesidadesPorLocalidad":
                listarNecesidadesPorLocalidad(strings[1]);
                break;
            default:
                break;
        }
        return mensaje;
    }

    private void buscarComedorPorIdNecesidad() {
        String query="select c.id,c.estado_id, e.descripcion,c.usuario_id,c.renacom,c.nombre,c.direccion,"+
                " c.localidad,c.provincia,c.telefono,c.nombre_responsable,c.apellido_responsable"+
                " from comedores c inner join comedores_x_necesidades cn on cn.comedor_id=c.id"+
                " inner join necesidades n on n.id=cn.necesidad_id"+
                " inner join estados_comedor e on e.id=c.estado_id"+
                " where n.id=?";
        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setLong(1,idNecesidad);
            comedor= new Comedor();
            ResultSet rs= pst.executeQuery();


            if(rs.next()){

                comedor.setId(rs.getLong(1));
                comedor.setEstado(new Estado(rs.getInt(2),rs.getString(3)));
                comedor.setIdResponsable(rs.getLong(4));
                comedor.setRenacom(rs.getLong(5));
                comedor.setNombre(rs.getString(6));
                comedor.setDireccion(rs.getString(7));
                comedor.setLocalidad(rs.getString(8));
                comedor.setProvincia(rs.getString(9));
                comedor.setTelefono(rs.getString(10));
                comedor.setNombreResponsable(rs.getString(11));
                comedor.setApellidoResponsable(rs.getString(12));
            }
            else
                mensaje = "No se encontro Comedor";
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar comedor";
        }
    }


    private void listarNecesidadesPorLocalidad(String localidad) {
        String query="SELECT n.id,n.tipo_id,t.descripcion,n.estado_id,e.descripcion,"+
                "n.descripcion,n.prioridad FROM necesidades n"+
                " INNER JOIN estados_necesidad e ON e.id=n.estado_id"+
                " INNER JOIN tipos_necesidad t ON t.id=n.tipo_id"+"" +
                " INNER JOIN comedores_x_necesidades cn ON cn.necesidad_id=n.id"+
                " INNER JOIN comedores c ON c.id=cn.comedor_id"+
                " WHERE c.localidad=? AND e.id=1"+
                " ORDER BY n.prioridad DESC";

        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,localidad);
            listaNecesidades=new ArrayList<Necesidad>();
            ResultSet rs= pst.executeQuery();


            while(rs.next()){
                Necesidad n= new Necesidad();

                n.setId(rs.getLong(1));
                n.setTipo(new Tipo(rs.getInt(2),rs.getString(3)));
                n.setEstado(new Estado(rs.getInt(4),rs.getString(5)));
                n.setDescripcion(rs.getString(6));
                n.setPrioridad(rs.getInt(7));

                listaNecesidades.add(n);
            }
            mensaje = "cargarListView";
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar las necesidades";
        }
    }

    private void listarNecesidades() {
        String query="SELECT n.id,n.tipo_id,t.descripcion,n.estado_id,e.descripcion,n.descripcion,n.prioridad"+
                " FROM necesidades n INNER JOIN estados_necesidad e on e.id=n.estado_id"+
                " INNER JOIN tipos_necesidad t on t.id=n.tipo_id"+
                " WHERE e.id=1"+
                " ORDER BY n.prioridad desc";

        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            listaNecesidades=new ArrayList<Necesidad>();
            ResultSet rs= pst.executeQuery();


            while(rs.next()){
                Necesidad n= new Necesidad();

                n.setId(rs.getLong(1));
                n.setTipo(new Tipo(rs.getInt(2),rs.getString(3)));
                n.setEstado(new Estado(rs.getInt(4),rs.getString(5)));
                n.setDescripcion(rs.getString(6));
                n.setPrioridad(rs.getInt(7));

                listaNecesidades.add(n);
            }
            mensaje = "cargarListView";
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar las necesidades";
        }
    }
    private void buscarEmail(){
        String query="SELECT u.email FROM usuarios u"+
                " INNER JOIN comedores c ON c.usuario_id=u.id"+
                " WHERE c.id=?";
        try{
            Class.forName(DataDB.DRIVER);
            Connection con = DriverManager.getConnection(DataDB.URLMYSQL,DataDB.USER,DataDB.PASS);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setLong(1,comedor.getId());
            ResultSet rs= pst.executeQuery();

            if(rs.next()) {
                email = rs.getString(1);
                mensaje="irAComedor";
            }
            else
                email="";
            rs.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            mensaje="Error al cargar las necesidades";
        }

    }
    @Override
    protected void onPostExecute(String mensaje) {
        if (mensaje.compareTo("cargarListView") == 0) {
            ListViewNecesidadesHomeAdapter adapter = new ListViewNecesidadesHomeAdapter(context, R.layout.item_row_necesidades, listaNecesidades);
            lvNecesidades.setAdapter(adapter);
            if(listaNecesidades.size()<1)
                Toast.makeText(context, "No se encontraron publicaciones", Toast.LENGTH_LONG).show();
        }

        if(mensaje.compareTo("irAComedor")==0){
            Intent i = new Intent(context, PerfilUser.class);
            i.putExtra("comedor",comedor);
            i.putExtra("email", email);
            context.startActivity(i);
        }
    }


}
