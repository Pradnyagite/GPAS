package com.NRB.gpas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);


    }

    public void forgotPass(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

}
