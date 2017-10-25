package com.team.baster.generator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.*;

/**
 * Created by Pasha on 10/21/2017.
 */

public class CoinGenerator {

    public Rectangle generateCoin(Rectangle lastDropItem){
        Rectangle coin = new Rectangle();
        if (lastDropItem.y + ITEM_HEIGHT <= -COIN_SIDE){
            coin.x = getXPosition((int) lastDropItem.x);
        }else {
            coin.x = calculateRandPosition(0, WORLD_WIDTH - COIN_SIDE);
        }
        coin.y = -COIN_SIDE;
        coin.width = COIN_SIDE;
        coin.height = COIN_SIDE;

        return coin;
    }



    private int getXPosition(int itemXPosition){
        int coinFirstDiap = itemXPosition - HERO_WIDTH;
        if (coinFirstDiap < 0 ){
            coinFirstDiap = 0;
        }

        int coinLastDiap = itemXPosition + ITEM_WIDTH + HERO_WIDTH;
        if (coinLastDiap < WORLD_WIDTH){
            coinLastDiap -= HERO_WIDTH ;
        }else {
            coinLastDiap = 0;
        }

        if (coinFirstDiap > 1 && coinLastDiap > 1) {
            return Math.random() > 0.5 ? calculateRandPosition(0, coinFirstDiap - COIN_SIDE) : calculateRandPosition(coinLastDiap, WORLD_WIDTH - COIN_SIDE);
        }else if (coinFirstDiap > 0){
            return calculateRandPosition(0, coinFirstDiap - COIN_SIDE);
        }else {
            return calculateRandPosition(coinLastDiap, WORLD_WIDTH - COIN_SIDE);
        }

    }


    private int calculateRandPosition(int start, int end){
        if (start == end){
            return 0;
        }
        return MathUtils.random(start, end);
    }
}
