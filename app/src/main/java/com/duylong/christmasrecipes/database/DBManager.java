package com.duylong.christmasrecipes.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

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

    public Cursor fetch_data() {
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMNS, null, null ,null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return  cursor;
    }
}
