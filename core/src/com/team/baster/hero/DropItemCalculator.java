package com.team.baster.hero;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;
import java.util.Map;

import static com.team.baster.GameConstants.ITEM_HEIGHT;
import static com.team.baster.GameConstants.ITEM_WIDTH;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/20/2017.
 */

public class DropItemCalculator {
    private static final String FIRST_DIAP = "0";
    private static final String LAST_DIAP = "1";


    public Rectangle generateItem(Rectangle prevItem) {
        Rectangle item = new Rectangle();
        if (prevItem != null) {
            int xStart = (int) prevItem.x;
            int xEnd = xStart + ITEM_WIDTH;
            item.x = chooseCoordinate(xStart, xEnd);
        } else {
            item.x = MathUtils.random(0, WORLD_WIDTH - ITEM_WIDTH);
        }
        item.y = -ITEM_HEIGHT;
        item.width = ITEM_WIDTH;
        item.height = ITEM_HEIGHT;

        return item;
    }


    private int chooseCoordinate(int start, int end) {
        Map<String, Integer> possDaip = calculatePossiblePositions(start, end);

        Integer first = possDaip.get(FIRST_DIAP);
        Integer last = possDaip.get(LAST_DIAP);
        if (first != null && last != null) {
            if (MathUtils.random() > 0.5) {
                return MathUtils.random(0, first + ITEM_WIDTH - 1);
            } else {
                return MathUtils.random(last, WORLD_WIDTH - ITEM_WIDTH);
            }
        } else if (first != null) {
            return MathUtils.random(0, first + ITEM_WIDTH - 1);
        } else {
            return MathUtils.random(last, WORLD_WIDTH - ITEM_WIDTH - 1);
        }

    }

    private Map<String, Integer> calculatePossiblePositions(int start, int end) {
        Map<String, Integer> result = new HashMap<>();
        int firstDiap = start - ITEM_WIDTH;
        if (firstDiap > 0) {
            result.put(FIRST_DIAP, firstDiap);
        }
        int lastDiap = end + ITEM_WIDTH;
        if (lastDiap < WORLD_WIDTH) {
            result.put(LAST_DIAP, end);
        }
        return result;
    }
}
