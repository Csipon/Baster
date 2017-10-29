package com.team.baster.storage.core;

/**
 * Created by Pasha on 10/28/2017.
 */

public class SqlConstant {

    public static final  String DROP_TABLES =
            "DROP IF EXIST TABLE actual_player_status;\n" +
            "DROP IF EXIST TABLE product_type;\n" +
            "DROP IF EXIST TABLE achievements;\n" +
            "DROP IF EXIST TABLE orders;\n" +
            "DROP IF EXIST TABLE score;\n" +
            "DROP IF EXIST TABLE product;";

    public static final String CREATE_DB_SCRIPT = "CREATE TABLE \"score\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"score\" integer NOT NULL,\n" +
            " \"date\" text NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE \"product\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text NOT NULL,\n" +
            " \"donat_price\" integer NOT NULL,\n" +
            " \"coin_price\" integer NOT NULL,\n" +
            " \"max_count\" integer NOT NULL,\n" +
            " \"product_type_id_fk\" integer NOT NULL,\n" +
            "              FOREIGN KEY (product_type_id_fk) REFERENCES product_type(id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE \"order\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"type_currency\" text NOT NULL,\n" +
            " \"actual_count\" text NOT NULL,\n" +
            " \"date_bought\" text NOT NULL,\n" +
            " \"last_date_bought\" text NOT NULL,\n" +
            "    \"product_id_fk\" integer NOT NULL,\n" +
            "             FOREIGN KEY (product_id_fk) REFERENCES product(id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE \"achievements\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text NOT NULL,\n" +
            " \"is_completed\" text NOT NULL,\n" +
            " \"coin_reward\" integer NOT NULL,\n" +
            " \"experience \" integer NOT NULL,\n" +
            " \"date_completed\" text NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE \"actual_player_status\" (\n" +
            " \"overall_score\" integer NOT NULL,\n" +
            " \"actual_coins\" integer NOT NULL,\n" +
            " \"overall_experience\" integer NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE \"product_type\" (\n" +
            " \"id\" integer PRIMARY KEY AUTOINCREMENT,\n" +
            " \"title\" text NOT NULL\n" +
            ");";
}