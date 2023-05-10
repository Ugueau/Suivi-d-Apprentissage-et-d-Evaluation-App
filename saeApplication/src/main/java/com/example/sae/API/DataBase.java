package com.example.sae.API;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s ="CREATE TABLE IF NOT EXISTS Login (token TEXT PRIMARY KEY NOT NULL, DateInit DATE NOT NULL, type TEXT);";
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade tables here
    }

    public void insertData(String token, String date, String type) {
        ContentValues values = new ContentValues();
        values.put("token", token);
        values.put("DateInit", date);
        values.put("type", type);
        db.insert("Login", null, values);
    }

    public Cursor selectAll() {
        Cursor cursor = db.query("Login", null, null, null, null, null, null);
        return cursor;
    }

    public void reset() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Login");
        onCreate(db);
    }

    public void insertOrUpdate(String token, String date, String type) {

        // Vérifie si le token existe déjà dans la base de données
        Cursor cursor = db.rawQuery("SELECT * FROM Login WHERE token = ?", new String[]{token});
        if (cursor.moveToFirst()) {
            // Si le token existe déjà, met à jour la ligne correspondante
            db.execSQL("UPDATE Login SET DateInit = ?, type = ? WHERE token = ?", new String[]{date, type, token});
        } else {
            // Sinon, insère une nouvelle ligne
            db.execSQL("INSERT INTO Login (token, DateInit, type) VALUES (?, ?, ?)", new String[]{token, date, type});
        }
        cursor.close();
    }
}
