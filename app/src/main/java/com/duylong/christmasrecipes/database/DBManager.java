package com.duylong.christmasrecipes.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

public class DBManager {
    private DatabaseHelper databaseHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public void updateDataBase() {
        databaseHelper.updateDataBase();
    }

    public Cursor fetch_data() {
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMNS, null, null ,null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return  cursor;
    }

    public Cursor fetch_data(String[] whereClauses) {
        String whereClause = TextUtils.join(" AND ", whereClauses);
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMNS, whereClause, null ,null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
