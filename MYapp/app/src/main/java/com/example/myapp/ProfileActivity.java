package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ProfileActivity - Displays the logged-in user's profile information.
 *
 * Features:
 *  - Shows user's name, email, and role loaded from SharedPreferences
 *  - Avatar initial (first letter of name) displayed in a circle
 *  - Edit Profile button (simulated with Toast)
 *  - Logout button with confirmation dialog
 *  - On logout, clears login state and navigates to LoginActivity via explicit Intent
 */
public class ProfileActivity extends AppCompatActivity {

    // UI references
    private TextView tvAvatar, tvName, tvEmail;
    private Button btnLogout, btnEditProfile;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Bind views
        tvAvatar       = findViewById(R.id.tvAvatar);
        tvName         = findViewById(R.id.tvName);
        tvEmail        = findViewById(R.id.tvEmail);
        btnLogout      = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnBack        = findViewById(R.id.btnBack);

        // Load and display user info from SharedPreferences
        loadUserInfo();

        // Back button - return to DashboardActivity
        btnBack.setOnClickListener(v -> finish());

        // Edit Profile button - simulated
        btnEditProfile.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.info_coming_soon), Toast.LENGTH_SHORT).show());

        // Logout button - show confirmation dialog
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());
    }

    /**
     * Loads user information from SharedPreferences and populates the UI.
     */
    private void loadUserInfo() {
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        String name  = prefs.getString(AppConstants.KEY_USER_NAME, "User");
        String email = prefs.getString(AppConstants.KEY_USER_EMAIL, "");

        tvName.setText(name);
        tvEmail.setText(email);

        // Set avatar initial (first character of name, uppercase)
        if (!name.isEmpty()) {
            tvAvatar.setText(String.valueOf(name.charAt(0)).toUpperCase());
        } else {
            tvAvatar.setText("U");
        }
    }

    /**
     * Shows an AlertDialog to confirm the logout action.
     */
    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout_confirm_title))
                .setMessage(getString(R.string.logout_confirm_message))
                .setPositiveButton(getString(R.string.btn_yes), (dialog, which) -> performLogout())
                .setNegativeButton(getString(R.string.btn_cancel), null)
                .show();
    }

    /**
     * Clears the user session from SharedPreferences and navigates to LoginActivity.
     */
    private void performLogout() {
        // Clear login state
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        prefs.edit()
                .putBoolean(AppConstants.KEY_IS_LOGGED_IN, false)
                .remove(AppConstants.KEY_USER_NAME)
                .remove(AppConstants.KEY_USER_EMAIL)
                .apply();

        // Explicit Intent: navigate to LoginActivity, clear back stack
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
