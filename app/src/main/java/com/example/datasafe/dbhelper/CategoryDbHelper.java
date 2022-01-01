package com.example.datasafe.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.datasafe.models.Category;

import java.util.ArrayList;

public class CategoryDbHelper extends DbHelper {
    public static final String TABLE_CATEGORY = "CATEGORY";
    public static final String C1 = "ID";
    public static final String C2 = "UID";
    public static final String C3 = "NAME";

    public CategoryDbHelper(@Nullable Context context) {
        super(context);
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {
        String categoryTableQuery;
        categoryTableQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s TEXT NOT NULL)", TABLE_CATEGORY, C1, C2, C3);
        db.execSQL(categoryTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_CATEGORY);
    }*/

    public boolean addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C2, category.getUid());
        cv.put(C3, category.getName());
        return db.insert(TABLE_CATEGORY, null, cv) != -1;
    }

    public boolean deleteCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        // TODO: 01/01/2022 Deleting category will also delete its relevant data
        return db.delete(TABLE_CATEGORY, "ID = ?", new String[]{String.valueOf(category.getId())}) > 0;
    }

    public ArrayList<Category> getAllCategories(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE UID = ?", new String[]{String.valueOf(userId)});
        ArrayList<Category> data = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                data.add(new Category(c.getInt(0), userId, c.getString(2)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return data;
    }

    public boolean updateCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C2, category.getUid());
        cv.put(C3, category.getName());
        return db.update(TABLE_CATEGORY, cv, "ID = ?", new String[]{String.valueOf(category.getId())}) != -1;
    }
}
