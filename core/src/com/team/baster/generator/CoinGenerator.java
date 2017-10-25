package com.team.baster.generator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import static com.team.baster.GameConstants.COIN_SIDE;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.ITEM_VERT_WIDTH;
import static com.team.baster.GameConstants.ITEM_WIDTH;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/21/2017.
 */

public class CoinGenerator {
    private Array<Rectangle> coins;

    public CoinGenerator() {
        coins = new Array<>();
    }

    public void generateCoin(Rectangle lastDropItem, Rectangle beforeLastDropItem) {
        Rectangle coin = new Rectangle();
        if (beforeLastDropItem.width < beforeLastDropItem.height && lastDropItem.width > lastDropItem.height) {
            coin.x = generatePositionWithVertBlock(lastDropItem, beforeLastDropItem);
        } else if (beforeLastDropItem.width > beforeLastDropItem.height && lastDropItem.width > lastDropItem.height && lastDropItem.y == beforeLastDropItem.y) {
            coin.x = calculateRandPosition((int) beforeLastDropItem.x + ITEM_WIDTH, (int) lastDropItem.x - COIN_SIDE);
        }else {
            if (lastDropItem.y <= 0) {
                coin.x = getXPosition((int) lastDropItem.x);
            } else {
                coin.x = calculateRandPosition(0, WORLD_WIDTH - COIN_SIDE);
            }
        }
        coin.y = -COIN_SIDE;
        coin.width = COIN_SIDE;
        coin.height = COIN_SIDE;
        coins.add(coin);
    }


    private int getXPosition(int itemXPosition) {
        int coinFirstDiap = itemXPosition - HERO_WIDTH;
        if (coinFirstDiap < 0) {
            coinFirstDiap = 0;
        }

        int coinLastDiap = itemXPosition + ITEM_WIDTH + HERO_WIDTH;
        if (coinLastDiap < WORLD_WIDTH) {
            coinLastDiap -= HERO_WIDTH;
        } else {
            coinLastDiap = 0;
        }

        if (coinFirstDiap > 1 && coinLastDiap > 1) {
            return Math.random() > 0.5 ? calculateRandPosition(0, coinFirstDiap - COIN_SIDE) : calculateRandPosition(coinLastDiap, WORLD_WIDTH - COIN_SIDE);
        } else if (coinFirstDiap > 0) {
            return calculateRandPosition(0, coinFirstDiap - COIN_SIDE);
        } else {
            return calculateRandPosition(coinLastDiap, WORLD_WIDTH - COIN_SIDE);
        }

    }

    private int generatePositionWithVertBlock(Rectangle lastDropItem, Rectangle beforeLastDropItem) {
        int x1;
        int x2;
        if (lastDropItem.x > beforeLastDropItem.x) {
            x1 = (int) (beforeLastDropItem.x - COIN_SIDE);
            x2 = (int) (lastDropItem.x + ITEM_WIDTH);
        } else {
            x1 = (int) (lastDropItem.x - COIN_SIDE);
            x2 = (int) (beforeLastDropItem.x + ITEM_VERT_WIDTH);
        }
        if (MathUtils.random() > 0.5) {
            return calculateRandPosition(0, x1);
        } else {
            return calculateRandPosition(x2, WORLD_WIDTH - COIN_SIDE);
        }
    }


    private int calculateRandPosition(int start, int end) {
        if (start == end) {
            return 0;
        }
        return MathUtils.random(start, end);
    }

    public void checkLastCoinBlockCollision(Rectangle lastDropItem, Rectangle beforeLastDropItem){
        if (coins.size != 0) {
            if (coins.peek().overlaps(beforeLastDropItem) || coins.peek().overlaps(lastDropItem)) {
                coins.pop();
            }
        }
    }

    public Array<Rectangle> getCoins() {
        return coins;
    }
}
