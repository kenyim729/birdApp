package com.example.myapplication.myHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDbAdapter {
    private static String DB_NAME = "EA_DB";
    private static int DB_VERSION = 1;
    private static String TABLE_NAME = "users";

    private Context context;
    private SQLiteOpenHelper myDbHelper;

    //create db
    public MyDbAdapter(Context context) {
        this.context = context;
        this.myDbHelper = new MyDbHelper(this.context, DB_NAME, null, DB_VERSION);
    }

    //add one book
    public long addUser(User user) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("userName", user.getUserName());
        contentValues.put("password", user.getPassword());

        SQLiteDatabase sqlDB = myDbHelper.getWritableDatabase();
        long numRowsInserted = sqlDB.insert(TABLE_NAME, null, contentValues);
        sqlDB.close();

        return numRowsInserted;
    }

    //select all books
    public Cursor getAllUsers() {
        SQLiteDatabase sqlDB = myDbHelper.getReadableDatabase();

        Cursor cursor = sqlDB.query(
                TABLE_NAME,
                new String[]{"_id","userName","password"},
                null,
                null,
                null,
                null,
                null);

        displayCursor(cursor);

        sqlDB.close();
        return cursor;
    }

    //delete all books
    public void deleteAllUsers() {
        SQLiteDatabase sqlDB = myDbHelper.getWritableDatabase();

        int numRowsDeleted = sqlDB.delete(
                TABLE_NAME,
                null,
                null
        );
        sqlDB.close();
    }

    //display in logcat
    private void displayCursor(Cursor cursor) {
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String userName = cursor.getString(1);
            String password = cursor.getString(2);

            System.out.println("_id: " + id + " userName " + userName + " password: " + password);
            cursor.moveToNext();
        }
    }

    public List<String> getAllUsersName (Cursor cursor) {
        List<String> tempList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tempList.add( cursor.getString(1) );
            cursor.moveToNext();
        }
        return tempList;
    }

    public List<User> getAllUsers (Cursor cursor) {
        List<User> tempList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String username = cursor.getString(1);
            String password = cursor.getString(2);

            tempList.add( new User(username, password) );
            cursor.moveToNext();
        }
        return tempList;
    }

}
