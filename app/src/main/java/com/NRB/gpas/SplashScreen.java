package com.NRB.gpas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                        Intent homeIntent = new Intent(SplashScreen.this, UserLoginActivity.class);
                        startActivity(homeIntent);
                        finish();

                }
            }, 2000);

    }
}