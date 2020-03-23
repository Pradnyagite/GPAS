package com.NRB.gpas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements SignUpAdminDialog.SignUpAdminDialogListener {

    EditText username, password;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private ProgressDialog mProLogin;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.login_et_un);
        password = (EditText) findViewById(R.id.login_et_pass);
        login = (Button) findViewById(R.id.btn_login_login);
        mProLogin = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("AdminRequests");


    }

    public void login(View view) {
        //Intent intent = new Intent(this, MainActivity.class);
//        mProLogin.setMessage("Logging in Please Wait...");
//        mProLogin.show();

        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (checkInternetConnection()) {

            if (TextUtils.isEmpty(user)) {
                Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            } else {

                if (!user.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Please provide a valid Email address", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(getApplicationContext(), "Enter a valid Password", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        mProLogin.setTitle("Logging In");
                        mProLogin.setMessage("Please wait while we check your credentials");
                        mProLogin.setCanceledOnTouchOutside(false);

                        mProLogin.show();

                        signin(user, pass);
                    }
                }

            }

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }

//        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
//        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(loginIntent);


    }

    public void signUp(View view) {

        SignUpAdminDialog signUpAdminDialog = new SignUpAdminDialog();
        signUpAdminDialog.show(getSupportFragmentManager(),"Sign up admin dialog");
    }

    private void signin(final String user, String pass) {

        final String tempuser = user, temppassword = pass;
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                   mProLogin.dismiss();
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("2Rz94bpVCmabEbp57svQ83q4Cpb2")) {

                        Intent loginIntent = new Intent(LoginActivity.this, AdminHome.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);

                    } else if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("YRX6heYKtNc6HXbRU2lmVLk7I8k2")){

                        Intent loginIntent = new Intent(LoginActivity.this, SecurityPanel.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);

                    } else {
                        Toast.makeText(getApplication(), "Enter valid credentials", Toast.LENGTH_SHORT).show();
                    }
//                    else {
//
//                        Intent loginIntent = new Intent(LoginActivity.this, UserHome.class);
//                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(loginIntent);
//                    }

                } else {
                    mProLogin.dismiss();
                    Toast.makeText(getApplication(), "Username or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public void onBackPressedBtn(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void applyTexts(final String email, final String password) {

        final String id[] = email.split("@");
        databaseReference = firebaseDatabase.getReference().child("AdminRequests");

        mAuth.signInWithEmailAndPassword("admin@gmail.com", "admin123").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if(dataSnapshot.exists()){
                               List<Object> map = (List<Object>) dataSnapshot.getValue();
//                               HashMap map1 = (HashMap) dataSnapshot.getValue();
                                Toast.makeText(LoginActivity.this, map.size(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(LoginActivity.this, "zala", Toast.LENGTH_SHORT).show();
//                                for(int i = 0;;){
//
//                                }
//
                                databaseReference.child(id[0]).child("Email").setValue(email);
                                databaseReference.child(id[0]).child("Password").setValue(password);
                            }
                        }
//
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//
//                    FirebaseAuth.getInstance().signOut();

                } else {

                }
            }
        });


    }
}
