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
import com.example.comedores.entidades.Necesidad;
import com.example.comedores.entidades.Solicitud;

import java.util.List;

public class ListViewNecesidadesHomeAdapter extends ArrayAdapter<Necesidad> {
    private List<Necesidad> listaNecesidades;
    private Context context;
    private int resourseLayout;

    public ListViewNecesidadesHomeAdapter(@NonNull Context context, int resource, @NonNull List<Necesidad> listaNecesidades) {
        super(context, resource, listaNecesidades);
        this.listaNecesidades=listaNecesidades;
        this.context=context;
        this.resourseLayout=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= convertView;
        if(view==null)
            view= LayoutInflater.from(context).inflate(resourseLayout,null);

        Necesidad n= listaNecesidades.get(position);
        TextView tvDescripcion= (TextView)view.findViewById(R.id.tvNecesidadDescripcion);
        TextView tvTipo=(TextView) view.findViewById(R.id.tvNecesidadTipo);
        TextView tvPrioridad= (TextView)view.findViewById(R.id.tvNecesidadPrioridad);
        TextView tvEstado= (TextView)view.findViewById(R.id.tvNecesidadEstado);

        tvDescripcion.setText("Estado: "+String.valueOf(n.getDescripcion()));
        tvTipo.setText("Tipo: "+n.getTipo());
        tvPrioridad.setText("Prioridad: "+n.getPrioridad());
        tvEstado.setText("Estado: "+n.getEstado().getDescripcion());

        return view;
    }

}
