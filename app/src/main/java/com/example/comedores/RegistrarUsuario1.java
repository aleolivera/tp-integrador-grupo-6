package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comedores.entidades.Usuario;

public class RegistrarUsuario1 extends AppCompatActivity {
    private EditText etNombre;
    private EditText etApellido;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword2;
    private EditText etDni;
    private Button btnAtras;
    private Button btnSiguiente;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario1);

        usuario=(Usuario)getIntent().getSerializableExtra("usuario");
        cargarUI();
        cargarControles();
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverALogin();
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irASiguiente();
            }
        });
    }

    private void cargarUI() {
        etNombre=(EditText) findViewById(R.id.etNombre);
        etApellido=(EditText) findViewById(R.id.etApellido);
        etEmail=(EditText) findViewById(R.id.etEmail);
        etPassword=(EditText) findViewById(R.id.etPasswordRegistrase);
        etPassword2=(EditText) findViewById(R.id.etRepetirPassword);
        etDni=(EditText) findViewById(R.id.etDni);
        btnAtras=(Button) findViewById(R.id.btnAtras);
        btnSiguiente=(Button) findViewById(R.id.btnSiguiente);
    }

    private void cargarControles() {
        if(usuario!=null){
            etNombre.setText(usuario.getNombre());
            etApellido.setText(usuario.getApellido());
            etEmail.setText(usuario.getEmail());
            etDni.setText(usuario.getDni());
        }
    }
    private String validarCampos() {
        String nombre=etNombre.getText().toString();
        String apellido=etApellido.getText().toString();
        String email=etEmail.getText().toString();
        String dni= etDni.getText().toString();
        String pass1= etPassword.getText().toString();
        String pass2= etPassword2.getText().toString();
        if(nombre.isEmpty()||apellido.isEmpty()||email.isEmpty()|| dni.isEmpty()||pass1.isEmpty()||pass2.isEmpty())
            return "Debe completar todos los campos";
        if(pass1.compareTo(pass2)!=0)
            return "Las contraseñas deben coincidir";
        return "";
    }
    private void cargarUsuario() {
        usuario=new Usuario();
        usuario.setNombre(etNombre.getText().toString());
        usuario.setApellido(etApellido.getText().toString());
        usuario.setEmail(etEmail.getText().toString());
        usuario.setDni(etDni.getText().toString());
        usuario.setPassword(etPassword.getText().toString());
    }
    private void irASiguiente(){
        String mensaje= validarCampos();
        if(mensaje.compareTo("")==0){
            Intent i= new Intent(RegistrarUsuario1.this,RegistrarUsuario2.class);
            cargarUsuario();
            i.putExtra("usuario",usuario);
            startActivity(i);
        }
        else
            Toast.makeText(RegistrarUsuario1.this, mensaje, Toast.LENGTH_SHORT).show();
    }
    private void volverALogin(){
        Intent i= new Intent(RegistrarUsuario1.this,MainActivity.class);
        startActivity(i);
    }
}