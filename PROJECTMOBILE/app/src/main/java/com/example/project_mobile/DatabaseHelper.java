package com.example.project_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "UserLogin.db";

    private static final String TABLE_LOGIN = "login";

    private static final String COLUMN_ID = "id";

    private static final String TABLE_BOOKS = "books";

    private static final String COLUMN_BOOK_ID = "id";
    private static final String COLUMN_BOOK_BOOKID = "book_id";
    private static final String COLUMN_BOOK_USERID = "user_id";
    private static final String COLUMN_TIMESTAMP = "timestamp";


    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    private static final String CREATE_TABLE_BOOKS = "CREATE TABLE " + TABLE_BOOKS + "("
            + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BOOK_BOOKID + " INTEGER,"
            + COLUMN_BOOK_USERID + " INTEGER,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY (" + COLUMN_BOOK_USERID + ") REFERENCES " + TABLE_LOGIN + "(" + COLUMN_ID + ")" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(CREATE_TABLE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_BOOKS + " ADD COLUMN " + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP");
        }
    }
    public int loginUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_LOGIN, projection, selection, selectionArgs, null, null, null);
        int userID = -1;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                if (idColumnIndex != -1) {
                    userID = cursor.getInt(idColumnIndex);
                } else {
                    Log.e("loginUser", "COLUMN_ID not found in the cursor");
                }
            } else {
                Log.d("loginUser", "No user found with username: " + username);
            }
            cursor.close();
        } else {
            Log.e("loginUser", "Cursor is null");
        }
        db.close();

        return userID;
    }

    public Set<Integer> getBookBookmark(int userID){
        Set<Integer> bookId = new HashSet<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_BOOK_BOOKID};
        String selection = COLUMN_BOOK_USERID + " = ?";
        String[] selectionArgs = {String.valueOf(userID)};
        String orderBy = COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.query(TABLE_BOOKS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()){
            do {
                int bookID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_BOOKID));
                bookId.add(bookID);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookId;
    }

    public void insertBookmarkBook(int book_id, int userId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_BOOKID, book_id);
        values.put(COLUMN_BOOK_USERID, userId);
        db.insert(TABLE_BOOKS, null, values);
    }
    public void addLogin(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_LOGIN, null, values);
        db.close();
    }

    public Cursor getAllLogins() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LOGIN, null);
    }
    public boolean isValidLogin(String username, String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "(" + COLUMN_USERNAME + " = ? OR " + COLUMN_EMAIL + " = ?) AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, email, password};
        Cursor cursor = db.query(TABLE_LOGIN, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_LOGIN, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public boolean deleteBook(int bookId, int username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_BOOK_BOOKID + "=? AND " + COLUMN_BOOK_USERID + "=?" ;
        String[] selectionArgs = new String[]{String.valueOf(bookId),String.valueOf(username)};
        boolean books = db.delete("books", selection, selectionArgs) > 0;
        db.close();
        return books;
    }

}
