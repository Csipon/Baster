package com.team.baster.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team.baster.model.PlayerStatus;
import com.team.baster.storage.core.SQLiteJDBC;

import java.util.Arrays;

/**
 * Created by Pasha on 10/29/2017.
 */

public class PlayerStatusStorage  {
    private static final String TABLE_NAME = "actual_player_status";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PLAYER = "player";
    private static final String COLUMN_OVERALL_SCORE = "overall_score";
    private static final String COLUMN_ACTUAL_COINS = "actual_coins";
    private static final String COLUMN_OVERALL_EXPERIENCE = "overall_experience";

    public static int actualCoins = 0;
    public static int overallExperience = 0;
    public static int overallScore = 0;


    private static final String[] TABLE_COLUMNS  = {
            COLUMN_ID,
            COLUMN_PLAYER,
            COLUMN_OVERALL_SCORE,
            COLUMN_ACTUAL_COINS,
            COLUMN_OVERALL_EXPERIENCE
    };
    private SQLiteJDBC jdbc;
    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;

    public PlayerStatusStorage() {
        jdbc = SQLiteJDBC.jdbc;
        writableDB = jdbc.getWritableDatabase();
        readableDB = jdbc.getReadableDatabase();
        readPlayerStatus();
    }


    public void update(Integer coins, Integer score){
        update(null, null, coins, score, null);
    }

    public void update(Integer experience){
        update(null, null,null, null, experience);
    }

    public void update(Integer id, String player, Integer coins, Integer score, Integer experience){
        ContentValues values = new ContentValues();
        String where = null;
        String[] whereParams = null;
        if (id != null){
            where = COLUMN_ID + " = ?";
            whereParams = new String[]{String.valueOf(id)};
        }
        if (player != null){
            values.put(COLUMN_PLAYER, player);
        }
        if (coins != null){
            actualCoins += coins;
            values.put(COLUMN_ACTUAL_COINS, actualCoins);
        }
        if (score != null){
            overallScore += score;
            values.put(COLUMN_OVERALL_SCORE, overallScore);
        }
        if (experience != null){
            overallExperience += experience;
            values.put(COLUMN_OVERALL_EXPERIENCE, overallExperience);
        }

        // Insert the new row, returning the primary key value of the new row
        long newRowId = writableDB.update(TABLE_NAME, values, where, whereParams);

        System.out.println("UPDATED = " + newRowId + " rows");
    }

    public PlayerStatus readPlayerStatus() {
        // How you want the results sorted in the resulting Cursor
//        String selection = COLUMN_PLAYER + " = ?";
//        String[] selectionArgs = {COLUMN_PLAYER};
        Cursor curs = readableDB.query(
                TABLE_NAME,                                 // The table to query
                TABLE_COLUMNS,                              // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        PlayerStatus playerStatus = new PlayerStatus();

        curs.moveToFirst();
        playerStatus.coins = curs.getInt(curs.getColumnIndex(COLUMN_ACTUAL_COINS));
        playerStatus.overallExperience = curs.getInt(curs.getColumnIndex(COLUMN_OVERALL_EXPERIENCE));
        playerStatus.overallScore = curs.getInt(curs.getColumnIndex(COLUMN_OVERALL_SCORE));
        playerStatus.id = curs.getInt(curs.getColumnIndex(COLUMN_ID));
        playerStatus.player = curs.getString(curs.getColumnIndex(COLUMN_PLAYER));
        System.out.println("COLUMNS ---------->>>>> " + Arrays.toString(curs.getColumnNames()));
//        playerStatus.id = 1;
//        playerStatus.player = "player";
        actualCoins = playerStatus.coins;
        overallExperience = playerStatus.overallExperience;
        overallScore = playerStatus.overallScore;
        curs.close();
        return playerStatus;
    }
}
