package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * LoginActivity - Handles user authentication.
 *
 * Features:
 *  - Email and password input with validation
 *  - Authenticates against demo credentials or a registered account (stored in SharedPreferences)
 *  - Navigate to RegisterActivity via explicit Intent
 *  - Navigate to ForgotPasswordActivity via explicit Intent
 *  - On successful login, navigate to DashboardActivity via explicit Intent
 */
public class LoginActivity extends AppCompatActivity {

    // UI references
    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin, btnRegister, btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind views
        tilEmail          = findViewById(R.id.tilEmail);
        tilPassword       = findViewById(R.id.tilPassword);
        etEmail           = findViewById(R.id.etEmail);
        etPassword        = findViewById(R.id.etPassword);
        btnLogin          = findViewById(R.id.btnLogin);
        btnRegister       = findViewById(R.id.btnRegister);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);

        // Set click listeners
        btnLogin.setOnClickListener(v -> attemptLogin());

        btnRegister.setOnClickListener(v -> {
            // Explicit Intent: navigate to RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnForgotPassword.setOnClickListener(v -> {
            // Explicit Intent: navigate to ForgotPasswordActivity
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Validates input fields and attempts to authenticate the user.
     */
    private void attemptLogin() {
        // Clear previous errors
        tilEmail.setError(null);
        tilPassword.setError(null);

        String email    = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString() : "";

        boolean isValid = true;

        // Validate email
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (password.length() < 6) {
            tilPassword.setError(getString(R.string.error_password_short));
            isValid = false;
        }

        if (!isValid) return;

        // Check credentials against demo account or registered account
        if (authenticate(email, password)) {
            onLoginSuccess(email);
        } else {
            tilPassword.setError(getString(R.string.error_invalid_credentials));
            Toast.makeText(this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks credentials against the hardcoded demo account and any registered account
     * saved in SharedPreferences.
     *
     * @param email    User-entered email
     * @param password User-entered password
     * @return true if credentials match
     */
    private boolean authenticate(String email, String password) {
        // Check against hardcoded demo credentials
        if (email.equalsIgnoreCase(AppConstants.DEMO_EMAIL)
                && password.equals(AppConstants.DEMO_PASSWORD)) {
            return true;
        }

        // Check against registered account saved in SharedPreferences
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        String regEmail    = prefs.getString(AppConstants.KEY_REG_EMAIL, "");
        String regPassword = prefs.getString(AppConstants.KEY_REG_PASSWORD, "");

        return email.equalsIgnoreCase(regEmail) && password.equals(regPassword);
    }

    /**
     * Called when login is successful. Saves login state and navigates to Dashboard.
     *
     * @param email The authenticated user's email
     */
    private void onLoginSuccess(String email) {
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);

        // Determine display name
        String name;
        if (email.equalsIgnoreCase(AppConstants.DEMO_EMAIL)) {
            name = AppConstants.DEMO_NAME;
        } else {
            name = prefs.getString(AppConstants.KEY_REG_NAME, email);
        }

        // Persist login state
        prefs.edit()
                .putBoolean(AppConstants.KEY_IS_LOGGED_IN, true)
                .putString(AppConstants.KEY_USER_NAME, name)
                .putString(AppConstants.KEY_USER_EMAIL, email)
                .apply();

        // Explicit Intent: navigate to DashboardActivity
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        // Clear back stack so user can't navigate back to Login
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
