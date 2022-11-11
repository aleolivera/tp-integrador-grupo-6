package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.comedores.adapters.ListViewComedoresAdapter;
import com.example.comedores.entidades.Comedor;

import java.util.List;

public class Comedores extends AppCompatActivity {

    Fragment buscarComedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedores);

        buscarComedor = new BuscarComedor();

        getSupportFragmentManager().beginTransaction().add(R.id.containerFragments, buscarComedor).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_buscar_comedor, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Buscar comedor");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}