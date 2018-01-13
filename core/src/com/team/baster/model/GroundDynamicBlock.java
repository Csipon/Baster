package com.team.baster.model;

import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
import static com.team.baster.GameConstants.ITEM_THUNDER_WIDTH;

/**
 * Created by Pasha on 13.01.2018.
 */

public class GroundDynamicBlock extends DynamicBlock {
    public GroundDynamicBlock(Rectangle rect) {
        super(rect);
    }

    public GroundDynamicBlock() {
        this.height  = ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
        this.width   = ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
    }


    public void checkCoordinate(int worldWidth){
        if (this.x <= ITEM_THUNDER_WIDTH){
            moveRight();
        }else if (this.x >= worldWidth - ITEM_THUNDER_WIDTH - ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE){
            moveLeft();
        }
    }
}
