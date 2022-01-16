package com.example.datasafe.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.CategoryDbHelper;
import com.example.datasafe.models.Category;

public class EditCategoryActivity extends AppCompatActivity {
    Category category;
    EditText name;
    Button cancelBtn, editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        if (getIntent() != null && getIntent().hasExtra("CATEGORY")) {
            category = (Category) getIntent().getSerializableExtra("CATEGORY");
        } else {
            finish();
        }

        name = findViewById(R.id.text_name_edit_category);
        cancelBtn = findViewById(R.id.btn_cancel_edit_category);
        editBtn = findViewById(R.id.btn_update_edit_category);

        name.setText(category.getName());

        cancelBtn.setOnClickListener(v -> finish());
        editBtn.setOnClickListener(v -> {
            CategoryDbHelper categoryDbHelper = new CategoryDbHelper(this);
            category.setName(name.getText().toString().trim());
            if (categoryDbHelper.updateCategory(category)) {
                Toast.makeText(this, getString(R.string.category_updated) + " (" + category.getName() + ")", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}