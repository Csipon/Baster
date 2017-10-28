package com.team.baster.generator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.generator.calculator.DropItemCalculator;

import static com.team.baster.GameConstants.*;

/**
 * Created by Pasha on 10/23/2017.
 */

public class BlockGenerator {
    public Array<Rectangle> blocks;
    public Array<Rectangle> square;
    public DropItemCalculator calculator;
    public Rectangle beforeLastDropItem;
    public Rectangle lastDropItem;

    public BlockGenerator() {
        blocks = new Array<>();
        square = new Array<>();
        calculator = new DropItemCalculator();
    }

    public Array<Rectangle> getBlocks() {
        return blocks;
    }

    public Array<Rectangle> getSquare() {
        return square;
    }

    public void dropItem() {
        double rand = MathUtils.random();
        if (rand < 0.25) {
            Rectangle left = generateLeftOrRight(true);
            Rectangle right = generateLeftOrRight(false);
            blocks.add(left);
            blocks.add(right);
        } else if (rand > 0.9) {
            generateVerticalBlocks();
        } else if (rand > 0.75 && rand < 0.9) {
            generateDynamicBlock();
        } else {
            Rectangle item = calculator.generateItem(blocks);
            blocks.add(item);
            beforeLastDropItem = lastDropItem;
            lastDropItem = item;
        }
    }

    private Rectangle generateLeftOrRight(boolean isLeft) {
        Rectangle item = calculator.generateItem(blocks);
        if (isLeft) {
            item.x = 0;
        } else {
            item.x = WORLD_WIDTH - ITEM_WIDTH;
        }
        beforeLastDropItem = lastDropItem;
        lastDropItem = item;
        return item;
    }

    private void generateVerticalBlocks() {
        Rectangle rectangle;
        for (int i = 0; i < 4; i++) {
            rectangle = calculator.generateVertItem(blocks, 0);
            blocks.add(rectangle);
            beforeLastDropItem = lastDropItem;
            lastDropItem = rectangle;
        }
        rectangle = calculator.generateItem(blocks);
        if (MathUtils.random() > 0.5) {
            rectangle.x = lastDropItem.x - ITEM_WIDTH;
            rectangle.y = lastDropItem.y;
        } else {
            rectangle.x = lastDropItem.x + ITEM_VERT_WIDTH;
            rectangle.y = lastDropItem.y;
        }
        blocks.add(rectangle);
        beforeLastDropItem = lastDropItem;
        lastDropItem = rectangle;
    }

    private void generateDynamicBlock() {
        Rectangle rectangle = calculator.generateSquareItem(square);
        square.add(rectangle);
        beforeLastDropItem = lastDropItem;
        lastDropItem = rectangle;
    }
}
