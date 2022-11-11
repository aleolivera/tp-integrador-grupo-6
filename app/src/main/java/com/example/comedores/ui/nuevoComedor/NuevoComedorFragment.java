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
import com.example.comedores.conexion.DataComedores;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.util.Validacion;
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
        //etDNI=(EditText) view.findViewById(R.id.etDNI);
        etProvincia=(EditText) view.findViewById(R.id.etProvincia);
        etLocalidad=(EditText)view.findViewById(R.id.etLocalidadRegistrarComedor);
        etDireccion=(EditText)view.findViewById(R.id.etDireccionAgregarComedor);
        //etEmail=(EditText)view.findViewById(R.id.etCorreoElectronicoAgregarComedor);
        etTelefono=(EditText)view.findViewById(R.id.etTelefonoAgregarComedor);
        btnRegistrar=(Button)view.findViewById(R.id.btnRegistrarComedor);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarComedor();
                usuario.setComedor(comedor);
                viewModel.setData(usuario);
            }
        });
    }

    private void cargarDatosPorDefecto() {
        etNombreResponsable.setText(usuario.getNombre());
        etApellidoResponsable.setText(usuario.getApellido());
        etDNI.setText(usuario.getDni());
    }

    private void cargarComedor() {
        comedor = new Comedor();
        comedor.setIdResponsable(usuario.getId());
        comedor.setRenacom(Long.parseLong(etRenacom.getText().toString()));
        comedor.setNombre(etNombreComedor.getText().toString());
        comedor.setDireccion(etDireccion.getText().toString());
        comedor.setLocalidad(etLocalidad.getText().toString());
        comedor.setProvincia(etProvincia.getText().toString());
        comedor.setTelefono(etTelefono.getText().toString());
        comedor.setNombreResponsable(etNombreResponsable.getText().toString());
        comedor.setApellidoResponsable(etApellidoResponsable.getText().toString());
    }

    private void registrarComedor() {
        String mensaje=validarDatos();
        if(mensaje.compareTo("")==0){
            cargarComedor();
            DataComedores task = new DataComedores(comedor, getContext());
            task.execute("AltaComedores");
        }
        else
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();

    }

    private String validarDatos() {
        String mensaje="";
        if(etRenacom.getText().toString().isEmpty() || etNombreComedor.getText().toString().isEmpty()||
            etNombreResponsable.getText().toString().isEmpty()|| etApellidoResponsable.getText().toString().isEmpty()||
            etDireccion.getText().toString().isEmpty()|| etLocalidad.getText().toString().isEmpty()||
            etProvincia.getText().toString().isEmpty()|| etTelefono.getText().toString().isEmpty()) {
            mensaje = "Debe completar todos los datos";
        }
        else {
            if (!Validacion.validarString(etRenacom.getText().toString(), Validacion.NUMEROS))
                mensaje = "Renacom: solo caracteres numericos";
            if (!Validacion.validarString(etNombreComedor.getText().toString(), Validacion.LETRAS_ESPACIOS))
                mensaje = "Nombre de comedor: solo letras y espacios";
            if (!Validacion.validarString(etNombreResponsable.getText().toString(), Validacion.LETRAS_ESPACIOS))
                mensaje = "Nombre responsable: solo caracteres alfabeticos";
            if (!Validacion.validarString(etApellidoResponsable.getText().toString(), Validacion.LETRAS_ESPACIOS))
                mensaje = "Apellido: solo caracteres alfabeticos";
            if (!Validacion.validarString(etDireccion.getText().toString(), Validacion.LETRAS_NUMEROS_ESPACIOS))
                mensaje = "Direccion: solo caracteres alfanumericos";
            if (!Validacion.validarString(etLocalidad.getText().toString(), Validacion.LETRAS_ESPACIOS))
                mensaje = "Localidad: solo caracteres alfabeticos";
            if (!Validacion.validarString(etProvincia.getText().toString(), Validacion.LETRAS_ESPACIOS))
                mensaje = "Provincia: solo caracteres alfabeticos";
            if (!Validacion.validarString(etTelefono.getText().toString(), Validacion.NUMEROS))
                mensaje = "Telefono: solo caracteres numericos";
        }
        /*
        etDNI=(EditText) view.findViewById(R.id.etDNI);
        etProvincia=(EditText) view.findViewById(R.id.etProvincia);
        etLocalidad=(EditText)view.findViewById(R.id.etLocalidadRegistrarComedor);
        etDireccion=(EditText)view.findViewById(R.id.etTelefonoAgregarComedor);
        etEmail=(EditText)view.findViewById(R.id.etCorreoElectronicoAgregarComedor);
        etTelefono=(EditText)view.findViewById(R.id.etTelefonoAgregarComedor);
        * */
        return mensaje;
    }


}