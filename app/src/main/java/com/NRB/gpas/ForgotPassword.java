package com.NRB.gpas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    EditText email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.et_forgotpass_email);
    }

    public void sendEmail(View view) {
        String emailid = email.getText().toString();

        if (TextUtils.isEmpty(emailid)) {
            Toast.makeText(getApplicationContext(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!emailid.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Please provide your valid Email address", Toast.LENGTH_SHORT).show();
                return;
            } else {


                Toast.makeText(getApplicationContext(), "E-mail has been sent!", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
