package com.example.datasafe.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.UserDbHelper;
import com.example.datasafe.models.User;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button loginBtn, createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.editText_username);
        passwordEditText = findViewById(R.id.editText_password);
        loginBtn = findViewById(R.id.btn_login);
        createAccountBtn = findViewById(R.id.btn_create_account);

        loginBtn.setOnClickListener(v -> {
            if (!validateEntries()) return;
            // hide keyboard if open
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            User user = new User(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString());
            UserDbHelper userDbHelper = new UserDbHelper(this);
            user = userDbHelper.verifyUser(user);
            if (user != null) {
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.putExtra("USER", user);
                startActivity(mainIntent);
                finish();
            } else {
                Snackbar.make(this.loginBtn, R.string.incorrect_username_or_password, Snackbar.LENGTH_LONG).setBackgroundTint(Color.rgb(255, 0, 0)).show();
            }
        });

        createAccountBtn.setOnClickListener(v -> {
            Intent signupIntent = new Intent(this, SignupActivity.class);
            startActivity(signupIntent);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("USERNAME", usernameEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("USERNAME"))
            usernameEditText.setText(savedInstanceState.getString("USERNAME"));
    }

    boolean validateEntries() {
        String username = usernameEditText.getText().toString().trim().toLowerCase();
        String password = passwordEditText.getText().toString();
        boolean isOk = true;
        if (username.length() < 3) {
            usernameEditText.setError(getString(R.string.min_length_3));
            isOk = false;
        }
        if (username.length() > 0 && (username.charAt(0) < 'a' || username.charAt(0) > 'z')) {
            usernameEditText.setError(getString(R.string.first_char_must_be_alpha));
            isOk = false;
        }
        if (username.contains(" ")) {
            usernameEditText.setError(getString(R.string.no_space_allowed));
            isOk = false;
        }
        if (password.length() < 4) {
            passwordEditText.setError(getString(R.string.min_length_4));
            isOk = false;
        }
        return isOk;
    }
}