# MyApp – Android Application Assignment 2

A complete Android application developed in **Java + XML** demonstrating common UI screens with Material Design, multiple layout types, input validation, and simulated authentication.

---

## 📱 Features & Screens

### 1. Splash Screen (`SplashActivity`)
- Displays the **app logo** and **app name** on a blue gradient background
- Automatically navigates to **Login** after 2 seconds
- If user is already logged in (via SharedPreferences), navigates directly to **Dashboard**
- Layout: **ConstraintLayout**

### 2. Login Screen (`LoginActivity`)
- Input fields: **Email** (with email keyboard) and **Password** (with toggle visibility)
- Buttons: **Login**, **Create Account** → Register, **Forgot Password?**
- Full input validation with inline error messages:
  - Empty field check
  - Email format validation (`Patterns.EMAIL_ADDRESS`)
  - Minimum password length (6 chars)
- Authenticates against a **hardcoded demo account** and any **registered account** stored locally
- Layout: **LinearLayout** + **ScrollView** + Card

### 3. Register Screen (`RegisterActivity`)
- Input fields: **Full Name**, **Email**, **Password**, **Confirm Password**
- Full validation:
  - All fields required
  - Name ≥ 2 characters
  - Valid email format
  - Password ≥ 6 characters
  - Passwords must match
- Simulates registration by saving to **SharedPreferences**
- Layout: **LinearLayout**

### 4. Forgot Password Screen (`ForgotPasswordActivity`)
- Input field: **Email Address**
- Validates email format and checks if email is registered
- Simulates sending a recovery email (Toast notification)
- Navigates to Reset Password screen passing the email via Intent extra
- Layout: **ConstraintLayout**

### 5. Reset Password Screen (`ResetPasswordActivity`)
- Input fields: **New Password** and **Confirm New Password**
- Validation: both fields required, min 6 chars, passwords must match
- Simulates password reset (updates SharedPreferences for registered accounts)
- Layout: **ConstraintLayout**

### 6. Dashboard Screen (`DashboardActivity`)
- Displays a personalized greeting: `"Hello, {UserName}!"`
- **8 module cards** in a **2-column GridLayout**, each with:
  - Distinct background color
  - Vector icon
  - Label text
- Modules: Profile, Settings, Notifications, Messages, Analytics, Calendar, Files, Help
- Profile card/button navigates to Profile screen
- Layout: **LinearLayout** (root) + **GridLayout** (modules)

### 7. Profile Screen (`ProfileActivity`)
- Displays user's **Name**, **Email**, and **Role** from SharedPreferences
- Avatar circle showing the first initial of the user's name
- **Edit Profile** button (simulated)
- **Logout** button with **confirmation dialog** (AlertDialog)
- Logout clears session and navigates back to Login, clearing the back stack
- Layout: **ConstraintLayout** (header) + **LinearLayout** (cards)

---

## 🔐 Authentication (Simulated – No Backend)

| Credential  | Value                |
|-------------|----------------------|
| Email       | `user@example.com`   |
| Password    | `password123`        |

You can also **register a new account** via the Register screen and log in with those credentials.

---

## 🗺️ Navigation Flow

```
SplashActivity
    ├──► LoginActivity
    │       ├──► RegisterActivity ──► LoginActivity
    │       ├──► ForgotPasswordActivity ──► ResetPasswordActivity ──► LoginActivity
    │       └──► DashboardActivity (on successful login)
    │               └──► ProfileActivity
    │                       └──► LoginActivity (on logout)
    └──► DashboardActivity (if already logged in)
```

All navigation uses **Explicit Intents**.

---

## 🛠️ Technical Requirements Coverage

| Requirement                              | Implementation                                                  |
|------------------------------------------|-----------------------------------------------------------------|
| String resources (`strings.xml`)         | ✅ All text externalized to `res/values/strings.xml`           |
| Night mode colors                        | ✅ `res/values-night/colors.xml` with dark theme overrides     |
| `LinearLayout`                           | ✅ Used in Login, Register, Dashboard root                     |
| `GridLayout`                             | ✅ Used in Dashboard for 8 module cards (2 columns)            |
| `ConstraintLayout`                       | ✅ Used in Splash, ForgotPassword, ResetPassword, Profile header|
| Text, email & password input types       | ✅ `android:inputType="textEmailAddress"`, `textPassword`      |
| Button clicks & input validation         | ✅ Inline errors via `TextInputLayout.setError()`, Patterns    |
| Explicit Intents                         | ✅ All activity navigation uses `new Intent(This, Target.class)`|
| Consistent UI                            | ✅ Material Design theme, shared styles and colors             |
| Simulated authentication (no backend)    | ✅ Hardcoded + SharedPreferences-based                         |

---

## 📂 Project Structure

```
app/src/main/
├── java/com/example/myapp/
│   ├── AppConstants.java         # App-wide constants & keys
│   ├── SplashActivity.java       # Entry point
│   ├── LoginActivity.java        # Authentication
│   ├── RegisterActivity.java     # New user registration
│   ├── ForgotPasswordActivity.java  # Password recovery initiation
│   ├── ResetPasswordActivity.java   # New password setting
│   ├── DashboardActivity.java    # Main module grid
│   └── ProfileActivity.java      # User info & logout
├── res/
│   ├── layout/
│   │   ├── activity_splash.xml
│   │   ├── activity_login.xml
│   │   ├── activity_register.xml
│   │   ├── activity_forgot_password.xml
│   │   ├── activity_reset_password.xml
│   │   ├── activity_dashboard.xml
│   │   └── activity_profile.xml
│   ├── drawable/           # Vector icons and backgrounds
│   ├── values/
│   │   ├── strings.xml     # All UI text
│   │   ├── colors.xml      # Light mode colors
│   │   └── themes.xml      # Styles and themes
│   └── values-night/
│       └── colors.xml      # Dark mode color overrides
└── AndroidManifest.xml
```

---

## ▶️ How to Run

1. Open the project in **Android Studio** (Electric Eel or newer recommended)
2. Wait for Gradle sync to complete
3. Select an emulator or connected device (API 24+)
4. Click **Run ▶**

**Demo Login:**  
Email: `user@example.com`  
Password: `password123`
