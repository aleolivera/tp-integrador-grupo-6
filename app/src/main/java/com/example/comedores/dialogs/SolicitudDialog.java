package com.example.comedores.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comedores.R;
import com.example.comedores.conexion.DataSolicitudes;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Solicitud;
import com.example.comedores.entidades.Usuario;

public class SolicitudDialog extends AppCompatDialogFragment {
    private TextView tvNombreComedor,tvIdSolicitud,tvRenacom;
    private Solicitud solicitud;
    private long idUsuario;
    AlertDialog.Builder builder;

    public SolicitudDialog(Solicitud solicitud,long idUsuario) {
        this.solicitud= solicitud;
        this.idUsuario= idUsuario;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_solicitud,null);

        cargarUI(view);
        iniciarDialog(view);
        return builder.create();
    }

    private void cargarUI(View view) {
        tvNombreComedor= (TextView) view.findViewById(R.id.tvNombreComedorDialog);
        tvIdSolicitud= (TextView) view.findViewById(R.id.tvIdSolicitudDialog);
        tvRenacom= (TextView) view.findViewById(R.id.tvRenacomDialog);

        tvNombreComedor.setText("Nombre Comedor: "+solicitud.getComedor().getNombre());
        tvIdSolicitud.setText("ID Solicitud: "+String.valueOf(solicitud.getId()));
        tvRenacom.setText("Renacom: "+String.valueOf(solicitud.getComedor().getRenacom()));
    }
    private void modificarUsuario(){
        DataSolicitudes task= new DataSolicitudes(getContext(),solicitud,solicitud.getComedor());
        task.execute("modificarSolicitud");
    }
    private void iniciarDialog(View view) {
        String texto;
        if(solicitud.isEstado())
            texto="Cambiar a Pendiente";
        else
            texto="Cambiar a Habilitado";
        builder.setView(view).
                setTitle("Solicitud").
                setPositiveButton(texto, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(solicitud.isEstado()){
                            solicitud.setIdSupervisor(idUsuario);
                            solicitud.setEstado(false);
                            solicitud.getComedor().setEstado(new Estado(1,null));
                        }
                        else{
                            solicitud.setIdSupervisor(idUsuario);
                            solicitud.setEstado(true);
                            solicitud.getComedor().setEstado(new Estado(2,null));
                        }
                        modificarUsuario();
                        Toast.makeText(getContext(), "Estado de comedor Modificado", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
