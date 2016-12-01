package com.example.aristide.flowerproject.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.controller.Adapter;
import com.example.aristide.flowerproject.model.Plant;

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
        db.execSQL("CREATE TABLE "+FlowerTable.TABLE_NAME+"(id INTEGER PRIMARY KEY autoincrement, "+FlowerTable.PLANT_NAME+" VARCHAR(100), "+FlowerTable.FREQUENCY_NUMBER+" INT, "+FlowerTable.LAST_WATER_DATE+" VARCHAR(100), "+FlowerTable.STATE+" INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static class FlowerTable implements BaseColumns {
        public static final String TABLE_NAME = "Plant";
        public static final String PLANT_NAME = "name";
        public static final String FREQUENCY_NUMBER = "days";
        public static final String LAST_WATER_DATE = "date";
        public static final String STATE = "state";
    }

    /**
     *
     * @return a list of names
     */
    public ArrayList<String> getFlowersNames() {
        ArrayList<String> names = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] fields = {
                FlowerTable.PLANT_NAME, FlowerTable.FREQUENCY_NUMBER, FlowerTable.LAST_WATER_DATE
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
     * @return a list of names
     */
    public ArrayList<Plant> getFlowers() {
        ArrayList<Plant> plants = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] fields = {
                FlowerTable.PLANT_NAME, FlowerTable.FREQUENCY_NUMBER, FlowerTable.LAST_WATER_DATE
        };
        Cursor cursor = db.query(FlowerTable.TABLE_NAME, fields, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Plant p = new Plant();
            p.setImageResId(R.drawable.red_flower);
            p.setName(cursor.getString(cursor.getColumnIndex(FlowerTable.PLANT_NAME)));
            p.setDays(cursor.getInt(cursor.getColumnIndex(FlowerTable.FREQUENCY_NUMBER)));
            p.setDate(cursor.getString(cursor.getColumnIndex(FlowerTable.LAST_WATER_DATE)));
            plants.add(p);
            cursor.moveToNext();
        }
        return plants;
    }


    /**
     * To insert a new Plant in the DB
     * @param name the name of the plant
     * @param frequency watering frequency
     * @return result a code from the insert request
     */
    public long insertPlant(String name, int frequency, String date, int state) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlowerTable.PLANT_NAME, name);
        values.put(FlowerTable.FREQUENCY_NUMBER, frequency);
        values.put(FlowerTable.LAST_WATER_DATE, date);
        values.put(FlowerTable.STATE, state);
        long result = db.insert(FlowerTable.TABLE_NAME, null, values);
        db.close();
        return result;
    }



    /**
     *  To select the plant by ID
     * @param id of the plant
     * @return row a name and the frequency
     */
    /*public String[] selectPlant(int id){
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }
        String[] row = {"",""};
        Cursor cursor = db.rawQuery("SELECT name, frequency FROM Plant WHERE id = ?", new String[] { String.valueOf(id) });

        if(cursor != null){
            if (cursor.moveToNext()) {
                row[0] = cursor.getString(0);
                row[1] = String.valueOf(cursor.getInt(1));
            }
            cursor.close();
        }
        db.close();
        return row;
    }*/

    /**
     * Updates a row in the DB
     * @param id of the plant
     * @param name The new name value
     * @param frequency The new frequency value
     * @return result a code from the update request
     */
    public long updatePlant(int id, String name, int frequency, String last_water_date, int state) {
        SQLiteDatabase db = getWritableDatabase();
        if (db == null) return Adapter._ERROR;
        ContentValues row = new ContentValues();
        row.put(FlowerTable.PLANT_NAME, name);
        row.put(FlowerTable.FREQUENCY_NUMBER, frequency);
        row.put(FlowerTable.LAST_WATER_DATE, last_water_date);
        row.put(FlowerTable.STATE, state);
        long result = db.update(FlowerTable.TABLE_NAME, row, "id = ?", new String[] { String.valueOf(id) } );
        db.close();
        return result;
    }

    /**
     *
     * @param id
     */
    public void delete(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String where = "id = ?";
        String[] whereArgs = { String.valueOf(id) };

        sqLiteDatabase.delete(FlowerTable.PLANT_NAME, where, whereArgs);
    }
}
