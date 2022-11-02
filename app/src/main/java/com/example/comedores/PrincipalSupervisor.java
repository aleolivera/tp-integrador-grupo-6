package com.example.comedores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import com.example.comedores.adapters.ViewPagerAdapter;
import com.example.comedores.entidades.Usuario;
import com.google.android.material.tabs.TabLayout;

public class PrincipalSupervisor extends AppCompatActivity {
    private Usuario usuario;
    private TextView tvTitulo;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_supervisor);
        usuario=(Usuario)getIntent().getSerializableExtra("usuario");

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
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void cargarViewPagerAdapter() {
        ViewPagerAdapter vpAdapter= new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(vpAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void cargarUI() {
        tvTitulo= (TextView)findViewById(R.id.tvPrincipalSupervisor);
        tabLayout= (TabLayout)findViewById(R.id.tabLayoutSupervidor);
        viewPager = (ViewPager2) findViewById(R.id.vpSupervisor);
        tvTitulo.setText("Supervisor: "+usuario.getNombre()+" "+usuario.getApellido());
    }
}