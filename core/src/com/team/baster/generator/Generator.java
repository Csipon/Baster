package com.team.baster.generator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.model.Burger;
import com.team.baster.model.Pill;
import com.team.baster.model.Square;

import static com.team.baster.GameConstants.ACTION_ITEM_SIDE;
import static com.team.baster.GameConstants.BUFFER_Y;
import static com.team.baster.GameConstants.ITEM_SQUARE_SIDE;
import static com.team.baster.GameConstants.ITEM_VERT_HEIGHT;
import static com.team.baster.GameConstants.ITEM_VERT_WIDTH;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 11/3/2017.
 */

public class Generator {

    public Array<UnitGeneration> units;

    public Generator() {
        this.units = new Array<>();
        initUnits();
    }


    private void initUnits(){
        dynamicBlock();
        tonelBlocks();
    }


    private void dynamicBlock(){
        UnitGeneration unitGeneration = new UnitGeneration();
        Square rectangle = new Square();
        rectangle.x = WORLD_WIDTH / 2;
        rectangle.y = BUFFER_Y -ITEM_SQUARE_SIDE;
        rectangle.moveLeft();
        unitGeneration.blocks.add(rectangle);
        unitGeneration.minPointY = (int) rectangle.y;
        units.add(unitGeneration);
    }


    private void tonelBlocks(){
        UnitGeneration unitGeneration = new UnitGeneration();
        int currentY = BUFFER_Y;
        for (int i = 0; i < 3; i++) {
            currentY -= ITEM_VERT_HEIGHT;
            unitGeneration.blocks.addAll(makeBlockLine(currentY));
            unitGeneration.actionItems.addAll(makeActionItems(currentY, i));
        }
        unitGeneration.minPointY = currentY;
        units.add(unitGeneration);
    }


    private Rectangle[] makeBlockLine(int y){
        Rectangle[] rect = new Rectangle[3];
        for (int i = 0; i < 3; i++){
            Rectangle rectangle = new Rectangle();
            if (i == 0){
                rectangle.x = 0;
            }else if (i == 1){
                rectangle.x = WORLD_WIDTH / 2 - ITEM_VERT_WIDTH / 2;
            }else {
                rectangle.x = WORLD_WIDTH - ITEM_VERT_WIDTH;
            }
            rectangle.y = y;
            rectangle.width = ITEM_VERT_WIDTH;
            rectangle.height = ITEM_VERT_HEIGHT;
            rect[i] = rectangle;
        }
        return rect;
    }

    private Rectangle[] makeActionItems(int y, int iter){
        Rectangle[] actionItems = new Rectangle[2];
        Rectangle rectLeft;
        Rectangle rectRight;
        if (iter % 2 == 0){
            rectLeft = new Pill();
            rectRight = new Burger();
        }else {
            rectLeft = new Burger();
            rectRight = new Pill();
        }

        rectLeft.x = (WORLD_WIDTH / 2 - ITEM_VERT_WIDTH - ITEM_VERT_WIDTH / 2) / 2 + ITEM_VERT_WIDTH - ACTION_ITEM_SIDE / 2;
        rectRight.x = WORLD_WIDTH / 2 + (WORLD_WIDTH / 2 - ITEM_VERT_WIDTH - ITEM_VERT_WIDTH / 2) / 2 + ITEM_VERT_WIDTH / 2 - ACTION_ITEM_SIDE / 2;


        rectLeft.y = y + ITEM_VERT_HEIGHT / 2 - ACTION_ITEM_SIDE / 2;
        rectRight.y = y + ITEM_VERT_HEIGHT / 2 - ACTION_ITEM_SIDE / 2;

        actionItems[0] = rectLeft;
        actionItems[1] = rectRight;

        return actionItems;
    }


}
