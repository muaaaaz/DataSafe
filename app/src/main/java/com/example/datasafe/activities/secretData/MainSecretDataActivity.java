package com.example.datasafe.activities.secretData;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasafe.R;
import com.example.datasafe.adapter.SecretDataAdapter;
import com.example.datasafe.dbhelper.SecretDataDbHelper;
import com.example.datasafe.models.Category;

public class MainSecretDataActivity extends AppCompatActivity {
    Category category;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        secretDataAdapter.update();
    }
}