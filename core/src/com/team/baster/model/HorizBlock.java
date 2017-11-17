package com.team.baster.model;

import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.ITEM_HEIGHT;
import static com.team.baster.GameConstants.ITEM_WIDTH;

/**
 * Created by Pasha on 11/5/2017.
 */

public class HorizBlock extends Block {
    public HorizBlock() {
        this.width   = ITEM_WIDTH;
        this.height  = ITEM_HEIGHT;
    }

    public HorizBlock(Rectangle rect) {
        super(rect);
    }
}
