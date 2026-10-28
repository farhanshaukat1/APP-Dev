package com.example.myapp;

/**
 * AppConstants - Central class for all application-wide constants.
 * Includes SharedPreferences keys, hardcoded credentials for simulation,
 * and Intent extra keys used for passing data between activities.
 */
public final class AppConstants {

    // SharedPreferences file name
    public static final String PREFS_NAME = "MyAppPrefs";

    // SharedPreferences keys
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";
    public static final String KEY_USER_NAME    = "user_name";
    public static final String KEY_USER_EMAIL   = "user_email";

    // Intent extra keys
    public static final String EXTRA_EMAIL = "extra_email";

    // Simulated registered accounts (hardcoded for demo - no backend required)
    // In a real app these would come from a database / server.
    public static final String DEMO_EMAIL    = "user@example.com";
    public static final String DEMO_PASSWORD = "password123";
    public static final String DEMO_NAME     = "John Doe";

    // Registration uses SharedPreferences to persist new accounts during session.
    // Keys for the "registered account" stored at registration time:
    public static final String KEY_REG_EMAIL    = "reg_email";
    public static final String KEY_REG_PASSWORD = "reg_password";
    public static final String KEY_REG_NAME     = "reg_name";

    // Private constructor - utility class, not meant to be instantiated
    private AppConstants() {}
}
