package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comedores.conexion.DataRegistrarUsuario;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.util.Validacion;

import java.util.ArrayList;

public class RegistrarUsuario2 extends AppCompatActivity {
    private EditText etDireccion;
    private EditText etLocalidad;
    private EditText etTelefono;
    private Spinner spProvincias;
    private Spinner spTiposUsuario;
    private Button btnAtras;
    private Button btnFinalizar;
    private Usuario usuario;
    private final String [] provincias={"Buenos Aires", "Ciudad Autónoma de Bs. As.",
            "Catamarca","Chaco","Chubut","Córdoba","Corrientes","Entre Ríos",
            "Formosa","Jujuy","La Pampa","La Rioja","Mendoza","Misiones","Neuquén",
            "Río Negro", "Salta","San Juan","San Luis","Santa Cruz","Santa Fe",
            "Santiago del Estero","Tierra del Fuego", "Antártida","Tucumán"};
    private final String [] tiposUsuario= {"Usuario solidario","Administrador de comedor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario2);

        usuario=(Usuario)getIntent().getSerializableExtra("usuario");
        cargarUI();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverAtras();
            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
        cargarControles();
    }

    private void cargarUI() {
        etDireccion=(EditText) findViewById(R.id.etDireccion);
        etLocalidad=(EditText) findViewById(R.id.etLocalidad);
        etTelefono=(EditText) findViewById(R.id.etTelefono);
        spProvincias=(Spinner) findViewById(R.id.spProvincias);
        spTiposUsuario=(Spinner) findViewById(R.id.spTiposUsuario);
        btnAtras=(Button) findViewById(R.id.btnAtras2);
        btnFinalizar=(Button) findViewById(R.id.btnFinalizar);

        ArrayAdapter<String> adapterProvincias= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,provincias);
        spProvincias.setAdapter(adapterProvincias);

        ArrayAdapter<String> adapterTiposUsuario= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tiposUsuario);
        spTiposUsuario.setAdapter(adapterTiposUsuario);
    }

    private void cargarControles() {
        if(usuario.getDireccion()!=null && !usuario.getDireccion().isEmpty())
            etDireccion.setText(usuario.getDireccion());
        if(usuario.getLocalidad()!=null && !usuario.getLocalidad().isEmpty())
            etLocalidad.setText(usuario.getLocalidad());
        if(usuario.getProvincia()!=null && !usuario.getProvincia().isEmpty())
            spProvincias.setSelection(encontrarIndice(usuario.getProvincia(),provincias));
        if(usuario.getTelefono()!=null && !usuario.getTelefono().isEmpty())
            etTelefono.setText(usuario.getTelefono());
        spTiposUsuario.setSelection(0);
    }

    private int encontrarIndice(String valor,String[]array) {
        for (int i=0;i< array.length;i++) {
            String string =array[i];
            if(string.compareTo(string)==0)
                return i;
        }
        return 0;
    }

    private String validarCampos() {
        String mensaje="";
        String direccion=etDireccion.getText().toString();
        String localidad=etLocalidad.getText().toString();
        String telefono=etTelefono.getText().toString();
        if(direccion.isEmpty()||localidad.isEmpty()||telefono.isEmpty())
            mensaje= "Debe completar todos los campos";
        else{
            if (!Validacion.validarString(direccion, Validacion.LETRAS_NUMEROS_ESPACIOS))
                mensaje = "Direccion: solo caracteres alfanumericos";
            if (!Validacion.validarString(localidad, Validacion.LETRAS_ESPACIOS))
                mensaje = "Localidad: solo caracteres alfabeticos";
            if (!Validacion.validarString(telefono, Validacion.NUMEROS))
                mensaje = "Telefono: solo caracteres numericos";
        }
        return mensaje;
    }
    private void cargarUsuario() {
        usuario.setDireccion(etDireccion.getText().toString());
        usuario.setTelefono(etTelefono.getText().toString());
        usuario.setLocalidad(etLocalidad.getText().toString());
        usuario.setTipo(spTiposUsuario.getSelectedItemPosition()+1);
        usuario.setProvincia(spProvincias.getSelectedItem().toString());
        usuario.setEstado(true);
    }
    private void volverAtras(){
        Intent i= new Intent(RegistrarUsuario2.this,RegistrarUsuario1.class);
        i.putExtra("usuario",usuario);
        startActivity(i);
        finish();
    }
    private void volverALogin(){
        Intent i= new Intent(RegistrarUsuario2.this,MainActivity.class);
        startActivity(i);
        finish();
    }
    private void registrarUsuario(){
        String mensaje= validarCampos();
        if(mensaje.compareTo("")==0){
            cargarUsuario();
            ejecutarTask();
            volverALogin();
        }
        else
            Toast.makeText(RegistrarUsuario2.this, mensaje, Toast.LENGTH_SHORT).show();
    }
    private void ejecutarTask(){
        DataRegistrarUsuario task= new DataRegistrarUsuario(this,usuario);
        task.execute();
    }
}