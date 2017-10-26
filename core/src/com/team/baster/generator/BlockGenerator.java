package com.team.baster.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.BasterGame;
import com.team.baster.generator.calculator.DropItemCalculator;
import com.team.baster.model.Square;
import com.team.baster.screen.menu.GameOverScreen;

import static com.team.baster.GameConstants.HORIZONTAL_SPEED;
import static com.team.baster.GameConstants.ITEM_VERT_WIDTH;
import static com.team.baster.GameConstants.ITEM_WIDTH;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/23/2017.
 */

public class BlockGenerator {
    private Array<Rectangle> blocks;
    private Array<Rectangle> square;
    private DropItemCalculator calculator;
    private Rectangle beforeLastDropItem;
    private Rectangle lastDropItem;
    private BasterGame game;

    public BlockGenerator(BasterGame game) {
        this.game = game;
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


    public void checkLasDropItemTime() {
        if (lastDropItem.y > WORLD_HEIGHT / 3) {
            dropItem();
        }
    }


    public void controlItemPosition(Rectangle hero, int speed, long score) {
        controlBlockPosition(hero, speed, score);
        controlSquarePosition(hero, speed, score);
    }

    private void controlBlockPosition(Rectangle hero, int speed, long score) {

        for (Rectangle item : blocks) {
            item.y += speed * Gdx.graphics.getDeltaTime();
            checkHeroCollision(item, hero, score);
        }
    }

    private void controlSquarePosition(Rectangle hero, int speed, long score) {
        for (Rectangle aSquare : square) {
            Square item = (Square) aSquare;
            item.y += speed * Gdx.graphics.getDeltaTime();
            item.checkCoordinate(WORLD_WIDTH);
            int horMove = (int) (HORIZONTAL_SPEED * Gdx.graphics.getDeltaTime());
            if (item.isRight()) {
                item.x += horMove;
            } else if (item.isLeft()) {
                item.x -= horMove;
            }
            checkHeroCollision(item, hero, score);
        }
    }

    private void checkHeroCollision(Rectangle item, Rectangle hero, long score) {
        if (item.overlaps(hero)) {
            game.setScreen(new GameOverScreen(game, score));
        }
    }

    public Rectangle getBeforeLastDropItem() {
        return beforeLastDropItem;
    }

    public Rectangle getLastDropItem() {
        return lastDropItem;
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
        Rectangle rectangle = calculator.generateSquareItem(blocks);
        square.add(rectangle);
        beforeLastDropItem = lastDropItem;
        lastDropItem = rectangle;
    }
}
