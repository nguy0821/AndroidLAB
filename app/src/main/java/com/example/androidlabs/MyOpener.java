package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

class MyOpener extends SQLiteOpenHelper {

    private static final String DB_NAME= "MessageDB";
    private static final String DB_TABLE= "MessageTB";
    //columns
    private static final String COLUMN_MESSAGE = "Message";
    private static final String COLUMN_IS_SEND = "IsSent";
    private static final String COLUMN_MESSAGE_ID = "MessageID";
    //Queries
    private static final String CREATE_TABLE = "CREATE TABLE "+DB_TABLE+" ("+COLUMN_MESSAGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "                                  "+ COLUMN_MESSAGE+" TEXT, "
            + COLUMN_IS_SEND+" BIT);";

    //context: the Activity where the database is being opened
    //databaseName
    //CursorFactory: An object to create Cursor objects, normally this is null
    //int version – What is the version of your database
    public MyOpener(@Nullable Context context) {
        super(context,DB_NAME, null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    //inster DATA them thi phai dung getWrite
    public boolean insertData (String message, boolean inSent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MESSAGE, message);
        if(inSent){
            contentValues.put(COLUMN_IS_SEND,0);
        }else {
            contentValues.put(COLUMN_IS_SEND, 1);
        }

        long result = db.insert(DB_TABLE, null, contentValues);
        //if result =-1 => data khong insert
        return result != -1;
    }

    //delete row
    public void DeleteData( long id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.remove(COLUMN_MESSAGE,M);
        db.delete(DB_TABLE, COLUMN_MESSAGE_ID + " = ?",
                new String[] {String.valueOf(id) });
        db.close();
    }

    //ViewData dung de xem => dung getRead
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        return cursor;

    }
}
