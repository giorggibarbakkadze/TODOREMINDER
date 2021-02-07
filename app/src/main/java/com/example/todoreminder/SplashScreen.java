package com.example.todoreminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.os.Looper;


public class SplashScreen extends AppCompatActivity {
    private long LOADING_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        Intent intent = new Intent( SplashScreen.this,LogInAtctivity.class);
        startActivity(intent);
        finish();



            }
        }, LOADING_TIME);

    }
}