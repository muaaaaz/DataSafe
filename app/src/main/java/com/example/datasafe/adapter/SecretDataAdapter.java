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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasafe.R;
import com.example.datasafe.activities.secretData.EditSecretDataActivity;
import com.example.datasafe.dbhelper.SecretDataDbHelper;
import com.example.datasafe.models.Category;
import com.example.datasafe.models.SecretData;

import java.util.ArrayList;

public class SecretDataAdapter extends RecyclerView.Adapter<SecretDataAdapter.SecretDataViewHolder> {
    SecretDataDbHelper secretDataDbHelper;
    Category category;
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
        // TODO: 02/01/2022 SetOnClickListener
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v, Gravity.END);
            popupMenu.inflate(R.menu.menu_category_item);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menuItem_edit_category) {
                        Intent editIntent = new Intent(context, EditSecretDataActivity.class);
                        editIntent.putExtra("SECRET_DATA", data);
                        context.startActivity(editIntent);
                    } else if (item.getItemId() == R.id.menuItem_delete_category) {
                        new AlertDialog.Builder(context)
                                .setTitle(context.getString(R.string.delete) + " \"" + data.getTitle() + "\"?")
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        secretDataDbHelper.removeData(data.getId(), SecretDataDbHelper.TYPE_ID);
                                        update();
                                        Toast.makeText(context, context.getString(R.string.secret_deleted) + " (" + data.getTitle() + ")", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
    }

    public void update() {
        this.secretData = this.secretDataDbHelper.getData(category.getUid(), category.getId());
        notifyDataSetChanged();
    }

    class SecretDataViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public SecretDataViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name_list_item_data);
        }
    }
}