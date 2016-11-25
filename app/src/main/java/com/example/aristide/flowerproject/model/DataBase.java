package com.example.aristide.flowerproject.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.security.AccessControlContext;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by --- on 24/11/2016.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Plants";

    public DataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+FlowerTable.TABLE_NAME+"(id INTEGER PRIMARY KEY NOT NULL, name VARCHAR(100), days INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static class FlowerTable implements BaseColumns {
        public static final String TABLE_NAME = "Plant";
        public static final String PLANT_NAME = "name";
        public static final String DAYS_NUMBER = "days";
    }

    public ArrayList<String> getFlowers() {
        ArrayList<String> names = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] fields = {
                FlowerTable.PLANT_NAME, FlowerTable.DAYS_NUMBER
        };
        Cursor cursor = db.query(FlowerTable.TABLE_NAME, fields, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return names;
    }

    public long putPlant(String name, int days) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlowerTable.PLANT_NAME, name);
        values.put(FlowerTable.DAYS_NUMBER, days);
        return db.insert(FlowerTable.TABLE_NAME, null, values);
    }
}
