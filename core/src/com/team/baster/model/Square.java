package com.team.baster.model;

import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.ITEM_SQUARE_SIDE;

/**
 * Created by Pasha on 10/26/2017.
 */

public class Square extends Rectangle {
    private boolean left;
    private boolean right;


    public Square(Rectangle rect) {
        super(rect);
        left = ((Square) rect).isLeft();
        right = ((Square) rect).isRight();
    }

    public Square() {
        this.height = ITEM_SQUARE_SIDE;
        this.width = ITEM_SQUARE_SIDE;
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
        if (this.x <= 1){
            moveRight();
        }else if (this.x >= worldWidth - this.height){
            moveLeft();
        }
    }
}
