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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentUserHome.OnFragmentInteractionListener,
        FragmentAboutUs.OnFragmentInteractionListener,
        FragmentHelp.OnFragmentInteractionListener,
        FragmentGiveFeedback.OnFragmentInteractionListener{

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView toolbarTitle;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.user_drawer_layout);

        navigationView = findViewById(R.id.user_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbarTitle = findViewById(R.id.user_toolbar_title);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.getMenu().findItem(R.id.drawer_user_home).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_user_layout, new UserMapsActivity()).commit();

        toolbarTitle.setText(navigationView.getMenu().findItem(R.id.drawer_user_home).getTitle());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.drawer_user_home:
                selectedFragment = new UserMapsActivity();
                break;
            case R.id.drawer_user_givefeed:
                selectedFragment = new FragmentGiveFeedback();
                break;
            case R.id.drawer_user_about_us:
                selectedFragment = new FragmentAboutUs();
                break;
            case R.id.drawer_user_help:
                selectedFragment = new FragmentHelp();
                break;

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container_user_layout, selectedFragment).commit();
        drawer.closeDrawer(GravityCompat.START);

        item.setChecked(true);
        toolbarTitle.setText(item.getTitle());

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
        menuInflater.inflate(R.menu.user_logout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()== R.id.user_logout_btn) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(UserHome.this, "Logged out", Toast.LENGTH_SHORT).show();
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
