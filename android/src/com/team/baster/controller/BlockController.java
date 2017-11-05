package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.asynch.MyAsyncTask;
import com.team.baster.domain.BasterGame;
import com.team.baster.generator.Generator;
import com.team.baster.generator.UnitGeneration;
import com.team.baster.model.Burger;
import com.team.baster.model.Pill;
import com.team.baster.model.Square;
import com.team.baster.screens.GameOverScreen;
import com.team.baster.storage.PlayerStatusStorage;
import com.team.baster.storage.ScoreStorage;

import java.util.Iterator;

import static com.team.baster.GameConstants.HORIZONTAL_SPEED;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;
import static com.team.baster.controller.HeroController.intersect;

/**
 * Created by Pasha on 10/28/2017.
 */

public class BlockController {
    private ScoreStorage scoreStorage;
    private PlayerStatusStorage playerStatusStorage;
    //    public BlockGenerator blockGenerator;
    private Generator generator;
    public Array<UnitGeneration> units;
    BasterGame game;
    public HeroController heroController;

    public BlockController(BasterGame game, HeroController heroController) {
        this.game = game;
        this.heroController = heroController;
        generator = new Generator();
        units = new Array<>();
//        blockGenerator = new BlockGenerator();
        scoreStorage = new ScoreStorage();
        playerStatusStorage = PlayerStatusStorage.getInstance();
    }

    public void controlItemsPosition(Circle head, Circle body, int speed, int score, int coins) {
        for (UnitGeneration unit : units) {
            for (Rectangle rect : unit.blocks) {
                rect.y += speed * Gdx.graphics.getDeltaTime();
                if (rect instanceof Square) {
                    controlSquarePosition((Square) rect);
                }
                checkHeroCollision(rect, head, body, score, coins);
            }
            Iterator<Rectangle> iterator = unit.actionItems.iterator();
            while (iterator.hasNext()) {
                Rectangle rect = iterator.next();
                rect.y += speed * Gdx.graphics.getDeltaTime();
                if (checkActionItemCollision(rect, head, body)) {
                    iterator.remove();
                }
            }
            unit.minPointY += speed * Gdx.graphics.getDeltaTime();
        }
        Iterator<UnitGeneration> iter = units.iterator();
        while (iter.hasNext()){
            UnitGeneration unit = iter.next();
            if (unit.minPointY > WORLD_HEIGHT){
                iter.remove();
            }
        }
    }

    private void controlSquarePosition(Square item) {
        item.checkCoordinate(WORLD_WIDTH);
        int horMove = (int) (HORIZONTAL_SPEED * Gdx.graphics.getDeltaTime());
        if (item.isRight()) {
            item.x += horMove;
        } else if (item.isLeft()) {
            item.x -= horMove;
        }
    }

    private void checkHeroCollision(Rectangle item, Circle head, Circle body, int score, int coins) {
        if (intersect(item, head) || intersect(item, body)) {
            save(score, coins);
            game.setScreen(new GameOverScreen(game, score, coins));
        }
    }

    private boolean checkActionItemCollision(Rectangle item, Circle head, Circle body) {
        if (intersect(item, head) || intersect(item, body)) {
            if (item instanceof Pill) {
                heroController.diet();
            } else if (item instanceof Burger) {
                heroController.eatFood();
            }
            return true;
        }
        return false;
    }

    public void checkLasDropItemTime() {
        if (units.peek().minPointY > WORLD_HEIGHT / 4) {
            dropItem();
        }
    }


    private void save(int score, int coins) {
        System.out.println("------- 0 " + TimeUtils.millis());

        new MyAsyncTask(scoreStorage, playerStatusStorage, score, coins).execute();
    }

    public void dropItem() {
        if (Math.random() > 0.5){
            UnitGeneration copy = generator.units.first().getCopy();
            if (Math.random() > 0.5) {
                ((Square)copy.blocks.first()).moveLeft();
            }else{
                ((Square)copy.blocks.first()).moveRight();
            }
            units.add(copy);
        }else {
            units.add(generator.units.peek().getCopy());
        }
    }

}
