package com.NRB.gpas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void user(View v){
        Intent i=new Intent(MainActivity.this,UserMapsActivity.class);
        startActivity(i);
    }
    public void admin(View v){
        Intent i=new Intent(MainActivity.this,AdminMapActivity.class);
        startActivity(i);
    }
}
