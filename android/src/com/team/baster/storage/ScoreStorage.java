package com.team.baster.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.badlogic.gdx.utils.Array;
import com.team.baster.storage.core.SQLiteJDBC;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * Created by Pasha on 10/28/2017.
 */

public class ScoreStorage {
    private static final String TABLE_NAME = "score";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_DATE = "date";
    private static final int LIMIT_ROWS = 10;
    private static final String[] TABLE_COLUMNS  = {
            COLUMN_ID,
            COLUMN_SCORE,
            COLUMN_DATE
    };
    private SQLiteJDBC jdbc;
    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;

    public ScoreStorage() {
        jdbc = SQLiteJDBC.jdbc;
        writableDB = jdbc.getWritableDatabase();
        readableDB = jdbc.getReadableDatabase();
    }

    public void save(long score){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        String nowDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());;
        values.put(COLUMN_DATE, nowDate);

// Insert the new row, returning the primary key value of the new row
        long newRowId = writableDB.insert(TABLE_NAME, null,	values);
        System.out.println("INSERTED = " + newRowId + " rows");
    }


    public Array<Long> readLastBestScore() {
        // How you want the results sorted in the resulting Cursor
        String sortOrder =  COLUMN_SCORE + " DESC";
        Cursor curs = readableDB.query(
                TABLE_NAME,                                 // The table to query
                TABLE_COLUMNS,                              // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder,                                  // The sort order
                String.valueOf(LIMIT_ROWS)                  // Limit rows
        );
        Array<Long> result = new Array<>(LIMIT_ROWS);
        for (int i = 0; i < curs.getCount(); i++) {
            curs.moveToPosition(i);
            result.add(curs.getLong(curs.getColumnIndex(COLUMN_SCORE)));
            System.out.println(curs.getInt(curs.getColumnIndex("id")) + " = " + curs.getInt(curs.getColumnIndex("score")) + " --> " + curs.getString(curs.getColumnIndex("date")));
        }
        return result;
    }

}
