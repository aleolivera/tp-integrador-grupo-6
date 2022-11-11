package com.example.comedores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.comedores.conexion.DataEstados;
import com.example.comedores.conexion.DataReporte;
import com.example.comedores.conexion.DataTipos;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

import java.util.List;

public class Reportes extends Fragment {

    private View view;
    private UsuarioViewModel viewModel;

    private Usuario usuario;
    private EditText etReportId;
    public Button btnBuscar;
    private Spinner spnTipo;
    private Spinner spnEstado;
    private ListView lvReportes;
    private List<Reporte> listaReportes;
    DataTipos dtipos;

    public Button btnBotonesReportes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_reportes, container, false);
        view = inflater.inflate(R.layout.fragment_reportes, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuario = viewModel.getData().getValue();
        cargarUI();
        iniciarListView();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusquedaFiltro();
            }
        });


        btnBotonesReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irABotonesReportes();


            }
        });

        lvReportes.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

    }

    private void iniciarListView() {
        cargarListView("0", "0", "1", "0");
        lvReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //VerlvReportes((Reportes) lvReportes.getItemAtPosition(position));
            }
        });
    }

    private void cargarListView(String IdUsuario, String IdReporte, String EstadoId, String TipoId) {

        DataReporte task = new DataReporte(getContext(), listaReportes, lvReportes);

        task.execute("ListarReporte", IdUsuario, IdReporte, EstadoId, TipoId);
    }


    private void cargarUI() {
        etReportId = (EditText) view.findViewById(R.id.etReporteId);

        spnTipo = (Spinner) view.findViewById(R.id.spTipoReporte);
        DataTipos taskTipos = new DataTipos(spnTipo, getContext());
        taskTipos.execute("2");//Obtengo los tipos de reporte

        spnEstado = (Spinner) view.findViewById(R.id.spEstadoReporte);
        DataEstados taskEstado = new DataEstados(spnEstado, getContext());
        taskEstado.execute("2");//Obtengo los tipos de reporte

        btnBuscar = (Button) view.findViewById(R.id.btnBuscarReporte);
        lvReportes = (ListView) view.findViewById(R.id.lvReportes);

        btnBotonesReportes = (Button) view.findViewById(R.id.btnBotonesReportes);
    }

    private void BusquedaFiltro() {
        String IdReporte = etReportId.getText().toString();

        if (IdReporte.isEmpty() || IdReporte == "") {
            IdReporte = "0";
        }

        Estado ESelect = (Estado) spnEstado.getSelectedItem();
        Integer EstadoId = ESelect.getId();

        Tipo tSelect = (Tipo) spnTipo.getSelectedItem();
        Integer TipoId = tSelect.getId();

        cargarListView("0", IdReporte, EstadoId.toString(), TipoId.toString());

    }

    private void irABotonesReportes() {

        Intent intent = new Intent(getActivity(), ReportesBotones.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }
}