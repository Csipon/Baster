package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Pasha on 10/28/2017.
 */

public class SQLiteTest {

    public SQLiteTest(Context context) {
        SQLiteDB jdbc = new SQLiteDB(context);
        insert(jdbc.getWritableDatabase());
        readData(jdbc.getReadableDatabase());
    }

    private void insert(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("score", 5555);
        values.put("date", "111111111");

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert("score", null,	values);
        System.out.println("INSERTED = " + newRowId + " rows");
    }


    private void readData(SQLiteDatabase db){
        String[] projection = {
                "id",
                "score",
                "date"
        };

// How you want the results sorted in the resulting Cursor

        Cursor c = db.query(
                "score",  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            System.out.println(c.getInt(c.getColumnIndex("id")) +" = " + c.getInt(c.getColumnIndex("score")) + " --> " + c.getString(c.getColumnIndex("date")));
        }
    }
}
