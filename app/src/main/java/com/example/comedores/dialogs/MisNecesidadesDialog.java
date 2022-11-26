package com.example.comedores.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comedores.R;
import com.example.comedores.conexion.DataMisNecesidadesDialog;
import com.example.comedores.conexion.DataNecesidades;
import com.example.comedores.entidades.Necesidad;

public class MisNecesidadesDialog extends AppCompatDialogFragment {
    private long idNecesidad;
    private long idComedor;
    private ListView lvNecesidades;
    AlertDialog.Builder builder;

    public MisNecesidadesDialog(long idNecesidad,long idComedor,ListView lvNecesidades){
        this.idNecesidad=idNecesidad;
        this.idComedor=idComedor;
        this.lvNecesidades=lvNecesidades;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_mis_necesidades,null);
        iniciarDialog(view);

        return builder.create();
    }

    private void iniciarDialog(View view){
        builder.setView(view).setTitle("Eliminar").
                setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarNecesidad();
                    }
                });
    }

    private void eliminarNecesidad(){
        DataMisNecesidadesDialog task= new DataMisNecesidadesDialog(getContext(),lvNecesidades,idNecesidad,idComedor);
        task.execute("eliminarNecesidad");
    }
}
