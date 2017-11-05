package com.team.baster.model;

import static com.team.baster.GameConstants.ITEM_VERT_HEIGHT;
import static com.team.baster.GameConstants.ITEM_VERT_WIDTH;

/**
 * Created by Pasha on 11/5/2017.
 */

public class VertBlock extends Block {

    public boolean isTop;
    public boolean isBody;
    public boolean isBot;

    public VertBlock() {
        this.width = ITEM_VERT_WIDTH;
        this.height = ITEM_VERT_HEIGHT;
    }

    public VertBlock(VertBlock rect) {
        super(rect);
        this.isTop = rect.isTop;
        this.isBody = rect.isBody;
        this.isBot = rect.isBot;
    }
}
