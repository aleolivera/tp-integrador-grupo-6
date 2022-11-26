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
import com.example.comedores.entidades.Reporte;

import java.util.List;

public class ListViewReportesAdapter extends ArrayAdapter<Reporte> {

    private List<Reporte> listaReportes;
    private Context context;
    private int resourseLayout;


    public ListViewReportesAdapter(@NonNull Context context, int resource, @NonNull List<Reporte> listaReportes) {
        super(context, resource, listaReportes);
        this.listaReportes = listaReportes;
        this.context = context;
        this.resourseLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(resourseLayout, null);

        Reporte r = listaReportes.get(position);

        TextView tvReporteId = (TextView) view.findViewById(R.id.tvReporteId);
        TextView tvEstadoReporte = (TextView) view.findViewById(R.id.tvEstadoReporte);
        TextView tvFechaAltaReporte = (TextView) view.findViewById(R.id.tvFechaAltaReporte);
        TextView tvTipoReporte = (TextView) view.findViewById(R.id.tvTipoReporte);
        TextView tvUsuarioAltaReporte = (TextView) view.findViewById(R.id.tvUsuarioAltaReporte);

        tvReporteId.setText("Reporte Id: " + String.valueOf(r.getId()));
        tvEstadoReporte.setText("Estado: " + r.getEstado().getDescripcion());
        tvFechaAltaReporte.setText("Fecha Alta: " + r.getFechaAlta().toString());
        tvTipoReporte.setText("Tipo: " + String.valueOf(r.getTipo().getDescripcion()));
        tvUsuarioAltaReporte.setText("Usuario: " + String.valueOf(r.getUsuario().getEmail()));

        switch (r.getEstado().getId()){
            case 1:
                tvEstadoReporte.setTextColor(view.getResources().getColor(R.color.alert));
                break;
            default:
                tvEstadoReporte.setTextColor(view.getResources().getColor(R.color.estado_habilitado));
                break;
        }

        return view;
    }
}
