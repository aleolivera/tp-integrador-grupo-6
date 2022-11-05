package com.example.comedores.ui.homeAdminComedor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.entidades.Usuario;


public class HomeAdminComedor extends Fragment {
    public TextView tvTitulo;
    public Usuario usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_admin_comedor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitulo=(TextView) view.findViewById(R.id.tvTituloAdmin);
        Toast.makeText(getContext(), "Fragment principal", Toast.LENGTH_SHORT).show();

    }
}