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
 * RegisterActivity - Handles new user registration.
 *
 * Features:
 *  - Collects: Full Name, Email, Password, Confirm Password
 *  - Validates all fields with appropriate error messages
 *  - Simulates registration by storing user data in SharedPreferences
 *  - Uses explicit Intent to navigate back to LoginActivity on success
 */
public class RegisterActivity extends AppCompatActivity {

    // UI references
    private TextInputLayout tilName, tilEmail, tilPassword, tilConfirmPassword;
    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister, btnSignIn;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind views
        tilName            = findViewById(R.id.tilName);
        tilEmail           = findViewById(R.id.tilEmail);
        tilPassword        = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etName             = findViewById(R.id.etName);
        etEmail            = findViewById(R.id.etEmail);
        etPassword         = findViewById(R.id.etPassword);
        etConfirmPassword  = findViewById(R.id.etConfirmPassword);
        btnRegister        = findViewById(R.id.btnRegister);
        btnSignIn          = findViewById(R.id.btnSignIn);
        btnBack            = findViewById(R.id.btnBack);

        // Back button - go back to previous screen (LoginActivity)
        btnBack.setOnClickListener(v -> finish());

        // Sign In button - navigate to LoginActivity via explicit Intent
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Register button - validate and register
        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    /**
     * Validates all input fields and performs simulated registration.
     */
    private void attemptRegister() {
        // Clear previous errors
        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);

        String name            = etName.getText() != null ? etName.getText().toString().trim() : "";
        String email           = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password        = etPassword.getText() != null ? etPassword.getText().toString() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString() : "";

        boolean isValid = true;

        // Validate Name (required, min 2 chars)
        if (TextUtils.isEmpty(name)) {
            tilName.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (name.length() < 2) {
            tilName.setError(getString(R.string.error_name_short));
            isValid = false;
        }

        // Validate Email
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        // Validate Password (required, min 6 chars)
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (password.length() < 6) {
            tilPassword.setError(getString(R.string.error_password_short));
            isValid = false;
        }

        // Validate Confirm Password
        if (TextUtils.isEmpty(confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.error_passwords_no_match));
            isValid = false;
        }

        if (!isValid) return;

        // Simulated registration: save to SharedPreferences
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);
        prefs.edit()
                .putString(AppConstants.KEY_REG_EMAIL, email)
                .putString(AppConstants.KEY_REG_PASSWORD, password)
                .putString(AppConstants.KEY_REG_NAME, name)
                .apply();

        Toast.makeText(this, getString(R.string.success_registered), Toast.LENGTH_LONG).show();

        // Navigate back to LoginActivity via explicit Intent
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
