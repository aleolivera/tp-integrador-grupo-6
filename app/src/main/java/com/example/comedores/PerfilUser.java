package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.comedores.entidades.Usuario;

public class PerfilUser extends AppCompatActivity {

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextName.setText(usuario.getNombre() + " " + usuario.getApellido());
        editTextEmail.setText(usuario.getEmail());
        editTextPhone.setText(usuario.getTelefono());
        editTextPassword.setText(usuario.getPassword());

        this.setTitle("Perfil");
    }


}