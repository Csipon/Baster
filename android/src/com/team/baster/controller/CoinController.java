package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.generator.CoinGenerator;

import java.util.Iterator;

import static com.team.baster.GameConstants.COIN_SIDE;
import static com.team.baster.GameConstants.MAX_COIN_GENER_TIME;
import static com.team.baster.GameConstants.MIN_COIN_GENER_TIME;
import static com.team.baster.GameConstants.WORLD_HEIGHT;

/**
 * Created by Pasha on 10/28/2017.
 */

public class CoinController {
    private int coinsCounter;
    public Array<Rectangle> coins;
    public CoinGenerator coinGenerator;
    public long lastDropCoinTime;
    public long periodCoinDrop;

    public CoinController() {
        coins = new Array<>();
        coinGenerator = new CoinGenerator(coins);
    }

    public void controlCoins(int speed, Rectangle hero) {
        Iterator<Rectangle> iter = coins.iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += speed * Gdx.graphics.getDeltaTime();
            if (checkCoinCollisions(item, hero)) {
                iter.remove();
            }
            if (item.y + COIN_SIDE > WORLD_HEIGHT + COIN_SIDE) {
                iter.remove();
            }
        }
    }

    public boolean checkCoinCollisions(Rectangle item, Rectangle hero) {
        if (item.overlaps(hero)) {
            coinsCounter += 1;
            return true;
        }
        return false;
    }

    public void checkCoinGeneration(Rectangle lastDropItem, Rectangle beforeLastDropItem) {
        if (periodCoinDrop < TimeUtils.nanoTime() - lastDropCoinTime) {
            coinGenerator.generateCoin(lastDropItem, beforeLastDropItem);
            generatePeriod();
            lastDropCoinTime = TimeUtils.nanoTime();
        }
        coinGenerator.checkLastCoinBlockCollision(lastDropItem, beforeLastDropItem);
    }

    public void generatePeriod() {
        periodCoinDrop = MathUtils.random(MIN_COIN_GENER_TIME, MAX_COIN_GENER_TIME);
    }

    public int getCoinsCounter() {
        return coinsCounter;
    }
}
