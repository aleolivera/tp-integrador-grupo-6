package com.example.comedores;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

public class ControlDeCuentasFragment extends Fragment {

    private View view;
    private UsuarioViewModel viewModel;
    private Usuario usuario;
    private ListView lvSolicitudes;

    public ControlDeCuentasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_control_de_cuentas, container, false);
        viewModel= new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuario=viewModel.getData().getValue();
        lvSolicitudes=(ListView) view.findViewById(R.id.lvSolicitudes);

    }
}