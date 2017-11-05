package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static com.team.baster.GameConstants.*;

/**
 * Created by Pasha on 10/28/2017.
 */

public class HeroController {

    private static final double SPEED_FACTOR = 1;
    public Rectangle hero;
    public Circle circleBody;
    public Circle circleHead;
    public Texture heroTexture;
    private Array<Texture> heroSizes;
    private int heroSize = 0;
    private long timeLeft;

    public HeroController() {
        hero = new Rectangle();
        circleBody = new Circle();
        circleHead = new Circle();
        heroSizes = new Array<>();
        timeLeft = TimeUtils.nanoTime();
        initArray();
        configHero();
    }

    public void controlHeroInput() {
        if (Gdx.input.isTouched()) {
            double x = Gdx.input.getDeltaX() * SPEED_FACTOR;
            hero.x += x;
            circleBody.x += x;
            circleHead.x += x;
        }
    }

    public void controlHeroPosition() {
        if (hero.x < 0){
            hero.x = 0;
            circleBody.x = hero.width / 2;
            circleHead.x = hero.width / 2;
        }

        if (hero.x > WORLD_WIDTH - heroTexture.getWidth()){
            hero.x = WORLD_WIDTH - heroTexture.getWidth();
            circleBody.x = hero.x + hero.width / 2;
            circleHead.x = hero.x + hero.width / 2;
        }
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
        circleHead.set(hero.x + HERO_WIDTH / 2, hero.y + HEAD_HEIGHT / 2 + 5,HEAD_HEIGHT / 2);
        circleBody.set(hero.x + HERO_WIDTH / 2, circleHead.y + circleHead.radius + HERO_WIDTH / 4 - 5,HERO_WIDTH / 2 - 5);
    }

    public Texture resizeHero() {
        long time = TimeUtils.nanoTime() - timeLeft;
        if (time / PERIOD_RESIZE >= 1 && heroSize < heroSizes.size - 1) {
            return eatFood();
        }
        return heroTexture;
    }


    public Texture eatFood(){
        if (heroSize < heroSizes.size - 1) {
            heroTexture = heroSizes.get(++heroSize);
            hero.width = heroTexture.getWidth();
            hero.height = heroTexture.getHeight();
            hero.x -= 2;
            circleHead.set(hero.x + hero.width / 2, hero.y + circleHead.radius + 5, circleHead.radius + 2);
            circleBody.set(hero.x + hero.width / 2, circleHead.y + circleHead.radius + hero.width / 4 - 5, hero.width / 2 - 5);
            timeLeft = TimeUtils.nanoTime();
        }
        return heroTexture;
    }
    public Texture diet() {
        if (heroSize > 0) {
            heroTexture = heroSizes.get(--heroSize);
            hero.width = heroTexture.getWidth();
            hero.height = heroTexture.getHeight();
            hero.x += 2;
            circleHead.set(hero.x + hero.width / 2, hero.y + circleHead.radius + 5,circleHead.radius - 2);
            circleBody.set(hero.x + hero.width / 2, circleHead.y + circleHead.radius + hero.width / 4 - 5, hero.width / 2 - 5);
            timeLeft = TimeUtils.nanoTime();
        }
        return heroTexture;
    }

    public void dispose() {
        for (Texture t : heroSizes) {
            t.dispose();
        }
    }


    public static boolean intersect(Rectangle rect, Circle circle) {
        float halfHeight = rect.height / 2;
        float halfWidth = rect.width / 2;
        float cx = Math.abs(circle.x - rect.x - halfWidth);
        float xDist = halfWidth + circle.radius;
        if (cx > xDist)
            return false;
        float cy = Math.abs(circle.y - rect.y - halfHeight);
        float yDist = halfHeight + circle.radius;
        if (cy > yDist)
            return false;
        if (cx <= halfWidth || cy <= halfHeight)
            return true;
        float xCornerDist = cx - halfWidth;
        float yCornerDist = cy - halfHeight;
        float xCornerDistSq = xCornerDist * xCornerDist;
        float yCornerDistSq = yCornerDist * yCornerDist;
        float maxCornerDistSq = circle.radius * circle.radius;
        return xCornerDistSq + yCornerDistSq <= maxCornerDistSq;
    }
}
