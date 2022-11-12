package com.example.comedores.ui.misNecesidades;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.adapters.ListViewMisNecesidadesAdapter;
import com.example.comedores.conexion.DataComedores;
import com.example.comedores.conexion.DataNecesidades;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

import java.util.List;

public class MisNecesidadesFragment extends Fragment {

    private UsuarioViewModel viewModel;
    private ListView lvMisNecesidades;
    private List<Necesidad> listaNecesidades;
    private Usuario usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_necesidades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtenerUsuario();
        cargarUI(view);
        iniciarListView();
    }

    private void iniciarListView() {
        cargarListView();
        lvMisNecesidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Necesidad n = (Necesidad)lvMisNecesidades.getItemAtPosition(i);
                DataNecesidades task = new DataNecesidades(getContext(), n);
                task.execute("modificarNecesidad");
                cargarListView();
            }
        });

    }

    private void cargarListView() {
        DataComedores task = new DataComedores(getContext(), usuario.getComedor(), lvMisNecesidades, listaNecesidades);
        task.execute("CargarNecesidades");
    }

    private void obtenerUsuario() {
        viewModel= new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuario=viewModel.getData().getValue();
    }

    private void cargarUI(View view) {
        lvMisNecesidades = (ListView) view.findViewById(R.id.lvMisNecesidades);
    }

    private void cambiarEstado() {

    }
}