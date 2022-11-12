package com.example.comedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comedores.conexion.DataEstados;
import com.example.comedores.conexion.DataReporte;
import com.example.comedores.conexion.DataTipos;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

public class ReportesAlta extends AppCompatActivity {

    private UsuarioViewModel viewModel;
    private Reporte reporte;

    private Usuario usuario;
    private EditText etReportId;
    private EditText etReporteDesc;
    public Button btnReportar;
    public Button btnAtras;
    private Spinner spnTipo;
    private Spinner spnEstado;
    private TextView tvIdReportado, tvEstado, tvTipo;
    private String Anterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_alta);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        reporte = (Reporte) getIntent().getSerializableExtra("reporte"); //Tiene que venir con tipo. Con Id si es mod


        cargarUI();
        cargarControles();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volver();
            }
        });
        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportarAlta();


            }
        });
    }

    private void cargarUI() {
        etReporteDesc = (EditText) findViewById(R.id.etReporteDesc);

        tvIdReportado = (TextView) findViewById(R.id.tvIdReportado);
        tvTipo = (TextView) findViewById(R.id.tvTipoRep);
        tvEstado = (TextView) findViewById(R.id.tvEstadoRep);


      /*  spnTipo = (Spinner) findViewById(R.id.spTipoReporte);
        DataTipos taskTipos = new DataTipos(spnTipo, this);
        taskTipos.execute("2");//Obtengo los tipos de reporte

        spnEstado = (Spinner) findViewById(R.id.spEstadoReporte);
        DataEstados taskEstado = new DataEstados(spnEstado, this);
        taskEstado.execute("2");//Obtengo los tipos de reporte*/

        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnReportar = (Button) findViewById(R.id.btnReportar);
    }

    private void cargarControles() {


        //etReporteDesc.setText(reporte.getDescripcion());
        tvEstado.setText("Estado: en proceso");

        switch (reporte.getTipo().getId()) {
            //Aplicacion, no ahcer nada
            case 1: {
                tvIdReportado.setVisibility(View.INVISIBLE);
                tvTipo.setText("Tipo: Aplicación");
            }
            break;
            //usuario Buscar Usaurio Reportado
            case 2: {
                String Reprotado = "Usuario Reportado: ";
                Reprotado += reporte.getUsuario().getEmail();
                tvIdReportado.setText(Reprotado);
                tvTipo.setText("Tipo: Usuario");
            }
            break;
            //Necesidad, Buscar Necesidad Reportado
            case 3: {
                String Reprotado = "Neceidad Id: ";
                Reprotado += reporte.getNecesidadRep().getId();
                tvIdReportado.setText(Reprotado);
                tvTipo.setText("Tipo: Necesidad");
            }
            break;
        }


    }

    protected void ReportarAlta() {
        //Cargar textos
        if (cargarReporteAlta()) {
            DataReporte task = new DataReporte(usuario, this, reporte);
            task.execute("AltaReporte");
        }

    }

    protected boolean cargarReporteAlta() {
        //Cargar textos
        if (etReporteDesc.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe Cargar la descripcion del reporte", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etReporteDesc.getText().toString().length() > 500) {
            Toast.makeText(this, "La descripcion debe contener menos de 500 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        reporte.setDescripcion(etReporteDesc.getText().toString());
     /*   reporteN.getEstado().setId(1);
        reporteN.getUsuario().setId(usuario.getId());
        reporteN.getTipo().setId(reporte.getTipo().getId());*/

        return true;
    }

    protected void Volver() {
        Intent in;
        switch(usuario.getTipo()){
            case 1:
                in= new Intent(this,PrincipalUsuario.class);
                break;
            case 2:
                in = new Intent(this, PrincipalComedorAdmin.class);
                break;
            default:
                in = new Intent(this, MainActivity.class);
        }
        in.putExtra("usuario",usuario);
        startActivity(in);

    }
}
