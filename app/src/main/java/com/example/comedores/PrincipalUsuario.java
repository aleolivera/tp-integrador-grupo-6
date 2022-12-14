package com.example.comedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comedores.databinding.ActivityPrincipalUsuarioBinding;
import com.example.comedores.entidades.Usuario;
import com.example.comedores.viewmodels.UsuarioViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class PrincipalUsuario extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalUsuarioBinding binding;
    private Usuario usuario;
    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        cargarViewModel();

        binding = ActivityPrincipalUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPrincipalUsuario.toolbar);
        /*
        binding.appBarPrincipalUsuario.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sin implementar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_homeUsuarioFinalFragment, R.id.nav_buscarComedor, R.id.nav_Mis_Reportes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal_usuario);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        cargarHeader(navigationView.getHeaderView(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal_usuario, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal_usuario);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                cerrarSession();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSession() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void cargarViewModel() { //Carga el usuario en el activity para que sea accesible para los fragments
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.setData(usuario);
    }

    private void cargarHeader(View header) {
        TextView tvNdHeaderTitulo = (TextView) header.findViewById(R.id.tvNdHeaderTitulo);
        TextView tvNdHeaderSubtitulo = (TextView) header.findViewById(R.id.tvNdHeaderSubtitulo);
        tvNdHeaderTitulo.setText("Bienvenido!");
        tvNdHeaderSubtitulo.setText(usuario.getEmail());
    }
}