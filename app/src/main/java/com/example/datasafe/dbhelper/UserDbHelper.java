package com.example.datasafe.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.datasafe.models.User;

public class UserDbHelper extends DbHelper {
    public static final String DATABASE_NAME = "DATASAFE.db";
    public static final String TABLE_USER = "USER";
    public static final String TABLE_USER_C1 = "ID";
    public static final String TABLE_USER_C2 = "USERNAME";
    public static final String TABLE_USER_C3 = "PASSWORD";

    public UserDbHelper(@Nullable Context context) {
        super(context);
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {
        String userTableQuery;
        userTableQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL)", TABLE_USER, TABLE_USER_C1, TABLE_USER_C2, TABLE_USER_C3);
        db.execSQL(userTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USER);
    }*/

    public boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_USER_C2, user.getUsername());
        cv.put(TABLE_USER_C3, user.getPassword());
        return db.insert(TABLE_USER, null, cv) != -1;
    }

    public boolean deleteUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_USER, "ID = ?", new String[]{String.valueOf(user.getId())}) > 0;
    }

    public User verifyUser(User user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        if (c.moveToFirst()) {
            do {
                if (user.getUsername().equals(c.getString(1)) && user.getPassword().equals(c.getString(2))) {
                    user.setId(c.getInt(0));
                    c.close();
                    return user;
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return null;
    }

    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = getReadableDatabase();
        boolean result = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE USERNAME = ?", new String[]{username}).getCount() > 0;
        db.close();
        return result;
    }
}
