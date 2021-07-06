package com.example.myapplication.myHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {

    //create db
    public MyDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE users (_id INTEGER PRIMARY KEY, userName TEXT, password TEXT)";
        System.out.println(sql);

        db.execSQL( sql );
    }

    //recreate table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql =
                "DROP TABLE IF EXISTS users";
        System.out.println(sql);

        db.execSQL( sql );

        onCreate( db );
    }
}
