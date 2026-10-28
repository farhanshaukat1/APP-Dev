package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SplashActivity - Entry point of the application.
 * Displays the app logo and name, then navigates to LoginActivity.
 * If the user is already logged in (via SharedPreferences), navigates to DashboardActivity.
 */
public class SplashActivity extends AppCompatActivity {

    // Splash display duration in milliseconds
    private static final long SPLASH_DURATION = 2000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Use a Handler to navigate after the splash delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            navigateFromSplash();
        }, SPLASH_DURATION);
    }

    /**
     * Decides where to navigate after the splash screen.
     * If the user is already logged in, go straight to Dashboard.
     * Otherwise, go to Login.
     */
    private void navigateFromSplash() {
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(AppConstants.KEY_IS_LOGGED_IN, false);

        Intent intent;
        if (isLoggedIn) {
            // User already authenticated - go to Dashboard
            intent = new Intent(SplashActivity.this, DashboardActivity.class);
        } else {
            // Not authenticated - go to Login
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }

        startActivity(intent);
        // Finish splash so user can't navigate back to it
        finish();
    }
}
