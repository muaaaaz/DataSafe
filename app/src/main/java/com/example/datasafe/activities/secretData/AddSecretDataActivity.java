package com.example.datasafe.activities.secretData;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.SecretDataDbHelper;
import com.example.datasafe.models.Category;
import com.example.datasafe.models.SecretData;
import com.example.datasafe.utilities.Utilities;

public class AddSecretDataActivity extends AppCompatActivity {
    EditText titleText, dataText;
    Button cancelBtn, addBtn;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_secret_data);

        if (getIntent().hasExtra("CATEGORY")) {
            category = (Category) getIntent().getSerializableExtra("CATEGORY");
        } else finish();

        titleText = findViewById(R.id.text_title_add_data);
        dataText = findViewById(R.id.text_data_add_data);
        cancelBtn = findViewById(R.id.btn_cancel_add_data);
        addBtn = findViewById(R.id.btn_add_add_data);

        cancelBtn.setOnClickListener(v -> finish());
        addBtn.setOnClickListener(v -> {
            if (Utilities.isTextViewEmpty(titleText)) {
                titleText.setError("Cannot be empty or null.");
                return;
            }
            if (Utilities.isTextViewEmpty(dataText)) {
                dataText.setError("Cannot be empty or null.");
                return;
            }
            String title = titleText.getText().toString().trim();
            String data = dataText.getText().toString().trim();
            SecretData dataToAdd = new SecretData(category.getUid(), category.getId(), title, data);
            SecretDataDbHelper secretDataDbHelper = new SecretDataDbHelper(this);
            if (secretDataDbHelper.addData(dataToAdd)) {
                Utilities.showCustomToast(this.getApplicationContext(), R.drawable.ic_info_24, getString(R.string.secret_added) + " (" + dataToAdd.getTitle() + ")");
            }
            finish();
        });
    }
}