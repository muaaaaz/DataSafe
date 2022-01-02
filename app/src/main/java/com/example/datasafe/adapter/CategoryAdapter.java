package com.example.datasafe.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datasafe.R;
import com.example.datasafe.activities.EditCategoryActivity;
import com.example.datasafe.dbhelper.CategoryDbHelper;
import com.example.datasafe.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> categories;
    CategoryDbHelper categoryDbHelper;

    public CategoryAdapter(ArrayList<Category> categories, CategoryDbHelper categoryDbHelper) {
        this.categories = categories;
        this.categoryDbHelper = categoryDbHelper;
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
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item_category, null, false);
        TextView name = convertView.findViewById(R.id.text_name_list_item_category);
        name.setText(categories.get(position).getName());

        // TODO: 01/01/2022 Set on click to launch category details
        View finalConvertView = convertView;
        convertView.setOnClickListener(v -> {
            Context parentContext = parent.getContext();
            PopupMenu popupMenu = new PopupMenu(parentContext, finalConvertView, Gravity.END);
            popupMenu.inflate(R.menu.menu_category_item);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menuItem_edit_category:
                            Intent editIntent = new Intent(parentContext, EditCategoryActivity.class);
                            editIntent.putExtra("CATEGORY", categories.get(position));
                            parentContext.startActivity(editIntent);
                            break;
                        case R.id.menuItem_delete_category:
                            new AlertDialog.Builder(parent.getContext())
                                    .setTitle(parentContext.getString(R.string.delete) + " \"" + categories.get(position).getName() + "\"")
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            categoryDbHelper.deleteCategory(categories.get(position));
                                            Toast.makeText(parentContext, parentContext.getString(R.string.category_deleted) + " (" + categories.get(position).getName() + ")", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).show();
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        });

        return convertView;
    }
}
