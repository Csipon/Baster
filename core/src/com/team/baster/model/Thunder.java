package com.team.baster.model;

import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.ITEM_THUNDER_HEIGHT;
import static com.team.baster.GameConstants.ITEM_THUNDER_WIDTH;

/**
 * Created by Pasha on 13.01.2018.
 */

public class Thunder extends Block{

    public Thunder() {
        this.width = ITEM_THUNDER_WIDTH;
        this.height = ITEM_THUNDER_HEIGHT;
    }

    public Thunder(Rectangle rect) {
        super(rect);
    }
}
