package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Usuario;

public class PerfilUser extends AppCompatActivity {

    Comedor comedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);


        this.setTitle("Perfil");

        comedor = (Comedor) getIntent().getSerializableExtra("comedor");


        TextView tvComedorNombre = (TextView) findViewById(R.id.tvComedorNombre);
        TextView tvRenacomNumero = (TextView) findViewById(R.id.tvRenacomNumero);
        TextView txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        TextView txtLocalidad = (TextView) findViewById(R.id.txtLocalidad);
        TextView txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        TextView txtResponsable = (TextView) findViewById(R.id.txtResponsable);

        tvComedorNombre.setText(comedor.getNombre());
        tvRenacomNumero.setText("# ReNaCom: " + String.valueOf(comedor.getRenacom()));
        txtDireccion.setText(comedor.getDireccion());
        txtLocalidad.setText(comedor.getLocalidad());
        txtTelefono.setText(comedor.getTelefono());
        txtResponsable.setText(comedor.getNombreResponsable() + " " + comedor.getApellidoResponsable());

    }


}