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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.comedores.adapters.ListViewSolicitudesAdapter;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Solicitud;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControlDeCuentasFragment extends Fragment {

    private View view;
    private UsuarioViewModel viewModel;
    private Usuario usuario;
    private List<Solicitud> listaSolicitudes= new ArrayList<Solicitud>();
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

        Comedor c1= new Comedor();
        Comedor c2= new Comedor();
        Comedor c3= new Comedor();
        c1.setId(1);
        c1.setNombre("comedor 1");
        c1.setRenacom(1234);
        c2.setId(2);
        c2.setNombre("comedor 2");
        c2.setRenacom(4321);
        c3.setId(2);
        c3.setNombre("comedor 2");
        c3.setRenacom(4321);

        listaSolicitudes=new ArrayList<Solicitud>();
        listaSolicitudes.add(new Solicitud(1,c1, LocalDate.parse("2019-10-11"),false,0));
        listaSolicitudes.add(new Solicitud(2,c2, LocalDate.parse("2018-01-12"),false,0));
        listaSolicitudes.add(new Solicitud(3,c2, LocalDate.parse("2018-01-12"),false,0));

        ListViewSolicitudesAdapter adapter= new ListViewSolicitudesAdapter(getContext(),R.layout.item_row_solicitudes,listaSolicitudes);
        lvSolicitudes=(ListView) view.findViewById(R.id.lvSolicitudes);
        lvSolicitudes.setAdapter(adapter);

    }
}