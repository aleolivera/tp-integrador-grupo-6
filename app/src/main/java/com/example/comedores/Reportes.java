package com.example.comedores;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
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
import com.example.comedores.dialogs.ReporteDialog;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.ReportesViewModel;
import com.example.comedores.viewmodels.UsuarioViewModel;

import java.util.List;

public class Reportes extends Fragment {

    private View view;
    private UsuarioViewModel viewModel;
    private ReportesViewModel vmRep;

    private Usuario usuario;
    private EditText etReportId;
    public Button btnBuscar, btnReporteAplicacion;
    private Spinner spnTipo;
    private Spinner spnEstado;
    private ListView lvReportes;
    private List<Reporte> listaReportes;
    DataTipos dtipos;

    private Reporte reporte;

    public Button btnBotonesReportes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_reportes, container, false);
        view = inflater.inflate(R.layout.fragment_reportes, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        vmRep = new ViewModelProvider(requireActivity()).get(ReportesViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuario = viewModel.getData().getValue();
        reporte = new Reporte();
        ;
        cargarUI();
        iniciarListView();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusquedaFiltro();
            }
        });


        btnReporteAplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarReprote(1, 0);

            }
        });

       /* btnBotonesReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irABotonesReportes();


            }
        });*/


    }

    private void iniciarListView() {

        String idUsuario = String.valueOf(usuario.getId());
        if (usuario.getTipo() == 3) {
            idUsuario = "0";
        }
        cargarListView(idUsuario, "0", "1", "0");

        lvReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VerlvReportes((Reporte) lvReportes.getItemAtPosition(position));
            }
        });


    }


    private void VerlvReportes(Reporte reporte) {
        ReporteDialog dialog = new ReporteDialog(reporte, usuario);
        dialog.show(getActivity().getSupportFragmentManager(), "reprote dialog");
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
        spnTipo.setVisibility(View.INVISIBLE);

        spnEstado = (Spinner) view.findViewById(R.id.spEstadoReporte);
        DataEstados taskEstado = new DataEstados(spnEstado, getContext());
        taskEstado.execute("2");//Obtengo los tipos de reporte


        btnBuscar = (Button) view.findViewById(R.id.btnBuscarReporte);
        btnReporteAplicacion = (Button) view.findViewById(R.id.btnReportarApp);

        if (usuario.getTipo() == 3) {
            btnReporteAplicacion.setVisibility(View.INVISIBLE);
        }

        lvReportes = (ListView) view.findViewById(R.id.lvReportes);

        //btnBotonesReportes = (Button) view.findViewById(R.id.btnBotonesReportes);
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

        String idUsuario = String.valueOf(usuario.getId());
        if (usuario.getTipo() == 3) {
            idUsuario = "0";
        }
        cargarListView(idUsuario, IdReporte, EstadoId.toString(), TipoId.toString());

    }
/*

    private void irABotonesReportes() {

        Intent intent = new Intent(getActivity(), ReportesBotones.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }
*/


    private void cargarReprote(int tipo, int idreportado) {

        reporte = new Reporte();
        Tipo t = new Tipo(1, "");
        Estado e = new Estado(1, "");

        //Aca estan los ejemplos
        switch (tipo) {
            case 1://Aplicacion
            {
                t.setId(1);
                reporte.setIdReportado(0);
            }
            break;
            case 2://Usaurio
            {
                t.setId(2);
                reporte.setIdReportado(idreportado);
            }
            break;
            case 3://necesidad
            {
                t.setId(3);
                reporte.setIdReportado(idreportado);
            }
            break;

        }
        reporte.setUsuario(usuario);
        reporte.setTipo(t);
        reporte.setEstado(e);
        IrAReportesAlta(reporte);

        Intent intent = new Intent(getActivity(), ReportesAlta.class);

        intent.putExtra("reporte", reporte);
        intent.putExtra("usuario", usuario);

        startActivity(intent);

    }

    private void IrAReportesAlta(Reporte reporte) {


    }
}