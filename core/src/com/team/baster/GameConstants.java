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
    public static final long PERIOD_ACCELERATION = 10_00_000_000L;
    public static final float PART_ACCELERATION = 0.05f;

    public static final int ITEM_WIDTH = 160;
    public static final int ITEM_HEIGHT = 60;

    public static final int HERO_WIDTH = 112;
    public static final int HERO_HEIGHT = 138;

    public static final int COIN_SIDE = 50;


    public static final long MIN_COIN_GENER_TIME = 5_000_000_000L;
    public static final long MAX_COIN_GENER_TIME = 7_000_000_000L;

}
