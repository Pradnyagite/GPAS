package com.NRB.gpas;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentAdminHome.OnFragmentInteractionListener,
        FragmentAdminAllAppointments.OnFragmentInteractionListener,
        FragmentAdminTrackUser.OnFragmentInteractionListener,
        FragmentAboutUs.OnFragmentInteractionListener,
        FragmentHelp.OnFragmentInteractionListener{

    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.admin_drawer_layout);

        navigationView = findViewById(R.id.admin_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.getMenu().findItem(R.id.drawer_admin_home).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_admin_layout, new FragmentAdminHome()).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.drawer_admin_home:
                selectedFragment = new FragmentAdminHome();
                break;
            case R.id.drawer_admin_all_appointments:
                selectedFragment = new FragmentAdminAllAppointments();
                break;
            case R.id.drawer_admin_track_user:
                selectedFragment = new FragmentAdminTrackUser();
                break;
            case R.id.drawer_admin_about_us:
                selectedFragment = new FragmentAboutUs();
                break;
            case R.id.drawer_admin_help:
                selectedFragment = new FragmentHelp();
                break;

        }

        navigationView.getMenu().findItem(item.getItemId()).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_admin_layout, selectedFragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.admin_logout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()== R.id.admin_logout_btn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(AdminHome.this, "Logged out", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
