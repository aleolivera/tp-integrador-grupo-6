package com.example.comedores.ui.homeUsuarioFinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comedores.MainActivity;
import com.example.comedores.PerfilUser;
import com.example.comedores.R;
import com.example.comedores.conexion.DataHomeUsuario;
import com.example.comedores.conexion.DataSolicitudes;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Solicitud;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

import java.util.List;


public class HomeUsuarioFinalFragment extends Fragment {
    private View view;
    private UsuarioViewModel viewModel;
    private Usuario usuario;
    private List<Necesidad> listaNecesidades;
    private ListView lvNecesidades;
    private Spinner spFiltro;
    private Comedor comedor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home_usuario_final, container, false);
        viewModel= new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuario=viewModel.getData().getValue();
        cargarUI();
        iniciarListView();
        iniciarSpinner();
    }

    private void iniciarSpinner() {
        String[]filtro={"Todas","En mi localidad"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,filtro);
        spFiltro.setAdapter(adapter);
        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        cargarListView("todas");
                        break;
                    case 1:
                        cargarListView("localidad");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void iniciarListView() {
        cargarListView("todas");
        lvNecesidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Necesidad n= (Necesidad)lvNecesidades.getItemAtPosition(position);
                irAPerfilComedor(n.getId());
            }
        });

    }
    private void cargarListView(String mostrar) {
        DataHomeUsuario task= new DataHomeUsuario(getContext(),lvNecesidades,listaNecesidades);
        switch(mostrar){
            case "todas":
                task.execute("listarNecesidades");
                break;
            case "localidad":
                task.execute("listarNecesidadesPorLocalidad",usuario.getLocalidad());
                break;
        }
    }
    private void irAPerfilComedor(long idNecesidad){
        DataHomeUsuario task= new DataHomeUsuario(getContext(),comedor, idNecesidad);
        task.execute("buscarComedorPorIdNecesidad");

    }
    private void cargarUI() {
        lvNecesidades=(ListView) view.findViewById(R.id.lvNecesidadesHome);
        spFiltro= (Spinner) view.findViewById(R.id.spFiltro);
    }
}