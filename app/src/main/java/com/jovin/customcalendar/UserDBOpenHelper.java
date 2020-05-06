package com.jovin.customcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_USER_TABLE = "create table "
            + UserDBStructure.USER_TABLE_NAME
            + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserDBStructure.NAME + " TEXT,"
            + UserDBStructure.PASSWORD + " TEXT,"
            + UserDBStructure.DESIGNATION + " TEXT)";

    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + UserDBStructure.USER_TABLE_NAME;

    UserDBOpenHelper(@Nullable Context context) {
        super(context, UserDBStructure.USER_DB_NAME, null, UserDBStructure.USER_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name, String password, String designation,SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDBStructure.NAME, name);
        contentValues.put(UserDBStructure.PASSWORD, password);
        contentValues.put(UserDBStructure.DESIGNATION, designation);

        long result = database.insert(UserDBStructure.USER_DB_NAME, null, contentValues);

        if (result == -1)
            return false;

        else
            return true;
    }

    public boolean readData(String name, String password, SQLiteDatabase database) {
        String[] projections = {
                UserDBStructure.NAME,
                UserDBStructure.PASSWORD,

        };

        String selection = UserDBStructure.NAME +"=? and "+UserDBStructure.PASSWORD +"=?";
        String[] selectionArgs = {name, password};

        Cursor cursor = database.query(UserDBStructure.USER_TABLE_NAME,projections,selection,selectionArgs,null,null,null);

//        Cursor cursor = database.rawQuery("Select * from " + UserDBStructure.USER_TABLE_NAME + " where " + UserDBStructure.NAME + "=? and " + UserDBStructure.PASSWORD + "=?", selectionArgs);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}


