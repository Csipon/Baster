package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static com.team.baster.GameConstants.HEAD_HEIGHT;
import static com.team.baster.GameConstants.HEAD_WIDTH;
import static com.team.baster.GameConstants.HERO_HEIGHT;
import static com.team.baster.GameConstants.HERO_SIZES;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.MARGIN_LEFT_PERCENT;
import static com.team.baster.GameConstants.PERIOD_RESIZE;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/28/2017.
 */

public class HeroController {
    private static final double SPEED_FACTOR = 1;
    public Rectangle hero;
    public Texture heroTexture;
    private Array<Texture> heroSizes;
    private int heroSize = 0;
    private long timeLeft;

    public Rectangle heroHead;
    public Rectangle heroBody;



    public HeroController() {
        hero = new Rectangle();
        heroHead = new Rectangle();
        heroBody = new Rectangle();
        heroSizes = new Array<>();
        timeLeft = TimeUtils.nanoTime();
        initArray();
        configHero();
    }

    public void controlHeroInput() {
        if (Gdx.input.isTouched()) {
            double x = Gdx.input.getDeltaX() * SPEED_FACTOR;
            hero.x += x;
            heroBody.x += x;
            heroHead.x += x;
        }
    }

    public void controlHeroPosition() {
        if (hero.x < 0) hero.x = 0;
        if (hero.x > WORLD_WIDTH - heroTexture.getWidth())
            hero.x = WORLD_WIDTH - heroTexture.getWidth();
    }

    private void initArray() {
        for (int i = 0; i < HERO_SIZES; i++) {
            heroSizes.add(new Texture("hero/grif_" + i + ".png"));
        }
        heroTexture = heroSizes.first();
    }

    private void configHero() {
        hero.x = WORLD_WIDTH / 2 - HERO_WIDTH / 2;
        hero.y = WORLD_HEIGHT - (WORLD_HEIGHT / 5) - HERO_HEIGHT;
        hero.width = HERO_WIDTH;
        hero.height = HERO_HEIGHT;
        heroHead.x = hero.x + (float) (HERO_WIDTH * MARGIN_LEFT_PERCENT);
        System.out.println("Hero Head = " + heroHead.x);
        heroHead.y = hero.y + 5;
        heroHead.width = HEAD_WIDTH;
        heroHead.height = HEAD_HEIGHT;
        heroBody.x = hero.x;
        heroBody.y = hero.y + heroHead.height;
        heroBody.width = hero.width;
        heroBody.height = hero.height - heroHead.height;
    }

    public Texture resizeHero() {
        long time = TimeUtils.nanoTime() - timeLeft;
        if (time / PERIOD_RESIZE >= 1 && heroSize < heroSizes.size - 1) {
            heroTexture = heroSizes.get(++heroSize);
            System.out.println("OLD SIZE = " + heroSize + ", NEW SIZE = " + (heroSize + 1));
            hero.width = heroTexture.getWidth();
            hero.height = heroTexture.getHeight();
            heroBody.width = heroTexture.getWidth();
            hero.x += 2;
            heroBody.x = hero.x;
            heroHead.x = hero.x + (float) (HERO_WIDTH * MARGIN_LEFT_PERCENT);
            heroHead.y = hero.y + 5;
            heroHead.width += 2;
            heroHead.height += 2;
            heroBody.height = hero.height - heroHead.height;
            heroBody.y = hero.y + heroHead.height;
            timeLeft = TimeUtils.nanoTime();
        }
        return heroTexture;
    }

    public Texture diet() {
        if (heroSize > 0) {
            heroTexture = heroSizes.get(--heroSize);
            hero.width = heroTexture.getWidth();
            hero.height = heroTexture.getHeight();
            hero.x -= 2;
            heroHead.x = hero.x + (float) (HERO_WIDTH * MARGIN_LEFT_PERCENT);
            heroHead.width -= 2;
            heroHead.height -= 2;
            heroBody.width = hero.width;
            heroBody.height = hero.height - heroHead.height;
            heroBody.x = hero.x;
            heroBody.y = hero.y + heroHead.height;
        }
        return heroTexture;
    }

    public void dispose() {
        for (Texture t : heroSizes) {
            t.dispose();
        }
    }
}
