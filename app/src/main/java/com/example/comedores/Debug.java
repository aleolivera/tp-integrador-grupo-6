package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Debug extends AppCompatActivity {

    private Button btnGoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        cargarUI();

        btnGoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoPerfil();
            }
        });

    }

    private void cargarUI() {
        this.setTitle("Debug");
        btnGoPerfil = (Button) findViewById(R.id.btnGoPerfil);
    }

    private void GoPerfil() {
        Intent i = new Intent(this, PerfilUser.class);
        startActivity(i);
        finish();
    }
}