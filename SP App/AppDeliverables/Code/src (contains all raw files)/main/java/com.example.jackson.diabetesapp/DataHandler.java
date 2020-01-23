package com.example.jackson.diabetesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
    public static final int TIME = 0;
    public static final int BG = 0;
    public static final int CARBS = 0;
    public static final int PROTEIN = 0;

    public static final int FAT = 0;

    public static final String TABLE_NAME = "dataTable";
    public static final String DATABASE_NAME = "database.sqlite";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE = "create table dataTable (time integer not null,bg integer not null, carbs integer not null,protein integer not null,fat integer not null);";

    DataBaseHelper dbhelper;
    Context context;

    SQLiteDatabase db;

    public DataHandler(Context context) {
        this.context = context;
        dbhelper = new DataBaseHelper(context);
    }


    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(TABLE_CREATE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS table");
            onCreate(db);

        }
    }

    public DataHandler open() {

        db = dbhelper.getWritableDatabase();
        return this;
    }


    public void close() {
        dbhelper.close();

    }

    public void delete() {

        db.execSQL("delete from " + TABLE_NAME);
    }

    public long insertData(int time, int bg, int carbs, int protein, int fat) {
        ContentValues content = new ContentValues();
        content.put("TIME", time);
        content.put("BG", bg);
        content.put("CARBS", carbs);
        content.put("PROTEIN", protein);
        content.put("FAT", fat);

        return db.insertOrThrow(TABLE_NAME, null, content);

    }

    public Cursor returnData() {

        return db.query(TABLE_NAME, new String[]{"TIME", "BG", "CARBS", "PROTEIN", "FAT"}, null, null, null, null, null);


    }


}
