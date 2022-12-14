package com.example.comedores.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comedores.R;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Reporte;

import java.util.List;

public class ListViewComedoresAdapter extends ArrayAdapter<Comedor> {

    private List<Comedor> listaComedores;
    private Context context;
    private int resourceLayout;

    public ListViewComedoresAdapter getListViewComedoresAdapter(){
        return this;
    }

    public ListViewComedoresAdapter(@NonNull Context context, int resource, @NonNull List<Comedor> listaComedores) {
        super(context, resource, listaComedores);
        this.listaComedores = listaComedores;
        this.context = context;
        this.resourceLayout = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(resourceLayout, null);

        Comedor c = listaComedores.get(position);

        TextView tvComedor = (TextView) view.findViewById(R.id.tvComedor);
        TextView tvDireccion = (TextView) view.findViewById(R.id.tvDireccion);
        tvComedor.setText(String.valueOf(c.getNombre()));
        tvDireccion.setText(c.getDireccion()+ ", " + c.getLocalidad());

        return view;
    }
}
