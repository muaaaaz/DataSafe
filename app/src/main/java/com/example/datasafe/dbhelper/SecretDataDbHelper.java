package com.example.datasafe.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.datasafe.models.SecretData;

import java.util.ArrayList;

public class SecretDataDbHelper extends DbHelper {
    public static final String TABLE_DATA = "SECRET_DATA";
    public static final String C1 = "ID";
    public static final String C2 = "UID";
    public static final String C3 = "CID";
    public static final String C4 = "TITLE";
    public static final String C5 = "DATA";
    public static final String TYPE_ID = "ID";
    public static final String TYPE_CID = "CID";

    public SecretDataDbHelper(@Nullable Context context) {
        super(context);
    }

    public boolean addData(SecretData data) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_DATA, null, getContentValues(data)) == -1;
    }

    /**
     * @param id   category id
     * @param TYPE constant defined in SecretDataDbHelper
     * @return true if deleted, false otherwise
     */
    public boolean removeData(int id, String TYPE) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_DATA, String.format("%s = ?", TYPE), new String[]{String.valueOf(id)}) > 0;
    }

    public boolean updateData(SecretData data) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(TABLE_DATA, getContentValues(data), "ID = ?", new String[]{String.valueOf(data.getId())}) > 0;
    }

    public ArrayList<SecretData> getData(int uid, int cid) {
        ArrayList<SecretData> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DATA + " WHERE UID = ? AND CID = ?", new String[]{String.valueOf(uid), String.valueOf(cid)});
        if (cursor.moveToFirst()) {
            do {
                data.add(new SecretData(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return data;
    }

    public ArrayList<SecretData> getDataWithTextMatch(int uid, int cid, String text) {
        ArrayList<SecretData> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DATA + " WHERE UID = ? AND CID = ? AND TITLE LIKE ?", new String[]{String.valueOf(uid), String.valueOf(cid), "%" + text + "%"});
        if (cursor.moveToFirst()) {
            do {
                data.add(new SecretData(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return data;
    }

    private ContentValues getContentValues(SecretData data) {
        ContentValues cv = new ContentValues();
        cv.put(C2, data.getUid());
        cv.put(C3, data.getCid());
        cv.put(C4, data.getTitle());
        cv.put(C5, data.getData());
        return cv;
    }
}
