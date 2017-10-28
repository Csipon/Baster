package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.team.baster.domain.BasterGame;
import com.team.baster.generator.BlockGenerator;
import com.team.baster.model.Square;
import com.team.baster.screens.GameOverScreen;
import com.team.baster.storage.ScoreStorage;

import static com.team.baster.GameConstants.HORIZONTAL_SPEED;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/28/2017.
 */

public class BlockController {
    private ScoreStorage scoreStorage;
    public BlockGenerator blockGenerator;
    BasterGame game;

    public BlockController(BasterGame game) {
        this.game = game;
        blockGenerator = new BlockGenerator();
        scoreStorage = new ScoreStorage();
    }

    public void controlItemPosition(Rectangle hero, int speed, long score) {
        controlBlockPosition(hero, speed, score);
        controlSquarePosition(hero, speed, score);
    }

    private void controlBlockPosition(Rectangle hero, int speed, long score) {

        for (Rectangle item : blockGenerator.blocks) {
            item.y += speed * Gdx.graphics.getDeltaTime();
            checkHeroCollision(item, hero, score);
        }
    }

    private void controlSquarePosition(Rectangle hero, int speed, long score) {
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
            checkHeroCollision(item, hero, score);
        }
    }

    private void checkHeroCollision(Rectangle item, Rectangle hero, long score) {
        if (item.overlaps(hero)) {
            scoreStorage.save(score);
            game.setScreen(new GameOverScreen(game, score));
        }
    }

    public void checkLasDropItemTime() {
        if (blockGenerator.lastDropItem.y > WORLD_HEIGHT / 3) {
            dropItem();
        }
    }


    public void dropItem(){
        blockGenerator.dropItem();
    }

}
