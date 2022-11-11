package com.example.comedores;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.comedores.conexion.DataComedores;
import com.example.comedores.conexion.DataEstados;
import com.example.comedores.conexion.DataReporte;
import com.example.comedores.conexion.DataTipos;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.ComedoresViewModel;
import com.example.comedores.viewmodels.UsuarioViewModel;

import java.util.List;

public class BuscarComedor extends Fragment {

    private View view;
    private ComedoresViewModel viewModel;
    private Comedor comedor;
    private ListView lvComedores;
    private List<Comedor> listaComedores;

    public BuscarComedor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_buscar_comedor, container, false);
        view = inflater.inflate(R.layout.fragment_buscar_comedor, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ComedoresViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        comedor = viewModel.getData().getValue();
        cargarUI();
        iniciarListView();



    }

    private void iniciarListView() {
        cargarListView();
        lvComedores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //VerlvReportes((Reportes) lvReportes.getItemAtPosition(position));
            }
        });
    }

    private void cargarListView() {

        DataComedores task = new DataComedores(getContext(), listaComedores, lvComedores);

        task.execute("listarComedores");
    }

    private void cargarUI() {

        lvComedores = (ListView) view.findViewById(R.id.lvComedores);
    }

}