package com.team.baster.storage.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Pasha on 10/28/2017.
 */

public class SQLiteJDBC extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "baster.db";
    public static SQLiteJDBC jdbc;

    public SQLiteJDBC(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlConstant.CREATE_DB_SCRIPT);
        System.out.println("DB is CREATED");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SqlConstant.DROP_TABLES);
        onCreate(db);
    }
}
