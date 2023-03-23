package com.example.zhuce_xxff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_AIHAO = "aihao";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_AIHAO + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_AIHAO, user.getAihao());

        long id = db.insert(TABLE_USERS, null, values);
        db.close();

        return id;
    }
    public long updata(String usen,String dd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, dd);

        long id = db.update(TABLE_USERS, values, "username=?", new String[]{usen});
        db.close();

        return id;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {COLUMN_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


//    public String getuses(String username, String password) {
//        String[] columns = {COLUMN_ID};
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
//        String[] selectionArgs = {username, password};
//
//        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
//        String name;
//        if (cursor.moveToFirst()) {
//             cursor.getString(cursor.getColumnIndex(username));
//        }
//        return name;
//    }

    public String getPasswordByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        String password = null;

        if (cursor != null && cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
        }

        cursor.close();
        db.close();

        return password;
    }

    public User getUserzh(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_AIHAO};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            String uname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String pwd = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String aihao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AIHAO));

            user = new User(uname, pwd, aihao);
        }

        cursor.close();
        db.close();

        return user;
    }


}