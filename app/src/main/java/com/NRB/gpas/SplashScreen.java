package com.NRB.gpas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (mAuth.getCurrentUser() != null) {
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("2Rz94bpVCmabEbp57svQ83q4Cpb2")) {

                        Intent loginIntent = new Intent(SplashScreen.this, AdminHome.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);

                    } else if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("YRX6heYKtNc6HXbRU2lmVLk7I8k2")){

                        Intent loginIntent = new Intent(SplashScreen.this, SecurityPanel.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);

                    }
                } else {

                    Intent homeIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
                    startActivity(homeIntent);
                    finish();
                }

            }
        }, 2000);

    }
}
