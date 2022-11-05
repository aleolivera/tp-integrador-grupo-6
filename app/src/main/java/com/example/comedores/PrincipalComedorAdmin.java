package com.example.comedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comedores.R;
import com.example.comedores.entidades.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comedores.databinding.ActivityPrincipalComedorAdminBinding;

public class PrincipalComedorAdmin extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalComedorAdminBinding binding;
    private Usuario usuario;
    private TextView tvNdHeaderTitulo;
    private TextView tvNdHeaderSubtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalComedorAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPrincipalComedorAdmin.toolbar);
        binding.appBarPrincipalComedorAdmin.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        PrincipalComedorAdmin.this,
                        "Falta implementar boton flotante",
                        Toast.LENGTH_SHORT).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_principal_admin, R.id.nav_mis_necesidades)
                .setOpenableLayout(drawer)
                .build();

        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal_comedor_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        cargarHeader(navigationView.getHeaderView(0));

    }

    private void cargarHeader(View v) {
        View header = v;
        TextView tvNdHeaderTitulo= (TextView)header.findViewById(R.id.tvNdHeaderTitulo);
        TextView tvNdHeaderSubtitulo=(TextView)header.findViewById(R.id.tvNdHeaderSubtitulo);
        tvNdHeaderTitulo.setText("Bienvenido!");
        tvNdHeaderSubtitulo.setText(usuario.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal_comedor_admin, menu);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal_comedor_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void cerrarSession(){
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }


}