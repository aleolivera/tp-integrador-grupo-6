package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Usuario;

public class PerfilUser extends AppCompatActivity {

    private Comedor comedor;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);


        this.setTitle("Perfil");

        comedor = (Comedor) getIntent().getSerializableExtra("comedor");
        email = getIntent().getStringExtra("email");

        TextView tvComedorNombre = (TextView) findViewById(R.id.tvComedorNombre);
        TextView tvRenacomNumero = (TextView) findViewById(R.id.tvRenacomNumero);
        TextView txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        TextView txtLocalidad = (TextView) findViewById(R.id.txtLocalidad);
        TextView txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        TextView txtResponsable = (TextView) findViewById(R.id.txtResponsable);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);

        tvComedorNombre.setText(comedor.getNombre());
        tvRenacomNumero.setText("# ReNaCom: " + String.valueOf(comedor.getRenacom()));
        txtDireccion.setText(comedor.getDireccion());
        txtLocalidad.setText(comedor.getLocalidad());
        txtTelefono.setText(comedor.getTelefono());
        txtResponsable.setText(comedor.getNombreResponsable() + " " + comedor.getApellidoResponsable());
        txtEmail.setText(email);
    }


}