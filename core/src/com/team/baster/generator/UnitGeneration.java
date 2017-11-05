package com.team.baster.generator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.model.Burger;
import com.team.baster.model.Pill;
import com.team.baster.model.Square;

/**
 * Created by Pasha on 11/3/2017.
 */

public class UnitGeneration {
    public Array<Rectangle> blocks;
    public Array<Rectangle> actionItems;
    public int minPointY;


    public UnitGeneration() {
        blocks = new Array<>();
        actionItems = new Array<>();
    }


    public UnitGeneration getCopy(){
        UnitGeneration unit = new UnitGeneration();
        for (Rectangle rectangle : blocks){
            unit.blocks.add(getCopy(rectangle));
        }
        for (Rectangle rectangle : actionItems){
            unit.actionItems.add(getCopy(rectangle));
        }
        unit.minPointY = minPointY;
        return unit;
    }

    private Rectangle getCopy(Rectangle src){
        Rectangle copy;
        if (src instanceof Square){
            copy = new Square(src);
        }else if (src instanceof Pill){
            copy =  new Pill(src);
        }else if (src instanceof Burger){
            copy = new Burger(src);
        }else {
            copy = new Rectangle(src);
        }
        return copy;
    }
}
