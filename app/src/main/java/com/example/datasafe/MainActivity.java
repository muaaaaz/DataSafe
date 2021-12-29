package com.example.datasafe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.models.User;

public class MainActivity extends AppCompatActivity {
    TextView welcomeUserText;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeUserText = findViewById(R.id.text_welcome_user);

        if (getIntent() != null && getIntent().hasExtra("USER")) {
            user = (User) getIntent().getSerializableExtra("USER");
        }

        welcomeUserText.setText(String.format("%s %s!", getString(R.string.welcome), user.getUsername()));
    }
}