package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comedores.entidades.Usuario;

public class PerfilUser extends AppCompatActivity {

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        TextView txtNombreUser = (TextView) findViewById(R.id.txtNombreUser);
        TextView txtTipoUser = (TextView) findViewById(R.id.txtTipoUser);

        txtNombreUser.setText(usuario.getNombre() + " " + usuario.getApellido());

        switch(usuario.getTipo()){
            case 1:
                txtTipoUser.setText("Usuario Final");
                break;
            case 2:
                txtTipoUser.setText("Administrador de Comedores");
                break;
            default:
                txtTipoUser.setText("Supervisor");
                break;
        }

        EditText editTextDireccion = (EditText) findViewById(R.id.editTextDireccion);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextDireccion.setText(usuario.getDireccion());
        editTextEmail.setText(usuario.getEmail());
        editTextPhone.setText(usuario.getTelefono());
        editTextPassword.setText(usuario.getPassword());



        this.setTitle("Perfil");
    }


}