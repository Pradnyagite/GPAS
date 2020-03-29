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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConcernedPerson extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    FragmentConcernedPersonHome.OnFragmentInteractionListener,
    FragmentHelp.OnFragmentInteractionListener,
    ChangePasswordDialog.ChangePasswordDialogListener,
 VisitorCardDialog.VisitorCardDialogListener{

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView toolbarTitle;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concerned_person);
        Toolbar toolbar = findViewById(R.id.concerned_person_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.concerned_person_drawer_layout);

        toolbarTitle = findViewById(R.id.concerned_person_toolbar_title);

        navigationView = findViewById(R.id.concerned_person_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.getMenu().findItem(R.id.drawer_concerned_person_home).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_concerned_person_layout, new FragmentConcernedPersonHome()).commit();

        toolbarTitle.setText(navigationView.getMenu().findItem(R.id.drawer_concerned_person_home).getTitle());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        Fragment selectedFragment = new FragmentConcernedPersonHome();

        switch (item.getItemId()) {
            case R.id.drawer_concerned_person_home:
                selectedFragment = new FragmentConcernedPersonHome();
                break;

            case R.id.drawer_concerned_person_help:
                selectedFragment = new FragmentHelp();
                break;

        }

        item.setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_concerned_person_layout, selectedFragment).commit();
        drawer.closeDrawer(GravityCompat.START);


        toolbarTitle.setText(item.getTitle());


        return false;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.concerned_person_logout, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()== R.id.concerned_person_logout_btn) {
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
                            Toast.makeText(ConcernedPerson.this, "Logged out", Toast.LENGTH_SHORT).show();
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
        } else if (item.getItemId()== R.id.concerned_person_change_pass_btn) {
            ChangePasswordDialog changePasswordDialog =  new ChangePasswordDialog();
            changePasswordDialog.show(getSupportFragmentManager(), "Change Password Dialog");
        }

        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void applyTexts(String currentPassword, final String newPassword) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,currentPassword);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(ConcernedPerson.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(ConcernedPerson.this, "Password successfully changed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(ConcernedPerson.this, "Current password incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void applyTexts() {

    }
}
