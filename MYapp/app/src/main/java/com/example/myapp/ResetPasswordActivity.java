package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * ResetPasswordActivity - Allows the user to set a new password.
 *
 * Features:
 *  - Receives the email via Intent extra from ForgotPasswordActivity
 *  - Accepts new password and confirmation with validation
 *  - Simulates password reset by updating SharedPreferences
 *  - On success, navigates back to LoginActivity via explicit Intent
 */
public class ResetPasswordActivity extends AppCompatActivity {

    // UI references
    private TextInputLayout tilNewPassword, tilConfirmPassword;
    private TextInputEditText etNewPassword, etConfirmPassword;
    private Button btnResetPassword;
    private ImageButton btnBack;

    // The email for which password is being reset
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Retrieve email passed from ForgotPasswordActivity
        userEmail = getIntent().getStringExtra(AppConstants.EXTRA_EMAIL);
        if (userEmail == null) userEmail = "";

        // Bind views
        tilNewPassword     = findViewById(R.id.tilNewPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etNewPassword      = findViewById(R.id.etNewPassword);
        etConfirmPassword  = findViewById(R.id.etConfirmPassword);
        btnResetPassword   = findViewById(R.id.btnResetPassword);
        btnBack            = findViewById(R.id.btnBack);

        // Back button - return to previous screen
        btnBack.setOnClickListener(v -> finish());

        // Reset password button
        btnResetPassword.setOnClickListener(v -> attemptResetPassword());
    }

    /**
     * Validates the new password and confirm password fields, then performs the reset.
     */
    private void attemptResetPassword() {
        // Clear previous errors
        tilNewPassword.setError(null);
        tilConfirmPassword.setError(null);

        String newPassword     = etNewPassword.getText() != null ? etNewPassword.getText().toString() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString() : "";

        boolean isValid = true;

        // Validate new password (required, min 6 chars)
        if (TextUtils.isEmpty(newPassword)) {
            tilNewPassword.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (newPassword.length() < 6) {
            tilNewPassword.setError(getString(R.string.error_password_short));
            isValid = false;
        }

        // Validate confirm password (must match new password)
        if (TextUtils.isEmpty(confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!newPassword.equals(confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.error_passwords_no_match));
            isValid = false;
        }

        if (!isValid) return;

        // Simulate password reset:
        // Update the stored password in SharedPreferences if it's a registered account
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        String regEmail = prefs.getString(AppConstants.KEY_REG_EMAIL, "");

        if (userEmail.equalsIgnoreCase(regEmail)) {
            // Update the registered account's password
            prefs.edit()
                    .putString(AppConstants.KEY_REG_PASSWORD, newPassword)
                    .apply();
        }
        // Note: For demo account, the password "reset" is simulated without actually changing it.
        // A real app would call an API endpoint.

        Toast.makeText(this, getString(R.string.success_password_reset), Toast.LENGTH_LONG).show();

        // Explicit Intent: navigate back to LoginActivity, clearing the back stack
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
