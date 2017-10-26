package com.team.baster.generator.calculator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.model.Square;

import java.util.HashMap;
import java.util.Map;

import static com.team.baster.GameConstants.ITEM_HEIGHT;
import static com.team.baster.GameConstants.ITEM_SQUARE_SIDE;
import static com.team.baster.GameConstants.ITEM_VERT_HEIGHT;
import static com.team.baster.GameConstants.ITEM_VERT_WIDTH;
import static com.team.baster.GameConstants.ITEM_WIDTH;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/20/2017.
 */

public class DropItemCalculator {
    private static final String FIRST_DIAP = "0";
    private static final String LAST_DIAP = "1";


    public Rectangle generateItem(Array<Rectangle> blocks) {
        Rectangle block;
        if (blocks.size == 15) {
            block = blocks.removeIndex(0);
        } else {
            block = new Rectangle();
        }
        if (blocks.size > 0) {
            Rectangle prevItem = blocks.peek();
            int xStart = (int) prevItem.x;
            int xEnd = (int) (xStart + prevItem.width);
            block.x = chooseCoordinate(xStart, xEnd, ITEM_WIDTH);
        } else {
            block.x = MathUtils.random(0, WORLD_WIDTH - ITEM_WIDTH);
        }
        block.y = -ITEM_HEIGHT;
        block.width = ITEM_WIDTH;
        block.height = ITEM_HEIGHT;

        return block;
    }

    public Rectangle generateSquareItem(Array<Rectangle> squares) {
        Square square;
        if (squares.size == 5) {
            square = (Square) squares.removeIndex(0);
        } else {
            square = new Square();
        }
        if (squares.size > 0) {
            Rectangle prevItem = squares.peek();
            int xStart = (int) prevItem.x;
            int xEnd = (int) (xStart + prevItem.width);
            square.x = chooseCoordinate(xStart, xEnd, ITEM_SQUARE_SIDE);
        } else {
            square.x = MathUtils.random(0, WORLD_WIDTH - ITEM_SQUARE_SIDE);
        }
        square.y = -ITEM_SQUARE_SIDE;
        square.moveLeft();
        return square;
    }

    public Rectangle generateVertItem(Array<Rectangle> blocks, int distance) {
        Rectangle block;
        if (blocks.size == 15) {
            block = blocks.removeIndex(0);
        } else {
            block = new Rectangle();
        }
        if (blocks.size > 0) {
            Rectangle prevBlock = blocks.peek();
            if (prevBlock.y < 0) {
                block.y = prevBlock.y - ITEM_VERT_HEIGHT - distance;
            } else {
                block.y = -ITEM_VERT_HEIGHT;
            }
        } else {
            block.y = -ITEM_VERT_HEIGHT;
        }
        block.x = WORLD_WIDTH / 2 - ITEM_VERT_WIDTH / 2;
        block.width = ITEM_VERT_WIDTH;
        block.height = ITEM_VERT_HEIGHT;

        return block;
    }

    private int chooseCoordinate(int start, int end, int itemWidth) {
        Map<String, Integer> possDaip = calculatePossiblePositions(start, end, itemWidth);

        Integer first = possDaip.get(FIRST_DIAP);
        Integer last = possDaip.get(LAST_DIAP);
        if (first != null && last != null) {
            if (MathUtils.random() > 0.5) {
                return MathUtils.random(0, first);
            } else {
                return MathUtils.random(last, WORLD_WIDTH - itemWidth - 1);
            }
        } else if (first != null) {
            return MathUtils.random(0, first);
        } else {
            return MathUtils.random(last, WORLD_WIDTH - itemWidth - 1);
        }

    }

    private Map<String, Integer> calculatePossiblePositions(int start, int end, int itemWidth) {
        Map<String, Integer> result = new HashMap<>();
        int firstDiap = start - itemWidth;
        if (firstDiap > 0) {
            result.put(FIRST_DIAP, firstDiap);
        }
        int lastDiap = end + itemWidth;
        if (lastDiap < WORLD_WIDTH) {
            result.put(LAST_DIAP, end);
        }
        return result;
    }
}
