package com.example.comedores.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comedores.R;
import com.example.comedores.entidades.Solicitud;

import java.util.List;

public class ListViewSolicitudesAdapter extends ArrayAdapter<Solicitud> {

    private List<Solicitud>listaSolicitudes;
    private Context context;
    private int resourseLayout;

    public ListViewSolicitudesAdapter(@NonNull Context context, int resource, @NonNull List<Solicitud> listaSolicitudes) {
        super(context, resource, listaSolicitudes);
        this.listaSolicitudes=listaSolicitudes;
        this.context=context;
        this.resourseLayout=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= convertView;
        if(view==null)
            view= LayoutInflater.from(context).inflate(resourseLayout,null);

        Solicitud s= listaSolicitudes.get(position);
        TextView tvRenacom= (TextView)view.findViewById(R.id.tvRenacom);
        TextView tvNombreComedor=(TextView) view.findViewById(R.id.tvNombreComedor);
        TextView tvFechaSolicitud= (TextView)view.findViewById(R.id.tvFechaSolicitud);
        TextView tvIdSolicitud= (TextView)view.findViewById(R.id.tvIdSolicitud);

        tvRenacom.setText("Renacom: "+String.valueOf(s.getComedor().getRenacom()));
        tvNombreComedor.setText("Comedor: "+s.getComedor().getNombre());
        tvFechaSolicitud.setText("Fecha: "+s.getFechaAlta().toString());
        tvIdSolicitud.setText("ID: "+String.valueOf(s.getId()));

        return view;
    }

}
