package com.team.baster;

import com.badlogic.gdx.Gdx;

/**
 * Created by Pasha on 10/20/2017.
 */

public final class GameConstants {

    private GameConstants() {
    }


    public static final int WORLD_WIDTH = Gdx.graphics.getWidth();
    public static final int WORLD_HEIGHT = Gdx.graphics.getHeight();

    public static final int DEFAULT_SPEED = 300;
    public static final int HORIZONTAL_SPEED = 200;
    public static final long PERIOD_ACCELERATION = 10_00_000_000L;
    public static final float PART_ACCELERATION = 0.035f;

    public static final int ITEM_WIDTH = 180;
    public static final int ITEM_HEIGHT = 70;
    public static final int ITEM_VERT_WIDTH = 70;
    public static final int ITEM_VERT_HEIGHT = 180;
    public static final int ITEM_SQUARE_SIDE = 100;

    public static final int HERO_WIDTH = 100;
    public static final int HERO_HEIGHT = 160;

    public static final int COIN_SIDE = 50;


    public static final long MIN_COIN_GENER_TIME = 5_000_000_000L;
    public static final long MAX_COIN_GENER_TIME = 7_000_000_000L;

}
