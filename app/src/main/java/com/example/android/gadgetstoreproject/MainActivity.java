package com.example.android.gadgetstoreproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.gadgetstoreproject.activities.ProfileActivity;
import com.example.android.gadgetstoreproject.authentication.LoginActivity;
import com.example.android.gadgetstoreproject.ui.cart.UserCartFragment;
import com.example.android.gadgetstoreproject.ui.category.CategoryFragment;
import com.example.android.gadgetstoreproject.ui.home.HomeFragment;
import com.example.android.gadgetstoreproject.ui.order.UserOrderFragment;
import com.example.android.gadgetstoreproject.ui.product.NewProductFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progressBar;
    private Button signIn;

    FirebaseAuth mAuth;

    DrawerLayout drawerLayout;
    NavigationView navigationView, bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        //Toolbar
        setSupportActionBar(toolbar);

        //Hide or show menu
        Menu menu = navigationView.getMenu();
        if(mAuth.getCurrentUser() == null) {
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
        } else{
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
        }

        //To make sure that fragments are clickable
        navigationView.bringToFront();
        //Navigation Drawer Menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    //Closes navigation menu when back button is pressed
    @Override
    public void onBackPressed() {
        //Checking if navigation is open
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_products:
                replaceFragment(new NewProductFragment());
                break;
            case R.id.nav_category:
                replaceFragment(new CategoryFragment());
                break;
            case R.id.nav_orders:
                replaceFragment(new UserOrderFragment());
                break;
            case R.id.nav_cart:
                replaceFragment(new UserCartFragment());
                break;
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.nav_profile:
                if(mAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    Toast.makeText(getApplicationContext(), "Wait you are already logged in", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "You are not logged in! Login first", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                break;

            case R.id.nav_share:
                Toast.makeText(getApplicationContext(), R.string.share, Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_rate_us:
                Toast.makeText(getApplicationContext(), R.string.rate_us, Toast.LENGTH_LONG).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraint_layout, fragment);
        fragmentTransaction.commit();
    }
}