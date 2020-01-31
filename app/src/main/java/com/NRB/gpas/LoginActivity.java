package com.NRB.gpas;

import android.app.ProgressDialog;
import android.content.Intent;
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


public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private ProgressDialog mProLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.login_et_un);
        password = (EditText) findViewById(R.id.login_et_pass);
        login = (Button) findViewById(R.id.btn_login_login);
    }

    public void login(View view) {
        //Intent intent = new Intent(this, MainActivity.class);


        String user = username.getText().toString();
        String pass = password.getText().toString();



        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else {

            if (!user.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Please provide your valid Email address", Toast.LENGTH_SHORT).show();
                return;
            } else {

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        signin(user, pass);

        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);


    }

    public void forgotPass(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    private void signin(final String user, String pass) {

        final String tempuser = user, temppassword = pass;
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    mProLogin.dismiss();
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("hgFkE0JFEsbiAF8xxIlsRaBrqr63")) {

                        Intent loginIntent = new Intent(LoginActivity.this, UserMapsActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);
                    } else {

                        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);
                    }

                } else {
                    mProLogin.dismiss();
                    Toast.makeText(getApplication(), "Username Or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
