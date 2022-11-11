package com.example.comedores;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private TextView tvIdReportado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_alta);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        reporte = (Reporte) getIntent().getSerializableExtra("reporte"); //Tiene que venir con tipo. Con Id si es mod


        cargarUI();
        //Para modificar
        //Reporte no deberia ser null nunca
        if (reporte.getId() != 0) {
            cargarControles();
        } else {
            if (reporte.getTipo().getId() == 1) {
                tvIdReportado.setVisibility(View.INVISIBLE);
            }
            spnTipo.setEnabled(false);
            spnTipo.setClickable(false);

            spnEstado.setEnabled(false);
            spnEstado.setClickable(false);
        }

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volverALogin();
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

        spnTipo = (Spinner) findViewById(R.id.spTipoReporte);
        DataTipos taskTipos = new DataTipos(spnTipo, this);
        taskTipos.execute("2");//Obtengo los tipos de reporte

        spnEstado = (Spinner) findViewById(R.id.spEstadoReporte);
        DataEstados taskEstado = new DataEstados(spnEstado, this);
        taskEstado.execute("2");//Obtengo los tipos de reporte

        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnReportar = (Button) findViewById(R.id.btnReportar);
    }

    private void cargarControles() {
        if (reporte != null) {
            spnTipo.setId(reporte.getTipo().getId());
            spnEstado.setId(reporte.getEstado().getId());
            etReporteDesc.setText(reporte.getDescripcion());


            switch (reporte.getTipo().getId()) {
                //Aplicacion, no ahcer nada
                case 1: {
                    tvIdReportado.setVisibility(View.INVISIBLE);
                }
                break;
                //usuario Buscar Usaurio Reportado
                case 2: {
                    String Reprotado = "Usuario Reportado: ";
                    Reprotado += reporte.getUsuario().getEmail();
                    tvIdReportado.setText(Reprotado);
                }
                break;
                //Necesidad, Buscar Necesidad Reportado
                case 3: {
                    String Reprotado = "Neceidad Id: ";
                    Reprotado += reporte.getNecesidadRep().getId();
                    tvIdReportado.setText(Reprotado);
                }
                break;
            }


        }
    }

    protected void ReportarAlta() {
        //Cargar textos
        cargarReporteAlta();
        DataReporte task = new DataReporte(usuario, this, reporte);
        task.execute("AltaReporte");
    }

    protected void cargarReporteAlta() {
        //Cargar textos
        reporte.setDescripcion(etReporteDesc.getText().toString());
    }
}
