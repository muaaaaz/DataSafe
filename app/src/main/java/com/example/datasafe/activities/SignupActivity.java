package com.example.datasafe.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.UserDbHelper;
import com.example.datasafe.models.User;
import com.example.datasafe.utilities.Utilities;

public class SignupActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    CheckBox tosCheckBox;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.editText_username);
        passwordEditText = findViewById(R.id.editText_password);
        tosCheckBox = findViewById(R.id.checkbox_tos_agree);
        signupBtn = findViewById(R.id.btn_signup);

        signupBtn.setOnClickListener(v -> {
            if (!validateEntries()) return;
            Utilities.hideVirtualKeyBoard(this, this.getCurrentFocus());
            if (!tosCheckBox.isChecked()) {
                Utilities.showSnackBar(this.signupBtn, R.string.must_agree, Color.RED);
                return;
            }
            User user = new User(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString());
            UserDbHelper userDbHelper = new UserDbHelper(this);
            if (userDbHelper.addUser(user)) {
                Toast.makeText(this, R.string.account_created, Toast.LENGTH_SHORT).show();
                finish();
            }
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
        if (isOk) {
            UserDbHelper userDbHelper = new UserDbHelper(this);
            if (userDbHelper.isUsernameTaken(username)) {
                usernameEditText.setError(getString(R.string.username_already_taken));
                isOk = false;
            }
        }
        if (password.length() < 4) {
            passwordEditText.setError(getString(R.string.min_length_4));
            isOk = false;
        }
        return isOk;
    }
}