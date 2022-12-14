package com.example.comedores.ui.homeAdminComedor;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;


public class HomeAdminComedorFragment extends Fragment {
    private UsuarioViewModel viewModel;
    private TextView tvNombreComedor,tvProvincia,tvLocalidad,tvDireccion,tvTelefono,tvRenacom,tvResponsable,tvEstado,tvSinComedor;
    public Usuario usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_admin_comedor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtenerUsuario();

        cargarUI(view);
        if(usuario.getComedor()==null)
            ocultarComedor();
        else{
            tvSinComedor.setVisibility(View.GONE);
            iniciarObserverViewModel();
        }
    }

    private void cargarUI(View view) {
        tvNombreComedor=(TextView) view.findViewById(R.id.tvNombreComedor);
        tvProvincia=(TextView)view.findViewById(R.id.tvProvincia);
        tvLocalidad=(TextView)view.findViewById(R.id.tvLocalidad);
        tvDireccion=(TextView)view.findViewById(R.id.tvDireccion);
        tvTelefono=(TextView)view.findViewById(R.id.tvTelefono);
        tvRenacom=(TextView)view.findViewById(R.id.tvRenacom);
        tvResponsable=(TextView)view.findViewById(R.id.tvResponsable);
        tvEstado=(TextView)view.findViewById(R.id.tvEstado);
        tvSinComedor=(TextView)view.findViewById(R.id.tvSinComedor);
    }

    private void ocultarComedor() {
        tvNombreComedor.setVisibility(View.GONE);
        tvProvincia.setVisibility(View.GONE);
        tvLocalidad.setVisibility(View.GONE);
        tvDireccion.setVisibility(View.GONE);
        tvTelefono.setVisibility(View.GONE);
        tvRenacom.setVisibility(View.GONE);
        tvResponsable.setVisibility(View.GONE);
        tvEstado.setVisibility(View.GONE);
    }

    private void obtenerUsuario() {
        viewModel= new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        usuario=viewModel.getData().getValue();
    }

    private void iniciarObserverViewModel() {
        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                tvNombreComedor.setText(usuario.getComedor().getNombre());
                tvProvincia.setText("Provincia: "+usuario.getComedor().getProvincia());
                tvLocalidad.setText("Localidad: "+usuario.getComedor().getLocalidad());
                tvDireccion.setText("Direccion: "+usuario.getComedor().getDireccion());
                tvTelefono.setText("Telefono: "+usuario.getComedor().getTelefono());
                tvRenacom.setText("Renacom: "+String.valueOf(usuario.getComedor().getRenacom()));
                tvResponsable.setText("Responsable: "+usuario.getComedor().getNombreResponsable()+" "+usuario.getComedor().getApellidoResponsable());
                tvEstado.setText("Estado: "+usuario.getComedor().getEstado().getDescripcion());
                colorearEstado();
            }
        });
    }

    private void colorearEstado(){
        if(usuario.getComedor().getEstado().getId()==2)
            tvEstado.setTextColor(getResources().getColor(R.color.estado_habilitado));
        else
            tvEstado.setTextColor(getResources().getColor(R.color.alert));
    }
}