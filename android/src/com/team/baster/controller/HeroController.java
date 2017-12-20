package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import static com.team.baster.GameConstants.HEAD_HEIGHT;
import static com.team.baster.GameConstants.HERO_HEIGHT;
import static com.team.baster.GameConstants.HERO_MAX_SIZE;
import static com.team.baster.GameConstants.HERO_SCALE_PER_TIME_HEIGHT;
import static com.team.baster.GameConstants.HERO_SCALE_PER_TIME_WIDTH;
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
    public Circle circleBody;
    public Circle circleHead;
    public TextureAtlas heroAtlas;
    public Animation<TextureAtlas.AtlasRegion> heroAnimation;
//    public Texture heroTexture;
    private int heroSize = 0;
    private long timeLeft;

    public int currentHeroWidth;
    public int currentHeroHeight;

    public HeroController() {
        hero       = new Rectangle();
        circleBody = new Circle();
        circleHead = new Circle();
        timeLeft   = TimeUtils.nanoTime();
        configHero();
    }

    public void controlHeroInput() {
        if (Gdx.input.isTouched()) {
            double x      = Gdx.input.getDeltaX() * SPEED_FACTOR;
            hero.x       += x;
            circleBody.x += x;
            circleHead.x += x;
        }
    }

    public void controlHeroPosition() {
        if (hero.x < 0){
            hero.x       = 0;
            circleBody.x = hero.width / 2;
            circleHead.x = hero.width / 2;
        }

        if (hero.x > WORLD_WIDTH - hero.width){
            hero.x       = WORLD_WIDTH - hero.width;
            circleBody.x = hero.x + hero.width / 2;
            circleHead.x = hero.x + hero.width / 2;
        }
    }

    private void configHero() {
        heroAtlas         = new TextureAtlas(Gdx.files.internal("hero/griffin.atlas"));
        heroAnimation     = new Animation<>(1 / 10f, heroAtlas.getRegions());
//        heroTexture       = new Texture("hero/griffin_origin.png");
        currentHeroWidth  = HERO_WIDTH;
        currentHeroHeight = HERO_HEIGHT;
        hero.x            = WORLD_WIDTH / 2 - currentHeroWidth / 2;
        hero.y            = WORLD_HEIGHT - (WORLD_HEIGHT / 5) - currentHeroHeight;
        hero.width        = currentHeroWidth;
        hero.height       = currentHeroHeight;
        circleHead.set(hero.x + HERO_WIDTH / 2, hero.y + HEAD_HEIGHT / 2 + 5,HEAD_HEIGHT / 2);
        circleBody.set(hero.x + HERO_WIDTH / 2, circleHead.y + circleHead.radius + HERO_WIDTH / 4 - 5,HERO_WIDTH / 2 - 5);
    }

    public void resizeHero() {
        long time = TimeUtils.nanoTime() - timeLeft;
        if (time / PERIOD_RESIZE >= 1) {
            eatFood();
        }
    }


    public void eatFood(){
        if (heroSize < HERO_MAX_SIZE) {
            currentHeroWidth   += HERO_SCALE_PER_TIME_WIDTH;
            currentHeroHeight  += HERO_SCALE_PER_TIME_HEIGHT;
            hero.width          = currentHeroWidth;
            hero.height         = currentHeroHeight;
            hero.x             -= HERO_SCALE_PER_TIME_WIDTH / 2;
            circleHead.set(hero.x + hero.width / 2, hero.y + circleHead.radius + 5, circleHead.radius + HERO_SCALE_PER_TIME_WIDTH / 2);
            circleBody.set(hero.x + hero.width / 2, circleHead.y + circleHead.radius + hero.width / 4 - 5, hero.width / 2 - 5);
            timeLeft = TimeUtils.nanoTime();
            heroSize++;
        }
    }
    public void diet() {
        int partResize = 3;
        if (heroSize < 3 && heroSize >= 0) {
            partResize = heroSize;
        }
        currentHeroWidth    -= HERO_SCALE_PER_TIME_WIDTH * partResize;
        currentHeroHeight   -= HERO_SCALE_PER_TIME_HEIGHT * partResize;
        hero.width           = currentHeroWidth;
        hero.height          = currentHeroHeight;
        hero.x              += HERO_SCALE_PER_TIME_WIDTH * (partResize * 2 >= 1 ? partResize * 2 : 1);
        int headRadius       = heroSize == 0 ? HEAD_HEIGHT / 2 : (int) (circleHead.radius - HERO_SCALE_PER_TIME_WIDTH * (partResize / 2 >= 1 ? partResize / 2 : 1));
        headRadius           = headRadius < HEAD_HEIGHT / 2 ? HEAD_HEIGHT / 2 : headRadius;
        circleHead.set(hero.x + currentHeroWidth / 2, hero.y + headRadius + 5, headRadius);
        circleBody.set(hero.x + currentHeroWidth / 2, circleHead.y + circleHead.radius + currentHeroWidth / 4 - 5, currentHeroWidth / 2 - 2);
        timeLeft  = TimeUtils.nanoTime();
        heroSize -= partResize;
    }

    public void dispose() {
        heroAtlas.dispose();
    }
}
