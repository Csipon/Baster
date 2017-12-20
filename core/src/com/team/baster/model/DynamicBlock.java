package com.team.baster.model;

import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.ITEM_AIRPLANE_HEIGHT;
import static com.team.baster.GameConstants.ITEM_AIRPLANE_WIDTH;

/**
 * Created by Pasha on 10/26/2017.
 */

public class DynamicBlock extends Block {
    private boolean left;
    private boolean right;


    public DynamicBlock(Rectangle rect) {
        super(rect);
        left = ((DynamicBlock) rect).isLeft();
        right = ((DynamicBlock) rect).isRight();
    }

    public DynamicBlock() {
        this.height  = ITEM_AIRPLANE_HEIGHT;
        this.width   = ITEM_AIRPLANE_WIDTH;
    }


    public void moveLeft(){
        left  = true;
        right = false;
    }

    public void moveRight(){
        right = true;
        left  = false;
    }

    public void makeRandMove(){
        if (Math.random() > 0.5) {
            moveLeft();
        } else {
            moveRight();
        }
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
        }else if (this.x >= worldWidth - this.width){
            moveLeft();
        }
    }
}
