package com.team.baster.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team.baster.model.PlayerStatus;
import com.team.baster.storage.core.SQLiteJDBC;

/**
 * Created by Pasha on 10/29/2017.
 */

public class PlayerStatusStorage  {
    private static final String TABLE_NAME                  = "actual_player_status";
    private static final String COLUMN_ID                   = "id";
    private static final String COLUMN_PLAYER               = "player";
    private static final String COLUMN_OVERALL_SCORE        = "overall_score";
    private static final String COLUMN_ACTUAL_COINS         = "actual_coins";
    private static final String COLUMN_OVERALL_EXPERIENCE   = "overall_experience";
    private static final Integer ID = 1;

    public static int actualCoins       = 0;
    public static int overallExperience = 0;
    public static int overallScore      = 0;


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
        update( null, coins, score, null);
    }

    public void update(Integer experience){
        update(null,null, null, experience);
    }

    public void update(String playerName, Integer coins, Integer score, Integer experience){
        ContentValues values     = new ContentValues();
        String where             = COLUMN_ID + " = ?";
        String[] whereParams     = new String[]{String.valueOf(ID)};

        if (playerName != null){
            values.put(COLUMN_PLAYER, playerName);
        }
        if (coins != null){
            actualCoins         += coins;
            values.put(COLUMN_ACTUAL_COINS, actualCoins);
        }
        if (score != null){
            overallScore        += score;
            values.put(COLUMN_OVERALL_SCORE, overallScore);
        }
        if (experience != null){
            overallExperience   += experience;
            values.put(COLUMN_OVERALL_EXPERIENCE, overallExperience);
        }

        // Insert the new row, returning the primary key value of the new row
        long newRowId = writableDB.update(TABLE_NAME, values, where, whereParams);

        System.out.println("UPDATED = " + newRowId + " rows");
    }

    public PlayerStatus readPlayerStatus() {
        Cursor curs = readableDB.query(
                TABLE_NAME,                                 // The table to query
                TABLE_COLUMNS,                              // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        PlayerStatus playerStatus       = new PlayerStatus();

        curs.moveToFirst();
        playerStatus.coins              = curs.getInt(curs.getColumnIndex(COLUMN_ACTUAL_COINS));
        playerStatus.overallExperience  = curs.getInt(curs.getColumnIndex(COLUMN_OVERALL_EXPERIENCE));
        playerStatus.overallScore       = curs.getInt(curs.getColumnIndex(COLUMN_OVERALL_SCORE));
        playerStatus.id                 = curs.getInt(curs.getColumnIndex(COLUMN_ID));
        playerStatus.player             = curs.getString(curs.getColumnIndex(COLUMN_PLAYER));
        actualCoins                     = playerStatus.coins;
        overallExperience               = playerStatus.overallExperience;
        overallScore                    = playerStatus.overallScore;
        curs.close();
        return playerStatus;
    }
}
