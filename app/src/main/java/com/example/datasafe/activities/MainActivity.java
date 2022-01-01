package com.example.datasafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;
import com.example.datasafe.adapter.CategoryAdapter;
import com.example.datasafe.dbhelper.CategoryDbHelper;
import com.example.datasafe.models.User;

public class MainActivity extends AppCompatActivity {
    User user;
    ListView listView;
    ImageButton addBtn;
    CategoryDbHelper categoryDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null && getIntent().hasExtra("USER")) {
            user = (User) getIntent().getSerializableExtra("USER");
        } else {
            finish();
        }

        categoryDbHelper = new CategoryDbHelper(this);

        // display categories
        listView = findViewById(R.id.listView_main);
        listView.setAdapter(new CategoryAdapter(categoryDbHelper.getAllCategories(user.getId())));

        addBtn = findViewById(R.id.btn_add_main);
        addBtn.setOnClickListener(v -> {
            Intent addCategoryIntent = new Intent(this, AddCategoryActivity.class);
            addCategoryIntent.putExtra("USER", user);
            startActivity(addCategoryIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(new CategoryAdapter(categoryDbHelper.getAllCategories(user.getId())));
    }
}