package com.team.baster.generator;

import com.badlogic.gdx.math.Rectangle;
import com.team.baster.model.Burger;
import com.team.baster.model.DynamicBlock;
import com.team.baster.model.HorizBlock;
import com.team.baster.model.Pill;
import com.team.baster.model.VertBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 11/3/2017.
 */

public class UnitGeneration {
    public List<Rectangle> blocks;
    public List<Rectangle> actionItems;
    public int minPointY;


    public UnitGeneration() {
        blocks = new ArrayList<>();
        actionItems = new ArrayList<>();
    }


    public UnitGeneration getCopy() {
        UnitGeneration unit = new UnitGeneration();
        blocks.forEach((element) -> unit.blocks.add(getCopy(element)));
        actionItems.forEach((element) -> unit.actionItems.add(getCopy(element)));
        unit.minPointY = minPointY;
        if (blocks.get(0) instanceof DynamicBlock) {
            ((DynamicBlock) unit.blocks.get(0)).makeRandMove();
        }
        return unit;
    }

    private Rectangle getCopy(Rectangle src) {
        Rectangle copy;
        if (src instanceof DynamicBlock) {
            copy = new DynamicBlock(src);
        } else if (src instanceof HorizBlock) {
            copy = new HorizBlock(src);
        } else if (src instanceof VertBlock) {
            copy = new VertBlock((VertBlock) src);
        } else if (src instanceof Pill) {
            copy = new Pill(src);
        } else if (src instanceof Burger) {
            copy = new Burger(src);
        } else {
            copy = new Rectangle(src);
        }
        return copy;
    }
}
