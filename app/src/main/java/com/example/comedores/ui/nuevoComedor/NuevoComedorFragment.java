package com.example.comedores.ui.nuevoComedor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

public class NuevoComedorFragment extends Fragment {

    private EditText etRenacom, etNombreComedor, etNombreResponsable,
            etApellidoResponsable, etDNI, etProvincia ,
        etLocalidad, etDireccion, etEmail, etTelefono;
    private Button btnRegistrar;
    private UsuarioViewModel viewModel;
    public Usuario usuario;
    public Comedor comedor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nuevo_comedor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtenerUsuario();
        if(usuario.getComedor()!=null) {

            Toast.makeText(getContext(), "Ya posee un comedor", Toast.LENGTH_SHORT).show();
        }
        cargarUi(view);
        cargarDatosPorDefecto();
    }

    private void obtenerUsuario() {
        viewModel= new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuario=viewModel.getData().getValue();
    }

    private void cargarUi(View view) {
        etRenacom=(EditText) view.findViewById(R.id.etRenacom);
        etNombreComedor=(EditText) view.findViewById(R.id.etNombreComedor);
        etNombreResponsable=(EditText) view.findViewById(R.id.etNombreResponsable);
        etApellidoResponsable=(EditText) view.findViewById(R.id.etApellidoResponsable);
        etDNI=(EditText) view.findViewById(R.id.etDNI);
        etProvincia=(EditText) view.findViewById(R.id.etProvincia);
        etLocalidad=(EditText)view.findViewById(R.id.etLocalidadRegistrarComedor);
        etDireccion=(EditText)view.findViewById(R.id.etTelefonoAgregarComedor);
        etEmail=(EditText)view.findViewById(R.id.etCorreoElectronicoAgregarComedor);
        etTelefono=(EditText)view.findViewById(R.id.etTelefonoAgregarComedor);
        btnRegistrar=(Button)view.findViewById(R.id.btnRegistrarComedor);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarComedor();
            }
        });
    }

    private void cargarDatosPorDefecto() {
        etNombreResponsable.setText(usuario.getNombre());
        etApellidoResponsable.setText(usuario.getApellido());
        etDNI.setText(usuario.getDni());
    }

    private void registrarComedor() {
        Toast.makeText(getContext(), "Falta Funcionalidad", Toast.LENGTH_SHORT).show();
    }


}