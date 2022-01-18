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
import com.example.datasafe.activities.secretData.EditSecretDataActivity;
import com.example.datasafe.activities.secretData.ViewSecretDataActivity;
import com.example.datasafe.dbhelper.SecretDataDbHelper;
import com.example.datasafe.models.Category;
import com.example.datasafe.models.SecretData;
import com.example.datasafe.utilities.Utilities;

import java.util.ArrayList;

public class SecretDataAdapter extends RecyclerView.Adapter<SecretDataAdapter.SecretDataViewHolder> {
    final SecretDataDbHelper secretDataDbHelper;
    final Category category;
    ArrayList<SecretData> secretData;

    public SecretDataAdapter(Category category, SecretDataDbHelper secretDataDbHelper) {
        this.secretDataDbHelper = secretDataDbHelper;
        this.category = category;
        this.secretData = this.secretDataDbHelper.getData(category.getUid(), category.getId());
    }

    @NonNull
    @Override
    public SecretDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item_data, parent, false);
        return new SecretDataViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return secretData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SecretDataViewHolder holder, int position) {
        SecretData data = secretData.get(position);
        holder.name.setText(data.getTitle());

        Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(v -> {
            Intent viewIntent = new Intent(context, ViewSecretDataActivity.class);
            viewIntent.putExtra("SECRET_DATA", data);
            context.startActivity(viewIntent);
        });
        holder.editBtn.setOnClickListener(v -> editDataClicked(context, data));
        holder.deleteBtn.setOnClickListener(v -> deleteDataClicked(context, data));
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v, Gravity.END);
            popupMenu.inflate(R.menu.menu_category_item);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menuItem_edit_category) {
                        editDataClicked(context, data);
                    } else if (item.getItemId() == R.id.menuItem_delete_category) {
                        deleteDataClicked(context, data);
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
    }

    private void editDataClicked(Context context, SecretData secretData) {
        Intent editIntent = new Intent(context, EditSecretDataActivity.class);
        editIntent.putExtra("SECRET_DATA", secretData);
        context.startActivity(editIntent);
    }

    private void deleteDataClicked(Context context, SecretData data) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.delete) + " \"" + data.getTitle() + "\"?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (secretDataDbHelper.removeData(data.getId(), SecretDataDbHelper.TYPE_ID)) {
                            update();
                            Utilities.showCustomToast(context, R.drawable.ic_info_24, context.getString(R.string.secret_deleted) + " (" + data.getTitle() + ")");
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
        this.secretData = this.secretDataDbHelper.getData(category.getUid(), category.getId());
        notifyDataSetChanged();
    }

    public void updateSecretData(String text) {
        if (text.trim().isEmpty()) {
            this.update();
            return;
        }
        this.secretData = this.secretDataDbHelper.getDataWithTextMatch(category.getUid(), category.getId(), text);
        notifyDataSetChanged();
    }

    static class SecretDataViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final ImageButton editBtn, deleteBtn;

        public SecretDataViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name_list_item_data);
            editBtn = itemView.findViewById(R.id.btn_edit_item_data);
            deleteBtn = itemView.findViewById(R.id.btn_delete_item_data);
        }
    }
}