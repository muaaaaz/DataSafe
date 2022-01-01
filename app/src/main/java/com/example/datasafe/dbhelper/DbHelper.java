package com.example.datasafe.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DATASAFE.db";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTableQuery;
        userTableQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL)", UserDbHelper.TABLE_USER, UserDbHelper.TABLE_USER_C1, UserDbHelper.TABLE_USER_C2, UserDbHelper.TABLE_USER_C3);
        db.execSQL(userTableQuery);

        String categoryTableQuery;
        categoryTableQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s TEXT NOT NULL)", CategoryDbHelper.TABLE_CATEGORY, CategoryDbHelper.C1, CategoryDbHelper.C2, CategoryDbHelper.C3);
        db.execSQL(categoryTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + UserDbHelper.TABLE_USER);
        db.execSQL("DROP TABLE " + CategoryDbHelper.TABLE_CATEGORY);
    }
}
