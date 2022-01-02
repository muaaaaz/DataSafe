package com.example.datasafe.activities.secretData;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.models.SecretData;

public class ViewSecretDataActivity extends AppCompatActivity {
    TextView titleTextView, dataTextView;
    Button cancelBtn, viewHideBtn;
    SecretData secretData;
    boolean isTextViewActive = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_secret_data);

        if (getIntent().hasExtra("SECRET_DATA")) {
            secretData = (SecretData) getIntent().getSerializableExtra("SECRET_DATA");
        } else finish();

        titleTextView = findViewById(R.id.text_title_view_data);
        dataTextView = findViewById(R.id.text_data_view_data);
        cancelBtn = findViewById(R.id.btn_cancel_view_data);
        viewHideBtn = findViewById(R.id.btn_view_hide_view_data);

        titleTextView.setText(secretData.getTitle());
        setDataTextView();

        cancelBtn.setOnClickListener(v -> finish());
        viewHideBtn.setOnClickListener(v -> setDataTextView());
    }

    private void setDataTextView() {
        if (!isTextViewActive) {
            dataTextView.setText(getString(R.string.click_button_to_view));
            dataTextView.setGravity(Gravity.CENTER);
            viewHideBtn.setText(R.string.view);
        } else {
            dataTextView.setText(secretData.getData());
            dataTextView.setGravity(Gravity.NO_GRAVITY);
            viewHideBtn.setText(R.string.hide);
        }
        isTextViewActive = !isTextViewActive;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isTextViewActive", isTextViewActive);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isTextViewActive = savedInstanceState.getBoolean("isTextViewActive");
        setDataTextView();
    }
}
