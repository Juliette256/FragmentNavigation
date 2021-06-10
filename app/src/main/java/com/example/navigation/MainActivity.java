package com.example.navigation;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigation.ui.gallery.GalleryFragment;
import com.example.navigation.ui.home.HomeFragment;
import com.example.navigation.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private AppBarConfiguration mAppBarConfiguration;


    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null) {
           HomeFragment homeFragment = new HomeFragment();
           FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           ft.replace(R.id.content, homeFragment).commit();
        }

         setNavigationDrawer();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


     private void setNavigationDrawer(){
    drawer = findViewById(R.id.drawer_layout);


    NavigationView navigationView = findViewById(R.id.nav_view);
    View headerView = navigationView.getHeaderView(0);
    TextView email = headerView.findViewById(R.id.tvEmailAddress);
    String Email=getIntent().getExtras().getString("Email");
    email.setText(Email);

        ImageView imageView= headerView.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditPic.class));
            }
        });

    mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
            .setDrawerLayout(drawer)
                .build();

    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);



     }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment frag = null;
        String title="";
        if (id == R.id.nav_home) {
            title= "Add Member";
            frag = new HomeFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, frag);
            transaction.commit();
            getSupportActionBar().setTitle(title);
            drawer.closeDrawers();
            return true;

        } else if (id == R.id.nav_gallery) {
            title= "List of Members";
            frag = new GalleryFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, frag);
            transaction.detach(frag).attach(frag).commit();
            getSupportActionBar().setTitle(title);
            drawer.closeDrawers();
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(MainActivity.this, Login.class));
    }

}