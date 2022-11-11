package com.example.comedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comedores.conexion.DataEstados;
import com.example.comedores.conexion.DataTipos;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

public class ReportesBotones extends AppCompatActivity {


    private View view;
    private UsuarioViewModel viewModel;
    private Reporte reporte;

    private Usuario usuario;

    public Button btnReporteAplicacion;
    public Button btnReporteUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_botones);

        //Para ingresar un nuevo reporte necesito:
        //Usuario logueado

        //Objeto tipo Reporte

        //Tipo de reporte(dentro del objeto), solo hace falta el Id
        //1 Aplicacion
        //2 Usuario
        //3 Necesidad(si hace falta)

        //Objeto Estado id en 1(en proceso)

        //ID del usuario reportado.(solo si es tipo 2)

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        reporte = new Reporte();
        cargarUI();
        int idreportado = 6;

        btnReporteAplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarReprote(1, idreportado);

            }
        });
        btnReporteUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarReprote(2, idreportado);
            }
        });
    }

    private void cargarUI() {

        btnReporteAplicacion = (Button) findViewById(R.id.btnReporteAplicacion);
        btnReporteUsuario = (Button) findViewById(R.id.btnReporteUsuario);
    }

    private void cargarReprote(int tipo, int idreportado) {
        Tipo t = new Tipo(1, "");
        Estado e = new Estado(1, "");
        //Aca estan los ejemplos
        switch (tipo) {
            case 1://Aplicacion
            {
                t.setId(1);
                reporte.setIdReportado(0);
            }
            break;
            case 2://Usaurio
            {
                t.setId(2);
                reporte.setIdReportado(idreportado);
            }
            break;
            case 3://necesidad
            {
                t.setId(3);
                reporte.setIdReportado(idreportado);
            }
            break;

        }
        reporte.setUsuario(usuario);
        reporte.setTipo(t);
        reporte.setEstado(e);
        IrAReportesAlta();
    }

    private void IrAReportesAlta() {
        Intent i = new Intent(this, ReportesAlta.class);
        i.putExtra("reporte", reporte);
        startActivity(i);
    }
}
