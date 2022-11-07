package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PerfilUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        this.setTitle("Perfil");
    }
}