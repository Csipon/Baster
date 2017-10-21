package com.team.baster.hero;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import static com.team.baster.GameConstants.COIN_SIDE;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.ITEM_HEIGHT;
import static com.team.baster.GameConstants.ITEM_WIDTH;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/21/2017.
 */

public class CoinGenerator {


    public Rectangle generateCoin(Rectangle lastDropItem){
        Rectangle coin = new Rectangle();
        if (lastDropItem.y + ITEM_HEIGHT <= ITEM_HEIGHT + COIN_SIDE){
            coin.x = getXPosition((int) lastDropItem.x);
        }

        coin.x = calculateRandPosition(0 , WORLD_WIDTH - COIN_SIDE);
        coin.y = - COIN_SIDE;

        return coin;
    }



    private int getXPosition(int itemXPosition){
        int coinFirstDiap = 0;
        if (itemXPosition > HERO_WIDTH){
            coinFirstDiap = itemXPosition;
        }

        int coinLastDiap = 0;
        if (itemXPosition + ITEM_WIDTH < WORLD_WIDTH - HERO_WIDTH){
            coinLastDiap = itemXPosition + ITEM_WIDTH;
        }

        if (coinFirstDiap > 0 && coinLastDiap > 0) {
            return Math.random() > 0.5 ? calculateRandPosition(0, coinFirstDiap - COIN_SIDE) : calculateRandPosition(coinLastDiap, WORLD_WIDTH - COIN_SIDE);
        }else if (coinFirstDiap > 0){
            return calculateRandPosition(0, coinFirstDiap - COIN_SIDE);
        }else {
            return calculateRandPosition(coinLastDiap, WORLD_WIDTH - COIN_SIDE);
        }

    }


    private int calculateRandPosition(int start, int end){
        return MathUtils.random(start, end);
    }
}
