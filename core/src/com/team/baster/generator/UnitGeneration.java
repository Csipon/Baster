package com.team.baster.generator;

import com.badlogic.gdx.math.Rectangle;
import com.team.baster.model.Burger;
import com.team.baster.model.DoubleDynamicBlock;
import com.team.baster.model.DynamicBlock;
import com.team.baster.model.EnumTypeOfBlock;
import com.team.baster.model.GroundDynamicBlock;
import com.team.baster.model.HorizBlock;
import com.team.baster.model.Pill;
import com.team.baster.model.Thunder;
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
    public EnumTypeOfBlock typeOfBlock;


    public UnitGeneration() {
        blocks = new ArrayList<>();
        actionItems = new ArrayList<>();
    }


    public UnitGeneration getCopy() {
        UnitGeneration unit = new UnitGeneration();

        for(Rectangle element : blocks) {
            unit.blocks.add(getCopy(element));
        }
//      blocks.forEach((element) -> unit.blocks.add(getCopy(element)));

        for(Rectangle element : actionItems) {
            unit.actionItems.add(getCopy(element));
        }
        //actionItems.forEach((element) -> unit.actionItems.add(getCopy(element)));



        unit.minPointY = minPointY;
        for (Rectangle block : unit.blocks) {
            if (block instanceof DynamicBlock) {
                ((DynamicBlock) block).makeRandMove();
            }
        }
        return unit;
    }

    private Rectangle getCopy(Rectangle src) {
        Rectangle copy;
        if (src instanceof DoubleDynamicBlock) {
            copy = new DoubleDynamicBlock(src);
        }else if (src instanceof GroundDynamicBlock) {
            copy = new GroundDynamicBlock(src);
        }else if (src instanceof DynamicBlock) {
            copy = new DynamicBlock(src);
        } else if (src instanceof HorizBlock) {
            copy = new HorizBlock(src);
        }else if (src instanceof Thunder) {
            copy = new Thunder(src);
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
