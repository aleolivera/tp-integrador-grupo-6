package com.example.comedores.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comedores.R;
import com.example.comedores.conexion.DataReporte;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Usuario;

public class ReporteDialog extends AppCompatDialogFragment {
    private TextView tvUsaurioAlta, tvReportado, tvReproteTitulo, tvFechaAlta, tvTipo;
    private CheckBox chkResuelto;
    private EditText etDescripcion, etRespuesta;
    private Spinner spnTipo;
    private Usuario usuario;
    private Reporte reporte;
    private long idUsuario;
    private Button btnBaja;

    AlertDialog.Builder builder;

    public ReporteDialog(Reporte reporte, Usuario usuario) {
        this.reporte = reporte;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_reporte, null);

        cargarUI(view);
        iniciarDialog(view);
        return builder.create();
    }

    private void cargarUI(View view) {
        btnBaja = (Button) view.findViewById(R.id.btnBaja);
        tvUsaurioAlta = (TextView) view.findViewById(R.id.tvUsuarioAltaR);
        tvReportado = (TextView) view.findViewById(R.id.tvIdReportado2);
        tvFechaAlta = (TextView) view.findViewById(R.id.tvFechaAltaR);
        tvReproteTitulo = (TextView) view.findViewById(R.id.tvReporteDialogT);

        etDescripcion = (EditText) view.findViewById(R.id.etDescripcionR);
        etRespuesta = (EditText) view.findViewById(R.id.etRespuestaR);

        tvTipo = (TextView) view.findViewById(R.id.tvTipo);

        chkResuelto = (CheckBox) view.findViewById(R.id.chkResuelto);

        //Carga de datos
        tvUsaurioAlta.setText("Usuario Alta: " + reporte.getUsuario().getEmail());
        tvFechaAlta.setText("Fecha Alta: " + reporte.getFechaAlta().toString());
        tvReproteTitulo.setText("Reporte: " + reporte.getId());
        tvTipo.setText("Tipo: " + reporte.getTipo().getDescripcion());

        String respuesta = "";
        if (!TextUtils.isEmpty(reporte.getRespuesta())) {
            respuesta = reporte.getRespuesta().toString();
        }
        etRespuesta.setText(respuesta);

        etDescripcion.setText(reporte.getDescripcion());
        etDescripcion.setEnabled(false);
        etDescripcion.setClickable(false);


        boolean chk = false;
        if (reporte.getEstado().getId() == 2) {
            chk = true;
        }
        chkResuelto.setChecked(chk);

        switch (reporte.getTipo().getId()) {
            //Aplicacion, no ahcer nada
            case 1: {
                tvReportado.setVisibility(View.INVISIBLE);
                btnBaja.setVisibility(View.INVISIBLE);
            }
            break;
            //usuario Buscar Usaurio Reportado
            case 2: {
                String Reprotado = "Usuario Reportado: ";
                Reprotado += reporte.getUsuarioRep().getEmail();
                tvReportado.setText(Reprotado);
            }
            break;
            //Necesidad, Buscar Necesidad Reportado
            case 3: {
                String Reprotado = "Neceidad Id: ";
                Reprotado += reporte.getNecesidadRep().getId();
                tvReportado.setText(Reprotado);
            }
            break;
        }

        if (usuario.getTipo() != 3) {

            etRespuesta.setEnabled(false);
            etRespuesta.setClickable(false);

            chkResuelto.setEnabled(false);
            chkResuelto.setClickable(false);

            btnBaja.setVisibility(View.INVISIBLE);
        }
    }


    private void iniciarDialog(View view) {
        String texto = "Modificar";

        if (usuario.getTipo() == 3) {


            builder.setView(view).
                    //setTitle("ReproteId").
                            setPositiveButton(texto, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (usuario.getTipo() == 3) {

                                if (ValidaCampos()) {
                                    modificarReporte();
                                } else {
                                }
                            } else {

                            }
                        }
                    });
        }
        texto = "Cerrar";
        builder.setView(view).
                //setTitle("ReproteId").
                        setNegativeButton(texto, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        btnBaja.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                DarDeBaja();
            }
        });

    }

    private void modificarReporte() {
        DataReporte task = new DataReporte(usuario, getContext(), reporte);
        task.execute("ModificarReporte");
    }

    private void DarDeBaja() {
        DataReporte task = new DataReporte(usuario, getContext(), reporte);
        task.execute("DarDeBaja");
    }

    protected boolean ValidaCampos() {
        boolean todoOk = true;

        int estado = 1;
        if (chkResuelto.isChecked()) {
            estado = 2;
        }

        reporte.getEstado().setId(estado);

        if (!etRespuesta.getText().toString().isEmpty()) {
            reporte.setRespuesta(etRespuesta.getText().toString());

        } else {
            todoOk = false;
        }
        return todoOk;
    }
}
