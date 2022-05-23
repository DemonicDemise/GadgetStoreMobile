package com.example.android.gadgetstoreproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.activities.AdminPageActivity;
import com.example.android.gadgetstoreproject.authentication.LoginActivity;
import com.example.android.gadgetstoreproject.models.UserModel;
import com.example.android.gadgetstoreproject.ui.cart.UserCartFragment;
import com.example.android.gadgetstoreproject.ui.category.CategoryFragment;
import com.example.android.gadgetstoreproject.ui.favourite.FavouriteFragment;
import com.example.android.gadgetstoreproject.ui.home.HomeFragment;
import com.example.android.gadgetstoreproject.ui.order.UserOrderFragment;
import com.example.android.gadgetstoreproject.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView logoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();

        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        //Toolbar
        setSupportActionBar(toolbar);

        logoImg = findViewById(R.id.logoImg);
        logoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

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

        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.nav_header_name);
        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);
        CircleImageView headerImg = headerView.findViewById(R.id.nav_header_img);
        ImageView navImageView = headerView.findViewById(R.id.nav_background_img);
        //LinearLayout li = headerView.findViewById(R.id.nav_layout_background_ly);

        if(mAuth.getCurrentUser() != null) {
            mDb.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserModel userModel = snapshot.getValue(UserModel.class);

                            headerName.setText(userModel.name);
                            headerEmail.setText(userModel.email);
                            Glide.with(getApplicationContext()).load(userModel.getProfileImg()).into(headerImg);
                            Glide.with(getApplicationContext()).load(userModel.getNavBackgroundImg()).into(navImageView);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "You are not logged in! Login first", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
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
            case R.id.nav_favourite:
                replaceFragment(new FavouriteFragment());
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
            case R.id.nav_admin:
                startActivity(new Intent(getApplicationContext(), AdminPageActivity.class));
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
                    replaceFragment(new ProfileFragment());
                    Toast.makeText(getApplicationContext(), "Wait you are already logged in", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "You are not logged in! Login first", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                break;
            case R.id.nav_rate_us:
                Toast.makeText(getApplicationContext(), R.string.rate_us, Toast.LENGTH_LONG).show();
                break;
        }
        logoImg.setVisibility(View.GONE);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraint_layout, fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "Application stopped", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Application destroyed", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "Application restarted", Toast.LENGTH_LONG).show();
        replaceFragment(new HomeFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Application is active", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Application started", Toast.LENGTH_LONG).show();
        replaceFragment(new HomeFragment());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "Application paused", Toast.LENGTH_LONG).show();
    }
}