package com.example.comedores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comedores.conexion.DataEstados;
import com.example.comedores.conexion.DataReporte;
import com.example.comedores.conexion.DataTipos;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Reportes extends Fragment {
    private Usuario usuario;

    private EditText etReportId;
    public Button btnBuscar;
    private Spinner spnTipo;
    private Spinner spnEstado;
    private ListView listViewReportes;
    DataTipos dtipos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewRoot = inflater.inflate(R.layout.fragment_reportes, container, false);

        usuario = (Usuario) getActivity().getIntent().getSerializableExtra("usuario");

        cargarUI(viewRoot);

        ejecutarTask(listViewReportes, "0", "0", "1", "0");

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BusquedaFiltro();
            }
        });


        return viewRoot;
    }


    private void ejecutarTask(ListView ReportesLV, String IdUsuario, String IdReporte, String EstadoId, String TipoId) {
        DataReporte task = new DataReporte(usuario, getContext(), ReportesLV);
        task.execute(IdUsuario, IdReporte, EstadoId, TipoId);
    }

    private void cargarUI(View view) {
        etReportId = (EditText) view.findViewById(R.id.etReporteId);

        spnTipo = (Spinner) view.findViewById(R.id.spnTipo);
        DataTipos taskTipos = new DataTipos(spnTipo, getContext());
        taskTipos.execute("2");//Obtengo los tipos de reporte

        spnEstado = (Spinner) view.findViewById(R.id.spnEstado);
        DataEstados taskEstado = new DataEstados(spnEstado, getContext());
        taskEstado.execute("2");//Obtengo los tipos de reporte

        btnBuscar = (Button) view.findViewById(R.id.btnBuscarReportes);
        listViewReportes = (ListView) view.findViewById(R.id.listViewReportes);
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

        ejecutarTask(listViewReportes, "0", IdReporte, EstadoId.toString(), TipoId.toString());

    }
}