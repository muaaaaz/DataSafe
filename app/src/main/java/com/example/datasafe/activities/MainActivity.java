package com.example.datasafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasafe.R;
import com.example.datasafe.adapter.CategoryAdapter;
import com.example.datasafe.dbhelper.CategoryDbHelper;
import com.example.datasafe.models.User;

public class MainActivity extends AppCompatActivity {
    User user;
    SearchView searchView;
    RecyclerView recyclerView;
    ImageButton addBtn;
    CategoryDbHelper categoryDbHelper;
    CategoryAdapter categoryAdapter;

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
        categoryAdapter = new CategoryAdapter(user.getId(), categoryDbHelper);

        // display categories
        recyclerView = findViewById(R.id.recyclerView_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);

        addBtn = findViewById(R.id.btn_add_main);
        addBtn.setOnClickListener(v -> {
            Intent addCategoryIntent = new Intent(this, AddCategoryActivity.class);
            addCategoryIntent.putExtra("USER", user);
            startActivity(addCategoryIntent);
        });

        searchView = findViewById(R.id.searchView_main);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                categoryAdapter.updateCategories(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoryAdapter.updateCategories(newText);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoryAdapter.update();
    }
}