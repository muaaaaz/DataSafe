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
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasafe.R;
import com.example.datasafe.activities.EditCategoryActivity;
import com.example.datasafe.activities.secretData.MainSecretDataActivity;
import com.example.datasafe.dbhelper.CategoryDbHelper;
import com.example.datasafe.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    final CategoryDbHelper categoryDbHelper;
    final int userId;
    ArrayList<Category> categories;

    public CategoryAdapter(int userId, CategoryDbHelper categoryDbHelper) {
        this.categoryDbHelper = categoryDbHelper;
        this.userId = userId;
        this.categories = this.categoryDbHelper.getAllCategories(userId);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());
        Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(v -> {
            Intent mi = new Intent(context, MainSecretDataActivity.class);
            mi.putExtra("CATEGORY", category);
            context.startActivity(mi);
        });
        holder.editBtn.setOnClickListener(v -> {
            editCategoryClicked(context, category);
        });
        holder.deleteBtn.setOnClickListener(v -> {
            deleteCategoryClicked(context, category);
        });
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v, Gravity.END);
            popupMenu.inflate(R.menu.menu_category_item);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menuItem_edit_category) {
                        editCategoryClicked(context, category);
                    } else if (item.getItemId() == R.id.menuItem_delete_category) {
                        deleteCategoryClicked(context, category);
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
    }

    private void editCategoryClicked(Context context, Category category) {
        Intent editIntent = new Intent(context, EditCategoryActivity.class);
        editIntent.putExtra("CATEGORY", category);
        context.startActivity(editIntent);
    }

    private void deleteCategoryClicked(Context context, Category category) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.delete) + " \"" + category.getName() + "\"?")
                .setMessage("All related data will be lost.")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (categoryDbHelper.deleteCategory(category)) {
                            update();
                            Toast.makeText(context, context.getString(R.string.category_deleted) + " (" + category.getName() + ")", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, context.getString(R.string.operation_not_performed), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void update() {
        this.categories = this.categoryDbHelper.getAllCategories(userId);
        notifyDataSetChanged();
    }

    public void updateCategories(String text) {
        if (text.trim().isEmpty()) {
            this.update();
            return;
        }
        this.categories = this.categoryDbHelper.getCategoriesWithTextMatch(userId, text);
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final ImageButton deleteBtn, editBtn;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name_list_item_category);
            editBtn = itemView.findViewById(R.id.btn_edit_item_category);
            deleteBtn = itemView.findViewById(R.id.btn_delete_item_category);
        }
    }
}