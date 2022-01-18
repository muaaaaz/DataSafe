package com.example.datasafe.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.UserDbHelper;
import com.example.datasafe.models.User;
import com.example.datasafe.utilities.Utilities;

public class SignupFragment extends Fragment {
    EditText usernameEditText, passwordEditText;
    CheckBox tosCheckBox;
    ImageButton signupBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameEditText = view.findViewById(R.id.editText_username);
        passwordEditText = view.findViewById(R.id.editText_password);
        tosCheckBox = view.findViewById(R.id.checkbox_tos_agree);
        signupBtn = view.findViewById(R.id.btn_signup);

        signupBtn.setOnClickListener(v -> {
            if (!validateEntries()) return;
            Utilities.hideVirtualKeyBoard(this.requireActivity(), this.requireActivity().getCurrentFocus());
            if (!tosCheckBox.isChecked()) {
                Utilities.showCustomToast(this.requireContext().getApplicationContext(), R.drawable.ic_warning_24, R.string.must_agree);
                return;
            }
            User user = new User(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString());
            UserDbHelper userDbHelper = new UserDbHelper(this.requireActivity().getApplicationContext());
            if (userDbHelper.addUser(user)) {
                Utilities.showCustomToast(this.requireContext().getApplicationContext(), R.drawable.ic_info_24, R.string.account_created);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_view_login_signup, new LoginFragment())
                        .commitNow();
            }
        });
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
            UserDbHelper userDbHelper = new UserDbHelper(this.requireActivity().getApplicationContext());
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
