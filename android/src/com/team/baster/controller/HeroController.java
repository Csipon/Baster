package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static com.team.baster.GameConstants.HERO_HEIGHT;
import static com.team.baster.GameConstants.HERO_SIZES;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.PERIOD_RESIZE;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/28/2017.
 */

public class HeroController {
    private static final double SPEED_FACTOR = 1;
    public Rectangle hero;
    private Texture heroTexture;
    private Array<Texture> heroSizes;
    private int heroSize = 0;
    private long timeLeft = TimeUtils.nanoTime();

    public HeroController() {
        hero = new Rectangle();
        heroSizes = new Array<>();
        initArray();
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

    private void initArray(){
        for (int i = 0; i < HERO_SIZES; i++){
            heroSizes.add(new Texture("hero/grif_" + i + ".png"));
        }
        heroTexture = heroSizes.first();
    }

    private void configHero() {
        hero.x = WORLD_WIDTH / 2 - HERO_WIDTH / 2;
        hero.y = WORLD_HEIGHT - (WORLD_HEIGHT / 5) - HERO_HEIGHT + 2;
        hero.width = HERO_WIDTH;
        hero.height = HERO_HEIGHT;
    }

    public Texture resizeHero(){
        long time = TimeUtils.nanoTime() - timeLeft;
        if (time / PERIOD_RESIZE > 1 && heroSize < heroSizes.size - 1){
            heroTexture =  heroSizes.get(++heroSize);
            hero.width = heroTexture.getWidth();
            hero.height = heroTexture.getHeight();
            timeLeft = TimeUtils.nanoTime();
        }
        return heroTexture;
    }

    public Texture diet(){
        if (heroSize > 0) {
            heroTexture = heroSizes.get(--heroSize);
            hero.width = heroTexture.getWidth();
            hero.height = heroTexture.getHeight();
        }
        return heroTexture;
    }

    public void dispose(){
        for (Texture t : heroSizes){
            t.dispose();
        }
    }
}
