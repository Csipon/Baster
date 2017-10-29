package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.asynch.MyAsyncTask;
import com.team.baster.domain.BasterGame;
import com.team.baster.generator.BlockGenerator;
import com.team.baster.model.Square;
import com.team.baster.screens.GameOverScreen;
import com.team.baster.storage.PlayerStatusStorage;
import com.team.baster.storage.ScoreStorage;

import static com.team.baster.GameConstants.HORIZONTAL_SPEED;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/28/2017.
 */

public class BlockController {
    private ScoreStorage scoreStorage;
    private PlayerStatusStorage playerStatusStorage;
    public BlockGenerator blockGenerator;
    BasterGame game;

    public BlockController(BasterGame game) {
        this.game = game;
        blockGenerator = new BlockGenerator();
        scoreStorage = new ScoreStorage();
        playerStatusStorage = PlayerStatusStorage.getInstance();
    }

    public void controlItemPosition(Rectangle hero, int speed, int score, int coins) {
        controlBlockPosition(hero, speed, score, coins);
        controlSquarePosition(hero, speed, score, coins);
    }

    private void controlBlockPosition(Rectangle hero, int speed, int score, int coins) {

        for (Rectangle item : blockGenerator.blocks) {
            item.y += speed * Gdx.graphics.getDeltaTime();
            checkHeroCollision(item, hero, score, coins);
        }
    }

    private void controlSquarePosition(Rectangle hero, int speed, int score, int coins) {
        for (Rectangle aSquare : blockGenerator.square) {
            Square item = (Square) aSquare;
            item.y += speed * Gdx.graphics.getDeltaTime();
            item.checkCoordinate(WORLD_WIDTH);
            int horMove = (int) (HORIZONTAL_SPEED * Gdx.graphics.getDeltaTime());
            if (item.isRight()) {
                item.x += horMove;
            } else if (item.isLeft()) {
                item.x -= horMove;
            }
            checkHeroCollision(item, hero, score, coins);
        }
    }

    private void checkHeroCollision(Rectangle item, Rectangle hero, int score, int coins) {
        if (item.overlaps(hero)) {
            save(score, coins);
            game.setScreen(new GameOverScreen(game, score));
        }
    }

    public void checkLasDropItemTime() {
        if (blockGenerator.lastDropItem.y > WORLD_HEIGHT / 3) {
            dropItem();
        }
    }


    private void save(int score, int coins){
        System.out.println("------- 0 "  + TimeUtils.millis());

        new MyAsyncTask(scoreStorage, playerStatusStorage, score, coins).execute();
    }

    public void dropItem(){
        blockGenerator.dropItem();
    }

}
