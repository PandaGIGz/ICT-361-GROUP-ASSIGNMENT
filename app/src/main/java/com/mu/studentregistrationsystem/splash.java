package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the splash screen layout
        setContentView(R.layout.activity_splash);

        // Wait for 2.5 seconds then transition to Login screen
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(splash.this, login.class);
            startActivity(intent);

            // Close Splash Activity
            finish();
        }, 2500);
    }
}
