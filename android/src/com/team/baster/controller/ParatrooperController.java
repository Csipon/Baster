package com.team.baster.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.model.Paratrooper;

import static com.team.baster.GameConstants.MAX_GENER_TIME;
import static com.team.baster.GameConstants.MIN_GENER_TIME;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 11/8/2017.
 */

public class ParatrooperController {
    public Paratrooper paratrooper;
    public boolean isFly = false;
    private long lastGenerated;
    private long periodGeneration;

    public ParatrooperController() {
        paratrooper = new Paratrooper();
        lastGenerated = TimeUtils.nanoTime();
        periodGeneration = MathUtils.random(MIN_GENER_TIME, MAX_GENER_TIME);
    }


    private void generateParatrooper(){
        paratrooper.body.x = WORLD_WIDTH /2 - paratrooper.body.width / 2;
        paratrooper.body.y = -paratrooper.body.height;
        paratrooper.changeCoordinate((int) paratrooper.body.y);
        System.out.println("PARATROOPER");
    }

    public void controlGenerated(){
        if (TimeUtils.nanoTime() - lastGenerated > periodGeneration && !isFly){
            generateParatrooper();
            lastGenerated = TimeUtils.nanoTime();
            periodGeneration = MathUtils.random(MIN_GENER_TIME, MAX_GENER_TIME);
            isFly = true;
        }
    }

    public boolean checkCollision(HeroController heroController){
        return paratrooper.checkCollision(heroController.circleHead, heroController.circleBody);
    }


    public void controlPosition(int speed){
        paratrooper.changeCoordinate(speed);
        paratrooper.checkCoordinate(WORLD_WIDTH);
        if (paratrooper.body.y > WORLD_HEIGHT){
            isFly = false;
        }
    }
}
