package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.asynch.MyAsyncTask;
import com.team.baster.domain.BasterGame;
import com.team.baster.generator.Generator;
import com.team.baster.generator.UnitGeneration;
import com.team.baster.model.Burger;
import com.team.baster.model.DynamicBlock;
import com.team.baster.model.Pill;
import com.team.baster.screens.GameOverScreen;
import com.team.baster.service.PlayerService;
import com.team.baster.service.ScoreService;
import com.team.baster.service.ServiceFactory;
import com.team.baster.storage.model.Score;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import static com.team.baster.CollisionChecker.intersect;
import static com.team.baster.GameConstants.HORIZONTAL_SPEED;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/28/2017.
 */

public class BlockController {

    private static ScoreService scoreService    = ServiceFactory.getScoreService();
    private static PlayerService playerService  = ServiceFactory.getPlayerService();
    public Array<UnitGeneration> units;
    private Random random;
    private BasterGame game;
    private Generator generator;
    private HeroController heroController;
    private ParatrooperController paratrooperController;

    public BlockController(BasterGame game, HeroController heroController, ParatrooperController paratrooperController) {
        this.game                   = game;
        this.heroController         = heroController;
        this.paratrooperController  = paratrooperController;
        random                      = new Random();
        units                       = new Array<>();
        generator                   = new Generator();
    }

    public void controlItemsPosition(Circle head, Circle body, int speed, int score, int coins, ParticleEffect pe) {
        for (UnitGeneration unit : units) {
            float currentSpeed = speed * Gdx.graphics.getDeltaTime();
            for (Rectangle rect : unit.blocks) {
                rect.y += currentSpeed;
                if (rect instanceof DynamicBlock) {
                    controlDynamicPosition((DynamicBlock) rect);
                }
                checkHeroCollision(rect, head, body, score, coins);
            }
            Iterator<Rectangle> iterator = unit.actionItems.iterator();
            while (iterator.hasNext()) {
                Rectangle rect = iterator.next();
                rect.y += currentSpeed;
                if (checkActionItemCollision(rect, head, body, pe)) {
                    iterator.remove();
                }
            }
            unit.minPointY += currentSpeed;
        }
        controlParatrooper(speed, score, coins);
        cleanUnits();
    }
    private void controlParatrooper(int speed, int score, int coins){
        if (paratrooperController.isFly) {
            int currentSpeed = (int) ((speed * Gdx.graphics.getDeltaTime()) * 1.8);
            int currentYPos  = (int) paratrooperController.paratrooper.body.y;
            paratrooperController.controlPosition(currentYPos + currentSpeed);
            checkParatrooperCollision(score, coins);
        }
    }

    private void cleanUnits(){
        Iterator<UnitGeneration> iter = units.iterator();
        while (iter.hasNext()) {
            UnitGeneration unit = iter.next();
            if (unit.minPointY > WORLD_HEIGHT) {
                iter.remove();
            }
        }
    }

    private void controlDynamicPosition(DynamicBlock item) {
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
    private void checkParatrooperCollision(int score, int coins){
        if (paratrooperController.checkCollision(heroController)){
            save(score, coins);
            game.setScreen(new GameOverScreen(game, score, coins));
        }
    }

    private boolean checkActionItemCollision(Rectangle item, Circle head, Circle body, ParticleEffect pe) {
        if (intersect(item, head) || intersect(item, body)) {
            if (item instanceof Pill) {
                heroController.diet();
            } else if (item instanceof Burger) {
                heroController.eatFood();
                pe.setPosition(item.x, item.y);
                pe.start();
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

    @SuppressWarnings("unchecked")
    private void save(int score, int coins) {
        Score scoreObj = new Score();
        scoreObj.setLogin(playerService.getCurrentUser().getLogin());
        scoreObj.setScore(score);
        scoreObj.setDate(new Date(System.currentTimeMillis()));
        scoreService.saveScoreToBack(scoreObj);
        new MyAsyncTask(scoreService, playerService, score, coins).execute();
    }

    public void dropItem() {
        units.add(generator.units.get(random.nextInt(generator.units.size)).getCopy());
    }

}
