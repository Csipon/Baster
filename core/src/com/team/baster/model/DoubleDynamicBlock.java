package com.team.baster.model;

import com.badlogic.gdx.math.Rectangle;
import com.team.baster.GameConstants;

import static com.team.baster.GameConstants.ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;

/**
 * Created by Pasha on 13.01.2018.
 */

public class DoubleDynamicBlock extends DynamicBlock {
    private boolean leftSide;
    private boolean rightSide;

    public DoubleDynamicBlock(Rectangle rect) {
        super(rect);
        leftSide = ((DoubleDynamicBlock) rect).isLeftSide();
        rightSide = ((DoubleDynamicBlock) rect).isRightSide();
    }

    public DoubleDynamicBlock() {
        this.height  = ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
        this.width   = ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
    }


    public void setLeftSideBlock(){
        this.x = 0;
        leftSide = true;
        rightSide = false;
        moveRight();
    }

    public void setRightSideBlock(){
        this.x = GameConstants.WORLD_WIDTH - this.width;
        rightSide = true;
        leftSide = false;
        moveLeft();
    }

    public boolean isLeftSide() {
        return leftSide;
    }

    public boolean isRightSide() {
        return rightSide;
    }

    @Override
    public void checkCoordinate(int worldWidth){
        if (leftSide){
            if (this.x <= 1){
                moveRight();
            }else if (this.x >= (worldWidth / 2) - this.width){
                moveLeft();
            }
        }else if (rightSide) {
            if (this.x <= (worldWidth / 2)) {
                moveRight();
            } else if (this.x >= worldWidth - this.width) {
                moveLeft();
            }
        }
    }
}
