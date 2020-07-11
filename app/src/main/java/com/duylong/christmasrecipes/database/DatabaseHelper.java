package com.duylong.christmasrecipes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";

    private static String DB_NAME = "christmas_recipes.db";

    public static String TABLE_NAME = "recipes";

    public static String[] COLUMNS = new String[] { "id", "name", "image_url", "skill_level", "description", "nutrition", "ingredients", "methods" };

    private  static final int DB_VERSION = 1;

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.context = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                InputStream inputStream = context.getAssets().open(DB_NAME);
                OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] mBuffer = new byte[1024];
                int mLength;
                while ((mLength = inputStream.read(mBuffer)) > 0)
                    outputStream.write(mBuffer, 0, mLength);

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
