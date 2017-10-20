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

    public static final int DEFAULT_SPEED = 400;
    public static final long PERIOD_ACCELERATION = 500_000_000L;
    public static final float PART_ACCELERATION = 0.1f;

    public static final int ITEM_WIDTH = 160;
    public static final int ITEM_HEIGHT = 60;

    public static final int HERO_WIDTH = 120;
    public static final int HERO_HEIGHT = 143;
}
