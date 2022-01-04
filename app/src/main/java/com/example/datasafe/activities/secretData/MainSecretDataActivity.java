package com.example.datasafe.activities.secretData;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasafe.R;
import com.example.datasafe.adapter.SecretDataAdapter;
import com.example.datasafe.dbhelper.SecretDataDbHelper;
import com.example.datasafe.models.Category;

public class MainSecretDataActivity extends AppCompatActivity {
    Category category;
    SearchView searchView;
    RecyclerView recyclerView;
    ImageButton addBtn;
    SecretDataAdapter secretDataAdapter;
    SecretDataDbHelper secretDataDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_secret_data);

        if (getIntent().hasExtra("CATEGORY")) {
            category = (Category) getIntent().getSerializableExtra("CATEGORY");
        } else finish();

        secretDataDbHelper = new SecretDataDbHelper(this);
        secretDataAdapter = new SecretDataAdapter(category, secretDataDbHelper);

        recyclerView = findViewById(R.id.recyclerView_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(secretDataAdapter);

        addBtn = findViewById(R.id.btn_add_main_data);
        addBtn.setOnClickListener(v -> {
            Intent addDataIntent = new Intent(this, AddSecretDataActivity.class);
            addDataIntent.putExtra("CATEGORY", category);
            startActivity(addDataIntent);
        });

        searchView = findViewById(R.id.searchView_data);
        searchView.setQueryHint(getString(R.string.search_in) + " \"" + category.getName() + "\"");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                secretDataAdapter.updateSecretData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                secretDataAdapter.updateSecretData(newText);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        secretDataAdapter.update();
    }
}