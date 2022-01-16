package com.example.datasafe.activities.secretData;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.models.SecretData;

public class ViewSecretDataActivity extends AppCompatActivity {
    TextView titleTextView, dataTextView;
    LinearLayout overlay;
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
        overlay = findViewById(R.id.layout_view_data);

        titleTextView.setText(secretData.getTitle());
        setDataTextView();

        dataTextView.setOnClickListener(v ->
        {
            isTextViewActive = !isTextViewActive;
            setDataTextView();
        });

        overlay.setOnClickListener(v ->
        {
            isTextViewActive = !isTextViewActive;
            setDataTextView();
        });
    }

    private void setDataTextView() {
        if (!isTextViewActive) {
            dataTextView.setVisibility(View.INVISIBLE);
            overlay.setVisibility(View.VISIBLE);
        } else {
            overlay.setVisibility(View.GONE);
            dataTextView.setVisibility(View.VISIBLE);
            dataTextView.setText(secretData.getData());
        }
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
