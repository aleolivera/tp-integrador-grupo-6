package com.example.comedores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.comedores.adapters.ViewPagerAdapter;
import com.example.comedores.conexion.DataReporte;
import com.example.comedores.entidades.Reporte;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PrincipalSupervisor extends AppCompatActivity {
    public Usuario usuario;
    private UsuarioViewModel usuarioViewModel;
    private TextView tvTitulo;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_supervisor);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        cargarViewModel();
        cargarUI();
        cargarViewPagerAdapter();
        cargarTabLayout();
    }

    private void cargarTabLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void cargarViewPagerAdapter() {
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(vpAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void cargarUI() {
        tvTitulo = (TextView) findViewById(R.id.tvPrincipalSupervisor);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutSupervidor);
        viewPager = (ViewPager2) findViewById(R.id.vpSupervisor);

        tvTitulo.setText("Supervisor: " + usuario.getNombre() + " " + usuario.getApellido());

    }

    private void cargarViewModel(){ //Carga el usuario en el activity para que sea accesible para los fragments
        usuarioViewModel=new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.setData(usuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_comedor_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cerrar_sesion:
                cerrarSession();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSession() {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}