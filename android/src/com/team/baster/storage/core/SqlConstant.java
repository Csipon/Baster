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
            " \"player\" text,\n" +
            " \"overall_score\" integer,\n" +
            " \"actual_coins\" integer,\n" +
            " \"overall_experience\" integer\n" +
            ");";

    public static final  String CREATE_PRODUCTS =
            "CREATE TABLE \"product\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text,\n" +
            " \"donat_price\" integer,\n" +
            " \"coin_price\" integer,\n" +
            " \"max_count\" integer,\n" +
            " \"product_type_id_fk\" integer,\n" +
            "              FOREIGN KEY (product_type_id_fk) REFERENCES product_type(id)\n" +
            ");";

    public static final  String CREATE_PRODUCT_TITLE =
            "CREATE TABLE \"product_type\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text \n" +
            ");";

    public static final String CREATE_ACHIEVEMENTS =
            "CREATE TABLE \"achievements\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text,\n" +
            " \"is_completed\" text,\n" +
            " \"coin_reward\" integer ,\n" +
            " \"experience \" integer,\n" +
            " \"date_completed\" text\n" +
            ");";

    public static final String CREATE_ORDERS =
            "CREATE TABLE \"orders\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"type_currency\" text ,\n" +
            " \"actual_count\" text ,\n" +
            " \"date_bought\" text ,\n" +
            " \"last_date_bought\" text ,\n" +
            "    \"product_id_fk\" integer ,\n" +
            "             FOREIGN KEY (product_id_fk) REFERENCES product(id)\n" +
            ");";

    public static final String INSERT_PLAYER_STATUS =
            "INSERT INTO actual_player_status(player, overall_score, actual_coins, overall_experience) VALUES('player', 0, 0, 0);";

    public static final String CREATE_SCORE =
            "CREATE TABLE \"score\" (\n" +
                    " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
                    " \"score\" integer ,\n" +
                    " \"login\" text ,\n" +
                    " \"isForBack\" boolean,\n" +
                    " \"date\" text \n" +
                    ");\n";
}
