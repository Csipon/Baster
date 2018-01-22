package com.team.baster.storage.core;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team.baster.AndroidInstanceHolder;


/**
 * Created by Pasha on 10/28/2017.
 */

public class SQLiteJDBC extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 5;
    private volatile static SQLiteJDBC jdbc;

    public SQLiteJDBC() {
        super(AndroidInstanceHolder.getAndroidLauncher(),  "/data/data/com.team.baster/baster.db", null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlConstant.CREATE_PLAYER_STATUS);
        db.execSQL(SqlConstant.INSERT_PLAYER_STATUS);
        db.execSQL(SqlConstant.CREATE_SCORE);
        db.execSQL(SqlConstant.CREATE_PRODUCT_TITLE);
        db.execSQL(SqlConstant.CREATE_PRODUCTS);
        db.execSQL(SqlConstant.CREATE_ACHIEVEMENTS);
        db.execSQL(SqlConstant.CREATE_ORDERS);
        System.out.println("DB is CREATED");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SqlConstant.DROP_TABLE_PRODUCT);
        db.execSQL(SqlConstant.DROP_TABLE_PRODUCT_TYPE);
        db.execSQL(SqlConstant.DROP_TABLE_SCORE);
        db.execSQL(SqlConstant.DROP_TABLE_ACHIEVEMENTS);
        db.execSQL(SqlConstant.DROP_TABLE_ACTUAL_PLAYER_STATUS);
        db.execSQL(SqlConstant.DROP_TABLE_ORDERS);
        onCreate(db);
    }

    public static SQLiteJDBC getJdbc() {
        if (jdbc == null){
            jdbc = new SQLiteJDBC();
        }
        return jdbc;
    }
}
