package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * ForgotPasswordActivity - Handles password recovery initiation.
 *
 * Features:
 *  - Accepts an email address input
 *  - Validates the email format
 *  - Simulates sending a password reset link (no actual email sent)
 *  - If email matches a known account, passes it forward to ResetPasswordActivity
 *  - Uses explicit Intent for navigation
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    // UI references
    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private Button btnSendReset, btnBackToLogin;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Bind views
        tilEmail       = findViewById(R.id.tilEmail);
        etEmail        = findViewById(R.id.etEmail);
        btnSendReset   = findViewById(R.id.btnSendReset);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        btnBack        = findViewById(R.id.btnBack);

        // Back arrow - return to previous screen
        btnBack.setOnClickListener(v -> finish());

        // Back to Login button - explicit Intent
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Send reset link
        btnSendReset.setOnClickListener(v -> initiatePasswordReset());
    }

    /**
     * Validates the entered email and simulates sending a password reset.
     * If email is recognized, navigates to ResetPasswordActivity.
     */
    private void initiatePasswordReset() {
        tilEmail.setError(null);

        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";

        // Validate email not empty
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError(getString(R.string.error_field_required));
            return;
        }

        // Validate email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            return;
        }

        // Check if email is registered (demo account or SharedPreferences registered account)
        if (!isEmailRegistered(email)) {
            tilEmail.setError(getString(R.string.error_email_not_found));
            return;
        }

        // Simulate sending reset email
        Toast.makeText(this, getString(R.string.success_reset_sent), Toast.LENGTH_LONG).show();

        // Explicit Intent: navigate to ResetPasswordActivity, passing the email
        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        intent.putExtra(AppConstants.EXTRA_EMAIL, email);
        startActivity(intent);
    }

    /**
     * Checks if the given email is registered in the system.
     *
     * @param email Email to verify
     * @return true if email is registered (demo or SharedPreferences)
     */
    private boolean isEmailRegistered(String email) {
        // Check demo account
        if (email.equalsIgnoreCase(AppConstants.DEMO_EMAIL)) {
            return true;
        }

        // Check SharedPreferences registered account
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        String regEmail = prefs.getString(AppConstants.KEY_REG_EMAIL, "");
        return email.equalsIgnoreCase(regEmail);
    }
}
