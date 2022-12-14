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
import com.example.comedores.viewmodels.UsuarioViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
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
    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuario= (Usuario) getIntent().getSerializableExtra("usuario");
        cargarViewModel();

        binding = ActivityPrincipalComedorAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPrincipalComedorAdmin.toolbar);
        /*
        binding.appBarPrincipalComedorAdmin.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        PrincipalComedorAdmin.this,
                        "Falta implementar boton flotante",
                        Toast.LENGTH_SHORT).show();
            }
        });

         */
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_principal_admin, R.id.nav_mis_necesidades, R.id.nav_nueva_necesidad, R.id.nav_Mis_Reportes)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal_comedor_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        cargarHeader(navigationView.getHeaderView(0));
        verificarComedor(navigationView);
    }

    private void cargarViewModel(){ //Carga el usuario en el activity para que sea accesible para los fragments
        usuarioViewModel=new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.setData(usuario);
    }
    private void cargarHeader(View header) {
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

    private void verificarComedor(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        if(usuario.getComedor()==null){
            MenuItem itemMisNecesidades = menu.findItem(R.id.nav_mis_necesidades);
            MenuItem itemNuevaNecesidad = menu.findItem(R.id.nav_nueva_necesidad);
            MenuItem itemReportes = menu.findItem(R.id.nav_Mis_Reportes);
            itemMisNecesidades.setVisible(false);
            itemNuevaNecesidad.setVisible(false);
        }
        else if(usuario.getComedor().getEstado().getId()==1){
            MenuItem itemMisNecesidades = menu.findItem(R.id.nav_mis_necesidades);
            MenuItem itemAgregarComedor = menu.findItem(R.id.nav_registrar_comedor);
            MenuItem itemNuevaNecesidad = menu.findItem(R.id.nav_nueva_necesidad);
            MenuItem itemReportes = menu.findItem(R.id.nav_Mis_Reportes);

            itemMisNecesidades.setVisible(false);
            itemAgregarComedor.setVisible(false);
            itemNuevaNecesidad.setVisible(false);
        }
        else{
            MenuItem itemAgregarComedor = menu.findItem(R.id.nav_registrar_comedor);
            itemAgregarComedor.setVisible(false);
        }
        /*
        if(usuario.getComedor()!=null) {
            MenuItem itemAgregarComedor = menu.findItem(R.id.nav_registrar_comedor);
            itemAgregarComedor.setVisible(false);
        }

         */
    }


}