package com.team.baster.storage.core;

/**
 * Created by Pasha on 10/28/2017.
 */

public class SqlConstant {

    public static final  String DROP_TABLE_ACTUAL_PLAYER_STATUS = "DROP TABLE actual_player_status;";
    public static final  String DROP_TABLE_PRODUCT              = "DROP TABLE product;";
    public static final  String DROP_TABLE_PRODUCT_TYPE         = "DROP TABLE product_type;";
    public static final  String DROP_TABLE_SCORE                = "DROP TABLE score;";
    public static final  String DROP_TABLE_ORDERS               = "DROP TABLE orders;";
    public static final  String DROP_TABLE_ACHIEVEMENTS         = "DROP TABLE achievements;";


    public static final  String CREATE_PLAYER_STATUS =
            "CREATE TABLE \"actual_player_status\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"player\" text NOT NULL,\n" +
            " \"overall_score\" integer NOT NULL,\n" +
            " \"actual_coins\" integer NOT NULL,\n" +
            " \"overall_experience\" integer NOT NULL\n" +
            ");";

    public static final  String CREATE_PRODUCTS =
            "CREATE TABLE \"product\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text NOT NULL,\n" +
            " \"donat_price\" integer NOT NULL,\n" +
            " \"coin_price\" integer NOT NULL,\n" +
            " \"max_count\" integer NOT NULL,\n" +
            " \"product_type_id_fk\" integer NOT NULL,\n" +
            "              FOREIGN KEY (product_type_id_fk) REFERENCES product_type(id)\n" +
            ");";

    public static final  String CREATE_PRODUCT_TITLE =
            "CREATE TABLE \"product_type\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text NOT NULL\n" +
            ");";

    public static final String CREATE_ACHIEVEMENTS =
            "CREATE TABLE \"achievements\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text NOT NULL,\n" +
            " \"is_completed\" text NOT NULL,\n" +
            " \"coin_reward\" integer NOT NULL,\n" +
            " \"experience \" integer NOT NULL,\n" +
            " \"date_completed\" text NOT NULL\n" +
            ");";

    public static final String CREATE_ORDERS =
            "CREATE TABLE \"orders\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"type_currency\" text NOT NULL,\n" +
            " \"actual_count\" text NOT NULL,\n" +
            " \"date_bought\" text NOT NULL,\n" +
            " \"last_date_bought\" text NOT NULL,\n" +
            "    \"product_id_fk\" integer NOT NULL,\n" +
            "             FOREIGN KEY (product_id_fk) REFERENCES product(id)\n" +
            ");";

    public static final String INSERT_PLAYER_STATUS =
            "INSERT INTO actual_player_status(player, overall_score, actual_coins, overall_experience) VALUES('player', 0, 0, 0);";

    public static final String CREATE_SCORE =
            "CREATE TABLE \"score\" (\n" +
                    " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
                    " \"score\" integer NOT NULL,\n" +
                    " \"login\" text NOT NULL,\n" +
                    " \"isForBack\" boolean NOT NULL,\n" +
                    " \"date\" text NOT NULL\n" +
                    ");\n";
}
