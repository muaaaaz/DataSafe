package com.example.datasafe.activities.secretData;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.SecretDataDbHelper;
import com.example.datasafe.models.SecretData;
import com.example.datasafe.utilities.Utilities;

public class EditSecretDataActivity extends AppCompatActivity {
    SecretData secretData;
    EditText titleText, dataText;
    Button cancelBtn, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_secret_data);

        if (getIntent().hasExtra("SECRET_DATA")) {
            secretData = (SecretData) getIntent().getSerializableExtra("SECRET_DATA");
        } else finish();

        titleText = findViewById(R.id.text_title_edit_data);
        dataText = findViewById(R.id.text_data_edit_data);
        cancelBtn = findViewById(R.id.btn_cancel_edit_data);
        addBtn = findViewById(R.id.btn_update_edit_data);

        titleText.setText(secretData.getTitle());
        dataText.setText(secretData.getData());

        cancelBtn.setOnClickListener(v -> finish());
        addBtn.setOnClickListener(v -> {
            String title = titleText.getText().toString().trim();
            String data = dataText.getText().toString().trim();
            secretData.setTitle(title);
            secretData.setData(data);
            SecretDataDbHelper secretDataDbHelper = new SecretDataDbHelper(this);
            if (secretDataDbHelper.updateData(secretData)) {
                Utilities.showCustomToast(this, R.drawable.ic_info_24, getString(R.string.secret_updated) + " (" + secretData.getTitle() + ")");
            }
            finish();
        });
    }
}