package com.team.baster.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.badlogic.gdx.utils.Array;
import com.team.baster.storage.core.SQLiteJDBC;
import com.team.baster.storage.model.Score;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Pasha on 10/28/2017.
 */

public class ScoreStorage {
    private static final String TABLE_NAME              = "score";
    private static final String COLUMN_ID               = "id";
    private static final String COLUMN_SCORE            = "score";
    private static final String COLUMN_DATE             = "date";
    private static final String COLUMN_QUEUE_FOR_SEND   = "isForBack";
    private static final String COLUMN_LOGIN            = "login";
    private static final int LIMIT_ROWS                 = 10;
    private static final String[] TABLE_COLUMNS         = {
            COLUMN_ID,
            COLUMN_SCORE,
            COLUMN_DATE,
            COLUMN_LOGIN,
            COLUMN_QUEUE_FOR_SEND
    };
    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;

    public ScoreStorage() {
        SQLiteJDBC jdbc = SQLiteJDBC.jdbc;
        writableDB = jdbc.getWritableDatabase();
        readableDB = jdbc.getReadableDatabase();
    }

    public void save(int score, String login) {
        ContentValues values = new ContentValues();
        String nowDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_DATE, nowDate);
        values.put(COLUMN_LOGIN, login);
// Insert the new row, returning the primary key value of the new row
        long newRowId = writableDB.insert(TABLE_NAME, null, values);
    }

    public long saveForQueue(Score score) {
        ContentValues values = new ContentValues();
        String nowDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        values.put(COLUMN_SCORE, score.getScore());
        values.put(COLUMN_DATE, nowDate);
        values.put(COLUMN_LOGIN, score.getLogin());
        values.put(COLUMN_QUEUE_FOR_SEND, score.isForBack());
        System.out.println(values);
        return writableDB.insert(TABLE_NAME, null, values);
    }

    // TODO: 09.11.17 save scores for backup, maybe use batch insert if we can do it in SQLLite
    public long saveForQueue(List<Score> scores) {
        writableDB.beginTransaction();
        for (Score score : scores) {
            saveForQueue(score);
        }
        writableDB.setTransactionSuccessful();
        writableDB.endTransaction();
        return 1;
    }

    public List<Score> readFromBackupQueue() {
        readableDB.beginTransaction();
        List<Score> result = new ArrayList<>();
        try {
            Cursor curs = readableDB.query(
                    TABLE_NAME,                                 // The table to query
                    TABLE_COLUMNS,                              // The columns to return
                    COLUMN_QUEUE_FOR_SEND + " = 1",                                       // The columns for the WHERE clause
                    null,                                       // The values for the WHERE clause
                    null,                                       // don't group the rows
                    null,                                       // don't filter by row groups
                    null,                                  // The sort order
                    null                 // Limit rows
            );

            while (curs.moveToNext()) {
                Score score = new Score();
                score.setLogin(curs.getString(curs.getColumnIndex(COLUMN_LOGIN)));
                /// TODO: 08.11.17 dmch need to set Date
                score.setDate(new Date(System.currentTimeMillis()));
                score.setForBack(curs.getInt(curs.getColumnIndex(COLUMN_QUEUE_FOR_SEND)) == 1);
                score.setScore(curs.getInt(curs.getColumnIndex(COLUMN_SCORE)));
                result.add(score);
            }

            //todo delete rows with @isForBack = true

            readableDB.setTransactionSuccessful();

            // TODO: 08.11.17 Exception
        } catch (Exception e){
            System.out.println("From backup exception" + e);
        }finally {
            readableDB.endTransaction();
        }
        return result;
    }


    public List<Long> readLastBestScore() {
        // How you want the results sorted in the resulting Cursor
        String sortOrder = COLUMN_SCORE + " DESC";
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
        List<Long> result = new ArrayList<>(LIMIT_ROWS);
        for (int i = 0; i < curs.getCount(); i++) {
            curs.moveToPosition(i);
            result.add(curs.getLong(curs.getColumnIndex(COLUMN_SCORE)));
        }
        curs.close();
        return result;
    }

}
