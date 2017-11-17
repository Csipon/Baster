package com.team.baster.model;

import com.badlogic.gdx.math.Rectangle;

import static com.team.baster.GameConstants.ACTION_ITEM_SIDE;

/**
 * Created by Pasha on 11/3/2017.
 */

public class ActionItem extends Rectangle {
    public ActionItem() {
        this.height = ACTION_ITEM_SIDE;
        this.width  = ACTION_ITEM_SIDE;
    }


    public ActionItem(Rectangle rect) {
        super(rect);
    }
}
