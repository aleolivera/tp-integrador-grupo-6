package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comedores.conexion.DataDB;
import com.example.comedores.conexion.DataLogin;
import com.example.comedores.conexion.DataRegistrarUsuario;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private EditText etCorreo;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegistrarse;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("");

        cargarUI();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irARegistrarUsuario();
            }
        });


    }


    private void Login() {
        String mensaje = validarControles();

        if (mensaje.compareTo("") == 0) {
            cargarUsuario();
            ejecutarTask();
        } else Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void cargarUsuario() {
        usuario = new Usuario();
        usuario.setId(0);
        usuario.setEmail(etCorreo.getText().toString());
        usuario.setPassword(etPassword.getText().toString());
    }

    private String validarControles() {
        String mensaje = "";

        //etCorreo.setText("leogerez@gmail.com");
        //etPassword.setText("1234");

        String email = etCorreo.getText().toString();
        String pass = etPassword.getText().toString();
        if (email.isEmpty() || pass.isEmpty())
            mensaje = "Debe completar todos los campos";
        return mensaje;
    }

    private void cargarUI() {
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
    }

    private void ejecutarTask() {
        DataLogin task = new DataLogin(usuario, this);
        task.execute();
    }

    private void irARegistrarUsuario() {
        Intent i = new Intent(this, RegistrarUsuario1.class);
        startActivity(i);
        finish();
    }
}