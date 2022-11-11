package com.example.comedores.ui.nuevoComedor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.conexion.DataComedores;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Estado;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.ui.homeAdminComedor.HomeAdminComedorFragment;
import com.example.comedores.util.Validacion;
import com.example.comedores.viewmodels.UsuarioViewModel;

public class NuevoComedorFragment extends Fragment {

    private EditText etRenacom, etNombreComedor, etNombreResponsable,
            etApellidoResponsable, etProvincia ,
        etLocalidad, etDireccion, etTelefono;
    private Button btnRegistrar;
    private Spinner spProvincias;
    private UsuarioViewModel viewModel;
    public Usuario usuario;
    public Comedor comedor;
    private final String [] provincias={"Buenos Aires", "Ciudad Autónoma de Bs. As.",
            "Catamarca","Chaco","Chubut","Córdoba","Corrientes","Entre Ríos",
            "Formosa","Jujuy","La Pampa","La Rioja","Mendoza","Misiones","Neuquén",
            "Río Negro", "Salta","San Juan","San Luis","Santa Cruz","Santa Fe",
            "Santiago del Estero","Tierra del Fuego", "Antártida","Tucumán"};

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
        //etProvincia=(EditText) view.findViewById(R.id.etProvincia);
        etLocalidad=(EditText)view.findViewById(R.id.etLocalidadRegistrarComedor);
        etDireccion=(EditText)view.findViewById(R.id.etDireccionAgregarComedor);
        etTelefono=(EditText)view.findViewById(R.id.etTelefonoAgregarComedor);

        spProvincias=(Spinner)view.findViewById(R.id.spProvinciasComedor);
        ArrayAdapter<String> adapterProvincias= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,provincias);
        spProvincias.setAdapter(adapterProvincias);

        btnRegistrar=(Button)view.findViewById(R.id.btnRegistrarComedor);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarComedor();
                buscarComedorPorIdUsuario();
                usuario.setComedor(comedor);
                viewModel.setData(usuario);
                //volverAHome();
            }
        });
    }

    private void cargarDatosPorDefecto() {
        etNombreResponsable.setText(usuario.getNombre());
        etApellidoResponsable.setText(usuario.getApellido());
        //etDNI.setText(usuario.getDni());
    }

    private void cargarComedor() {
        comedor = new Comedor();
        comedor.setIdResponsable(usuario.getId());
        comedor.setRenacom(Long.parseLong(etRenacom.getText().toString()));
        comedor.setNombre(etNombreComedor.getText().toString());
        comedor.setDireccion(etDireccion.getText().toString());
        comedor.setLocalidad(etLocalidad.getText().toString());
        comedor.setProvincia(spProvincias.getSelectedItem().toString());
        comedor.setTelefono(etTelefono.getText().toString());
        comedor.setNombreResponsable(etNombreResponsable.getText().toString());
        comedor.setApellidoResponsable(etApellidoResponsable.getText().toString());
        comedor.setEstado(new Estado(1,null));
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
                etTelefono.getText().toString().isEmpty()) {
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
    private void volverAHome(){
        Fragment fragment = new HomeAdminComedorFragment();
// Obtener el administrador de fragmentos a través de la actividad
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
// Definir una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
// Remplazar el contenido principal por el fragmento
        fragmentTransaction.replace(R.id.nav_view, fragment);
        fragmentTransaction.addToBackStack(null);
// Cambiar
        fragmentTransaction.commit();
    }
    private void buscarComedorPorIdUsuario(){
        DataComedores task= new DataComedores(comedor,getContext());
        task.execute("buscarComedorPorIdUsuario");
    }


}