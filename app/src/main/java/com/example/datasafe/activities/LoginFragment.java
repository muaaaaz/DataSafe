package com.example.datasafe.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.UserDbHelper;
import com.example.datasafe.models.User;
import com.example.datasafe.utilities.Utilities;

public class LoginFragment extends Fragment {
    EditText usernameEditText, passwordEditText;
    ImageButton loginBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameEditText = view.findViewById(R.id.editText_username);
        passwordEditText = view.findViewById(R.id.editText_password);
        loginBtn = view.findViewById(R.id.btn_login);

        loginBtn.setOnClickListener(v -> {
            if (!validateEntries()) return;
            // hide keyboard if open
            Utilities.hideVirtualKeyBoard(this.requireActivity(), this.requireActivity().getCurrentFocus());

            User user = new User(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString());
            UserDbHelper userDbHelper = new UserDbHelper(this.requireActivity().getApplicationContext());
            user = userDbHelper.verifyUser(user);
            if (user != null) {
                Intent mainIntent = new Intent(this.requireContext(), MainActivity.class);
                mainIntent.putExtra("USER", user);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
            } else {
                Utilities.showCustomToast(this.requireContext().getApplicationContext(), R.drawable.ic_warning_24, R.string.incorrect_username_or_password);
            }
        });
    }

    boolean validateEntries() {
        String username = usernameEditText.getText().toString().trim().toLowerCase();
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
        return isOk;
    }
}
