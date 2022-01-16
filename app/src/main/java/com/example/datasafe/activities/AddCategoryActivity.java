package com.example.datasafe.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.dbhelper.CategoryDbHelper;
import com.example.datasafe.models.Category;
import com.example.datasafe.models.User;

public class AddCategoryActivity extends AppCompatActivity {
    User user;
    EditText name;
    Button cancelBtn, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        if (getIntent() != null && getIntent().hasExtra("USER")) {
            user = (User) getIntent().getSerializableExtra("USER");
        } else {
            finish();
        }

        name = findViewById(R.id.text_name_add_category);
        cancelBtn = findViewById(R.id.btn_cancel_add_category);
        addBtn = findViewById(R.id.btn_add_add_category);

        cancelBtn.setOnClickListener(v -> finish());
        addBtn.setOnClickListener(v -> {
            CategoryDbHelper categoryDbHelper = new CategoryDbHelper(this);
            Category category = new Category(user.getId(), name.getText().toString().trim());
            if (categoryDbHelper.addCategory(category)) {
                Toast.makeText(this, getString(R.string.category_added) + " (" + category.getName() + ")", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}