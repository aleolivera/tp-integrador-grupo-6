package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.comedores.entidades.Usuario;

public class Debug extends AppCompatActivity {

    private Button btnGoPerfil;
    private Button btnMaps;
    private Button btnBuscarComedor;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        cargarUI();

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        btnGoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoPerfil();
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoMaps();
            }
        });

        btnBuscarComedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoBuscarComedor();
            }
        });

    }

    private void cargarUI() {
        this.setTitle("Debug");
        btnGoPerfil = (Button) findViewById(R.id.btnGoPerfil);
        btnMaps = (Button) findViewById(R.id.btnMaps);
        btnBuscarComedor = (Button) findViewById(R.id.btnBuscarComedor);

    }

    private void GoPerfil() {
        Intent i = new Intent(this, PerfilUser.class);
        i.putExtra("usuario",usuario);
        startActivity(i);
        finish();
    }

    private void GoMaps() {

    }

    private void GoBuscarComedor() {
        Intent i = new Intent(this, Comedores.class);
        startActivity(i);
        finish();
    }
}