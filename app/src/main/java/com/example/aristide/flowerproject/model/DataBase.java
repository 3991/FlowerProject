package com.example.aristide.flowerproject.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;



/**
 *
 */
public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Plants";

    public DataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+FlowerTable.TABLE_NAME+"(id INTEGER PRIMARY KEY autoincrement, name VARCHAR(100), days INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static class FlowerTable implements BaseColumns {
        public static final String TABLE_NAME = "Plant";
        public static final String PLANT_NAME = "name";
        public static final String DAYS_NUMBER = "days";
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param name
     * @param days
     * @return
     */
    public long putPlant(String name, int days) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlowerTable.PLANT_NAME, name);
        values.put(FlowerTable.DAYS_NUMBER, days);
        return db.insert(FlowerTable.TABLE_NAME, null, values);
    }

    /**
     *
     * @param id
     */
    public String[] selectPlant(int id){
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }
        String[] row = {"",""};
        Cursor cursor = db.rawQuery("SELECT name, days FROM Plant WHERE id = ?", new String[] { String.valueOf(id) });

        if(cursor != null){
            if (cursor.moveToNext()) {
                Log.d("SIZE", String.valueOf(cursor.getInt(1)));Log.d("SIZE2", cursor.getString(0));
                row[0] = cursor.getString(0);
                row[1] = String.valueOf(cursor.getInt(1));
            }
            cursor.close();
            db.close();

        }

        return row;
    }
}
