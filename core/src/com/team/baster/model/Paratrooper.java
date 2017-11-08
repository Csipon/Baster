package com.team.baster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.team.baster.CollisionChecker;

import static com.team.baster.GameConstants.PARATROOPER_BODE_WIDTH;
import static com.team.baster.GameConstants.PARATROOPER_BODY_HEIGHT;
import static com.team.baster.GameConstants.PARATROOPER_HEIGHT;
import static com.team.baster.GameConstants.PARATROOPER_WIDTH;
import static com.team.baster.GameConstants.PARATROOP_RADIUS;

/**
 * Created by Pasha on 11/8/2017.
 */

public class Paratrooper {

    public Rectangle body;
    public Rectangle man;
    public Circle paratroop;

    private boolean left;
    private boolean right;

    public final int speed = 200;


    public Paratrooper() {
        body = new Rectangle();
        man = new Rectangle();
        paratroop = new Circle();
        body.width = PARATROOPER_BODE_WIDTH;
        body.height = PARATROOPER_BODY_HEIGHT;
        man.width = PARATROOPER_WIDTH;
        man.height = PARATROOPER_HEIGHT;
        paratroop.radius = PARATROOP_RADIUS;
        if (Math.random() > 0.5){
            moveRight();
        }else {
            moveLeft();
        }
    }

    public void changeCoordinate(int y){
        if (left){
            body.x = body.x - speed * Gdx.graphics.getDeltaTime();
        }else {
            body.x = body.x + speed * Gdx.graphics.getDeltaTime();
        }
        System.out.println("PARA^ ----------- " + y + " ==== " + body.x);
        body.y = y;
        man.x = body.x + body.width / 2  - man.width / 2;
        man.y = body.y;
        paratroop.x = body.width /2 + body.x;
        paratroop.y = body.y + body.height - paratroop.radius;
    }

    public void moveLeft(){
        left = true;
        right = false;
    }

    public void moveRight(){
        right = true;
        left = false;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void checkCoordinate(int worldWidth){
        if (body.x <= 1){
            moveRight();
        }else if (body.x >= worldWidth - body.width){
            moveLeft();
        }
    }

    public boolean checkCollision(Circle heroHead, Circle heroBody){
        if (CollisionChecker.hasCollision(paratroop, heroHead)
                || CollisionChecker.hasCollision(paratroop, heroBody)
                || CollisionChecker.intersect(man, heroBody)
                || CollisionChecker.intersect(man, heroHead)){
            return true;
        }
        return false;
    }


}
