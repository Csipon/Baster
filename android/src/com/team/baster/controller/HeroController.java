package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.HERO_HEIGHT;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/28/2017.
 */

public class HeroController {
    private static final double SPEED_FACTOR = 1;
    public Rectangle hero;

    public HeroController() {
        hero = new Rectangle();
        configHero();
    }

    public void controlHeroInput() {
        if (Gdx.input.isTouched()) {
            hero.x += Gdx.input.getDeltaX() * SPEED_FACTOR;
        }
    }

    public void controlHeroPosition() {
        if (hero.x < 0) hero.x = 0;
        if (hero.x > WORLD_WIDTH - HERO_WIDTH) hero.x = WORLD_WIDTH - HERO_WIDTH;
    }

    private void configHero() {
        hero.x = WORLD_WIDTH / 2 - HERO_WIDTH / 2;
        hero.y = WORLD_HEIGHT - (WORLD_HEIGHT / 5) - HERO_HEIGHT;
        hero.width = HERO_WIDTH;
        hero.height = HERO_HEIGHT;
    }
}
