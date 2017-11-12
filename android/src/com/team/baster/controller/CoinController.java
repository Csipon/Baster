package com.team.baster.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.generator.CoinGenerator;

import static com.team.baster.GameConstants.MAX_COIN_GENER_TIME;
import static com.team.baster.GameConstants.MIN_COIN_GENER_TIME;

/**
 * Created by Pasha on 10/28/2017.
 */

public class CoinController {
    private int coinsCounter;
    public Array<Rectangle> coins;
    public CoinGenerator coinGenerator;
    public long lastDropCoinTime;
    public long periodCoinDrop;
    private HeroController heroController;

    public CoinController(HeroController heroController) {
        this.heroController = heroController;
        coins = new Array<>();
        coinGenerator = new CoinGenerator(coins);
    }

//
//    public boolean checkCoinCollisions(Rectangle item, Rectangle hero) {
//        if (item.overlaps(hero)) {
//            coinsCounter += 1;
//            return true;
//        }
//        return false;
//    }

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
