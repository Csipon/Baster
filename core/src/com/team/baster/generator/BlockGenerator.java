package com.team.baster.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.BasterGame;
import com.team.baster.generator.calculator.DropItemCalculator;
import com.team.baster.screen.menu.GameOverScreen;

import java.util.Iterator;

import static com.team.baster.GameConstants.ITEM_HEIGHT;
import static com.team.baster.GameConstants.ITEM_WIDTH;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/23/2017.
 */

public class BlockGenerator {
    private Array<Rectangle> blocks;
    private DropItemCalculator calculator;
    private Rectangle lastDropItem;
    private BasterGame game;

    public BlockGenerator(BasterGame game) {
        this.game = game;
        blocks = new Array<>();
        calculator = new DropItemCalculator();
    }

    public Array<Rectangle> getBlocks(){
        return blocks;
    }

    public void dropItem() {
        if (MathUtils.random() < 0.25){
            Rectangle left = generateLeftOrRight(true);
            Rectangle right = generateLeftOrRight(false);
            blocks.add(left);
            blocks.add(right);
        }else {
            Rectangle item = calculator.generateItem(blocks);
            blocks.add(item);
            lastDropItem = item;
        }
    }


    public void checkLasDropItemTime() {
        if (lastDropItem.y > WORLD_HEIGHT / 3) {
            dropItem();
        }
    }


    public void controlItemPosition(Rectangle hero, int speed, long score) {
        Iterator<Rectangle> iter = blocks.iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += speed * Gdx.graphics.getDeltaTime();
            if (item.y + ITEM_HEIGHT > WORLD_HEIGHT + ITEM_HEIGHT) {
                iter.remove();
            }
            checkHeroCollision(item, hero, score);
        }
    }

    private void checkHeroCollision(Rectangle item, Rectangle hero, long score) {
        if (item.overlaps(hero)) {
            game.setScreen(new GameOverScreen(game, score));
        }
    }


    public Rectangle getLastDropItem() {
        return lastDropItem;
    }

    private Rectangle generateLeftOrRight(boolean isLeft){
        Rectangle item = calculator.generateItem(blocks);
        if (isLeft){
            item.x = 0;
        }else {
            item.x = WORLD_WIDTH - ITEM_WIDTH;
        }
        lastDropItem = item;
        return item;
    }
}
