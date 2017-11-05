package com.example.database;

/**
 * Created by Pasha on 10/28/2017.
 */

public class SqlConstant {

    public static final String CREATE_TABLE_SCORE = "CREATE TABLE \"score\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"score\" integer NOT NULL,\n" +
            " \"date\" text NOT NULL\n" +
            ");";

    public static final  String DROP_TABLES = "DROP TABLE score;";
    public static final  String SELECT = "SELECT * FROM score;";
    public static final  String INSERT = "INSERT INTO score(score, date) VALUES(123, 'asdasd');";
}
