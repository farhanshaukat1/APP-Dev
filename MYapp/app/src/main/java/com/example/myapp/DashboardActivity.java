package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * DashboardActivity - Main hub of the application after login.
 *
 * Features:
 *  - Displays a personalized greeting using the logged-in user's name
 *  - Shows 8 module cards in a GridLayout (2 columns)
 *  - Each card has a distinct color, icon, and label
 *  - Profile icon button navigates to ProfileActivity via explicit Intent
 *  - Module cards show a "Coming Soon" Toast (except Profile which goes to ProfileActivity)
 */
public class DashboardActivity extends AppCompatActivity {

    private TextView tvGreeting;
    private ImageButton btnProfile;

    // Module cards
    private CardView cardProfile, cardSettings, cardNotifications, cardMessages;
    private CardView cardAnalytics, cardCalendar, cardFiles, cardHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Bind views
        tvGreeting        = findViewById(R.id.tvGreeting);
        btnProfile        = findViewById(R.id.btnProfile);
        cardProfile       = findViewById(R.id.cardProfile);
        cardSettings      = findViewById(R.id.cardSettings);
        cardNotifications = findViewById(R.id.cardNotifications);
        cardMessages      = findViewById(R.id.cardMessages);
        cardAnalytics     = findViewById(R.id.cardAnalytics);
        cardCalendar      = findViewById(R.id.cardCalendar);
        cardFiles         = findViewById(R.id.cardFiles);
        cardHelp          = findViewById(R.id.cardHelp);

        // Load user data from SharedPreferences and display greeting
        loadUserGreeting();

        // Set click listeners
        setupClickListeners();
    }

    /**
     * Loads user name from SharedPreferences and sets the greeting text.
     */
    private void loadUserGreeting() {
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        String userName = prefs.getString(AppConstants.KEY_USER_NAME, "User");

        // Use formatted string resource: "Hello, {name}!"
        String greeting = getString(R.string.dashboard_greeting, userName);
        tvGreeting.setText(greeting);
    }

    /**
     * Sets up all click listeners for profile button and module cards.
     */
    private void setupClickListeners() {
        // Profile icon in header - explicit Intent to ProfileActivity
        btnProfile.setOnClickListener(v -> navigateToProfile());

        // Profile card - also navigates to ProfileActivity
        cardProfile.setOnClickListener(v -> navigateToProfile());

        // Other module cards - show "Coming Soon" for now
        cardSettings.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());

        cardNotifications.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());

        cardMessages.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());

        cardAnalytics.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());

        cardCalendar.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());

        cardFiles.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());

        cardHelp.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());
    }

    /**
     * Navigates to ProfileActivity using an explicit Intent.
     */
    private void navigateToProfile() {
        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
