package com.team.baster;

import com.badlogic.gdx.Gdx;

/**
 * Created by Pasha on 10/20/2017.
 */

public class GameConstants {
    private GameConstants constants = new GameConstants();
    public GameConstants() {
        int differenceWidth = WORLD_WIDTH / DEFAULT_WORLD_WIDTH;
        if (WORLD_WIDTH != DEFAULT_WORLD_WIDTH) {
            ITEM_WIDTH = ITEM_WIDTH * differenceWidth;
            ITEM_VERT_WIDTH = ITEM_VERT_WIDTH * differenceWidth;
            HERO_WIDTH = HERO_WIDTH * differenceWidth;
            ITEM_SQUARE_SIDE = ITEM_SQUARE_SIDE * differenceWidth;
            ITEM_VERT_HEIGHT = ITEM_VERT_HEIGHT * differenceWidth;
            ITEM_HEIGHT = ITEM_HEIGHT * differenceWidth;
            HERO_HEIGHT = HERO_HEIGHT * differenceWidth;
        }
    }


    private static final int DEFAULT_WORLD_WIDTH = 720;
    public static final int WORLD_WIDTH = Gdx.graphics.getWidth();
    public static final int WORLD_HEIGHT = Gdx.graphics.getHeight();

    public static final int DEFAULT_SPEED = 300;
    public static final int HORIZONTAL_SPEED = 200;
    public static final long PERIOD_ACCELERATION = 10_00_000_000L;
    public static final long PERIOD_RESIZE = 2_000_000_000L;
    public static final int HERO_SIZES = 16;
    public static final double MARGIN_LEFT_PERCENT = 0.18;
    public static final int HEAD_WIDTH = 73;
    public static final int HEAD_HEIGHT = 35;
    public static final float PART_ACCELERATION = 0.025f;

    public static int ITEM_WIDTH = 180;
    public static int ITEM_HEIGHT = 70;
    public static int ITEM_VERT_WIDTH = 70;
    public static int ITEM_VERT_HEIGHT = 180;
    public static int ITEM_SQUARE_SIDE = 100;


    public static int HERO_WIDTH = 100;
    public static int HERO_HEIGHT = 160;

    public static int COIN_SIDE = 50;


    public static final long MIN_COIN_GENER_TIME = 2_000_000_000L;
    public static final long MAX_COIN_GENER_TIME = 4_000_000_000L;

}
