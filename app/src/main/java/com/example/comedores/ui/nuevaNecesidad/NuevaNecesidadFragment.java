package com.example.comedores.ui.nuevaNecesidad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.conexion.DataNecesidades;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Tipo;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.util.Validacion;
import com.example.comedores.viewmodels.UsuarioViewModel;

public class NuevaNecesidadFragment extends Fragment {

    private TextView tvNumeroPrioridad;
    private Spinner spTiposNecesidad;
    private SeekBar sbPrioridad;
    private EditText etDescripcion;
    private Button btnAgregar;

    private Usuario usuario;
    private Necesidad necesidad;
    private UsuarioViewModel viewModel;
    private final String [] tiposNecesidad = {
            "alimentos", "medicinas",
            "herramientas", "voluntarios",
            "utiles escolares", "utensillos"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_necesidad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtenerUsuario();
        cargarUi(view);

    }

    private void obtenerUsuario() {
        viewModel= new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuario=viewModel.getData().getValue();
    }

    private void cargarUi(View view) {
        spTiposNecesidad= (Spinner) view.findViewById(R.id.spTipoNecesidad);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tiposNecesidad);
        spTiposNecesidad.setAdapter(adapter);
        tvNumeroPrioridad = (TextView) view.findViewById(R.id.tvNumeroPrioridad);
        sbPrioridad = (SeekBar) view.findViewById(R.id.sbPrioridad);
        etDescripcion = (EditText) view.findViewById(R.id.etDescripcion);
        btnAgregar = (Button) view.findViewById(R.id.btnAgregarNecesidad);

        sbPrioridad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvNumeroPrioridad.setText(String.valueOf(i + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarNecesidad();
                etDescripcion.setText("");
            }
        });

    }

    private void registrarNecesidad() {
        cargarNecesidad();
        String mensaje= validarDatos();
        if(mensaje.compareTo("")==0) {
            DataNecesidades task = new DataNecesidades(getContext(), (int) usuario.getComedor().getId(), necesidad);
            task.execute("agregarNecesidad");
        } else {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarNecesidad() {
        necesidad = new Necesidad();
        necesidad.setPrioridad(Integer.parseInt(tvNumeroPrioridad.getText().toString()));
        necesidad.setDescripcion(etDescripcion.getText().toString());
        necesidad.setEstado(new Estado(1, ""));
        necesidad.setTipo(new Tipo(spTiposNecesidad.getSelectedItemPosition() + 1, ""));

    }

    private String validarDatos() {
        String mensaje = "";
        if(etDescripcion.getText().toString().isEmpty()) {
            mensaje = "Ingrese una descripción";
        }
        return mensaje;
    }
}