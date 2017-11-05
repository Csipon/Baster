CREATE TABLE "score" (
 "id" integer PRIMARY KEY AUTOINCREMENT,
 "score" integer NOT NULL,
 "date" text NOT NULL
);

CREATE TABLE "actual_player_status" (
 "id" integer PRIMARY KEY AUTOINCREMENT,
 "player" text NOT NULL,
 "overall_score" integer NOT NULL,
 "actual_coins" integer NOT NULL,
 "overall_experience" integer NOT NULL
);

INSERT INTO actual_player_status(player, overall_score, actual_coins, overall_experience) VALUES('player', 0, 0, 0);

CREATE TABLE "product" (
   "id" integer PRIMARY KEY AUTOINCREMENT,
   "title" text NOT NULL,
   "donat_price" integer NOT NULL,
   "coin_price" integer NOT NULL,
   "max_count" integer NOT NULL,
   "product_type_id_fk" integer NOT NULL,
                FOREIGN KEY (product_type_id_fk) REFERENCES product_type(id)
  );

CREATE TABLE "orders" (
 "id" integer PRIMARY KEY AUTOINCREMENT,
 "type_currency" text NOT NULL,
 "actual_count" text NOT NULL,
 "date_bought" text NOT NULL,
 "last_date_bought" text NOT NULL,
    "product_id_fk" integer NOT NULL,
             FOREIGN KEY (product_id_fk) REFERENCES product(id)
);

CREATE TABLE "achievements" (
 "id" integer PRIMARY KEY AUTOINCREMENT,
 "title" text NOT NULL,
 "is_completed" text NOT NULL,
 "coin_reward" integer NOT NULL,
 "experience " integer NOT NULL,
 "date_completed" text NOT NULL
);



CREATE TABLE "product_type" (
 "id" integer PRIMARY KEY AUTOINCREMENT,
 "title" text NOT NULL
);

DROP IF EXIST TABLE actual_player_status;
DROP IF EXIST TABLE product_type;
DROP IF EXIST TABLE achievements;
DROP IF EXIST TABLE orders;
DROP IF EXIST TABLE score;
DROP IF EXIST TABLE product;