package com.team.baster;

import com.badlogic.gdx.Gdx;

/**
 * Created by Pasha on 10/20/2017.
 */

public class GameConstants {

    private static final int DEFAULT_WORLD_WIDTH    = 720;
    public static final String DEFAULT_PLAYER_NAME  = "player";
    public static final int WORLD_WIDTH             = Gdx.graphics.getWidth();
    public static final int WORLD_HEIGHT            = Gdx.graphics.getHeight();

    public static final int DEFAULT_SPEED               = 300;
    public static final int HORIZONTAL_SPEED            = 200;
    public static final long PERIOD_ACCELERATION        = 10_00_000_000L;
    public static final long PERIOD_RESIZE              = 1_000_000_000L;
    public static final int HERO_MAX_SIZE               = 25;
    public static final int HERO_SCALE_PER_TIME_WIDTH   = 2;
    public static final int HERO_SCALE_PER_TIME_HEIGHT  = 4;
    public static final float PART_ACCELERATION         = 0.025f;


    public static int ITEM_WIDTH                = 180;
    public static int ITEM_HEIGHT               = 70;
    public static int ITEM_TOP_VERT_WIDTH       = 100;
    public static int ITEM_TOP_VERT_HEIGHT      = 40;
    public static int ITEM_VERT_WIDTH           = 70;
    public static int ITEM_VERT_HEIGHT          = 120;
    public static int PARATROOPER_BODY_WIDTH    = 130;
    public static int PARATROOPER_BODY_HEIGHT   = 230;
    public static int PARATROOPER_WIDTH         = 50;
    public static int PARATROOPER_HEIGHT        = 82;
    public static int PARATROOP_RADIUS          = PARATROOPER_WIDTH / 2;
    public static int ITEM_AIRPLANE_WIDTH       = 200;
    public static int ITEM_AIRPLANE_HEIGHT      = 80;
    public static int HEAD_HEIGHT               = 35;
    public static int HERO_WIDTH                = 100;
    public static int HERO_HEIGHT               = 160;
    public static int ACTION_ITEM_SIDE          = 50;
    public static int DISTANCE_FIVE_BLOCK       = 300;
    public static int BUFFER_Y                  = -100;
    public static final long MIN_GENER_TIME     = 10_000_000_000L;
    public static final long MAX_GENER_TIME     = 15_000_000_000L;



    public GameConstants() {
        double differenceWidth = WORLD_WIDTH / DEFAULT_WORLD_WIDTH;
        if (WORLD_WIDTH != DEFAULT_WORLD_WIDTH) {
            ITEM_WIDTH              *= differenceWidth;
            ITEM_HEIGHT             *= differenceWidth;
            ITEM_TOP_VERT_WIDTH     *= differenceWidth;
            ITEM_VERT_WIDTH         *= differenceWidth;
            ITEM_TOP_VERT_HEIGHT    *= differenceWidth;
            ITEM_VERT_HEIGHT        *= differenceWidth;
            PARATROOPER_BODY_WIDTH *= differenceWidth;
            PARATROOPER_WIDTH       *= differenceWidth;
            PARATROOPER_BODY_HEIGHT *= differenceWidth;
            PARATROOPER_HEIGHT      *= differenceWidth;
            PARATROOP_RADIUS         = PARATROOPER_WIDTH / 2;
            ITEM_AIRPLANE_WIDTH     *= differenceWidth;
            ITEM_AIRPLANE_HEIGHT    *= differenceWidth;
            HEAD_HEIGHT             *= differenceWidth;
            HERO_WIDTH              *= differenceWidth;
            HERO_HEIGHT             *= differenceWidth;
            ACTION_ITEM_SIDE        *= differenceWidth;
            DISTANCE_FIVE_BLOCK     *= differenceWidth;
            BUFFER_Y                *= differenceWidth;
        }
    }





}
