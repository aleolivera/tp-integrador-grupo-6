package com.example.comedores;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.comedores.conexion.DataComedores;
import com.example.comedores.conexion.DataReporte;
import com.example.comedores.entidades.Comedor;
import com.example.comedores.entidades.Reporte;

import java.util.List;

public class BuscarComedor extends AppCompatActivity {

    private ListView lvComedores;
    private List<Comedor> listaComedores;

    ArrayAdapter<Comedor> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_comedor);

        lvComedores = findViewById(R.id.lstComedores);

        DataComedores task = new DataComedores(listaComedores, lvComedores);

        task.execute("listarComedores");
/*
        arrayAdapter = new ArrayAdapter<Comedor>(this, android.R.layout.simple_list_item_1, task.getComedores());
        lstComedores.setAdapter(arrayAdapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.setTitle("Buscar Comedor");

        getMenuInflater().inflate(R.menu.menu_buscar_comedor, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar Comedor");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}