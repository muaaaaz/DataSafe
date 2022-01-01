package com.example.datasafe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datasafe.R;
import com.example.datasafe.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> categories;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_category, null, false);
        TextView name = convertView.findViewById(R.id.text_name_list_item_category);
        name.setText(categories.get(position).getName());

        // TODO: 01/01/2022 Set on click to launch category details
        convertView.setOnClickListener(v -> {
            Toast.makeText(parent.getContext(), categories.get(position).getName(), Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
