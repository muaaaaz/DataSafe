package com.example.datasafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.dbhelper.DbHelper;
import com.example.datasafe.models.User;

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
            User user = new User(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString());
            DbHelper dbHelper = new DbHelper(this);
            if (dbHelper.verifyUser(user)) {
                Toast.makeText(this, "Welcome " + user.getUsername() + "!", Toast.LENGTH_SHORT).show();
            }
            finish();
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