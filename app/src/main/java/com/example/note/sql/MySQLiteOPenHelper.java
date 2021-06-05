package com.example.note.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOPenHelper extends SQLiteOpenHelper {
    public static final String CREATE_NOTE = "create table Note("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "text text,"
            + "time text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE);
    }

    public MySQLiteOPenHelper(@Nullable Context context, @Nullable String name,
                              @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Note");
        onCreate(db);
    }
}
