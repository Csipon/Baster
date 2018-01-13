package com.team.baster.generator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.model.ActionItem;
import com.team.baster.model.Burger;
import com.team.baster.model.DoubleDynamicBlock;
import com.team.baster.model.DynamicBlock;
import com.team.baster.model.EnumTypeOfBlock;
import com.team.baster.model.GroundDynamicBlock;
import com.team.baster.model.HorizBlock;
import com.team.baster.model.Pill;
import com.team.baster.model.Thunder;
import com.team.baster.model.VertBlock;

import java.util.Arrays;
import java.util.Random;

import static com.team.baster.GameConstants.*;

/**
 * Created by Pasha on 11/3/2017.
 */

public class Generator {

    public Array<UnitGeneration> units;
    private static final int COUNT_OF_BLOCKS = 3;
    private Random random;
    private EnumTypeOfBlock previousTypeOfBlock;

    public Generator() {
        this.units = new Array<>();
        this.random = new Random();
        initUnits();
    }


    private void initUnits(){
        dynamicBlock();
        tonelBlocks();
        makeFiveBlock();
        doubleDynamicBlock();
        thunderBlock();
    }

    public UnitGeneration generateUnit(){
        UnitGeneration unitGeneration = units.get(random.nextInt(units.size));
        if (unitGeneration.typeOfBlock != previousTypeOfBlock){
            previousTypeOfBlock = unitGeneration.typeOfBlock;
            return unitGeneration.getCopy();
        }
        return generateUnit();
    }

    private void doubleDynamicBlock() {
        UnitGeneration unitGeneration = new UnitGeneration();
        unitGeneration.typeOfBlock = EnumTypeOfBlock.DOUBLE_DYNAMIC_BLOCK;
        DoubleDynamicBlock leftSide = new DoubleDynamicBlock();
        leftSide.setLeftSideBlock();
        leftSide.y = BUFFER_Y - ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
        DoubleDynamicBlock rightSide = new DoubleDynamicBlock();
        rightSide.setRightSideBlock();
        rightSide.y = BUFFER_Y - ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
        unitGeneration.blocks.add(leftSide);
        unitGeneration.blocks.add(rightSide);
        unitGeneration.minPointY = (int) leftSide.y;
        units.add(unitGeneration);
    }


    private void dynamicBlock(){
        UnitGeneration unitGeneration   = new UnitGeneration();
        unitGeneration.typeOfBlock = EnumTypeOfBlock.DYNAMIC_BLOCK;
        DynamicBlock rectangle          = new DynamicBlock();
        rectangle.x                     = WORLD_WIDTH / 2;
        rectangle.y                     = BUFFER_Y - ITEM_AIRPLANE_HEIGHT;
        rectangle.makeRandMove();
        unitGeneration.blocks.add(rectangle);
        unitGeneration.minPointY        = (int) rectangle.y;
        units.add(unitGeneration);
    }


    private void tonelBlocks(){
        UnitGeneration unitGeneration = new UnitGeneration();
        unitGeneration.typeOfBlock = EnumTypeOfBlock.VERT_BLOCK;
        int currentY = BUFFER_Y;
        currentY    -= ITEM_TOP_VERT_HEIGHT;
        unitGeneration.blocks.addAll(Arrays.asList(makeTopBlocks(currentY)));
        for (int i = 0; i < COUNT_OF_BLOCKS; i++) {
            currentY -= ITEM_VERT_HEIGHT;
            unitGeneration.blocks.addAll(Arrays.asList(makeBlockLine(currentY, i != 2)));
            unitGeneration.actionItems.addAll(Arrays.asList(makeActionItems(currentY, i)));
        }
        unitGeneration.minPointY = currentY;
        units.add(unitGeneration);
    }

    private void thunderBlock(){
        UnitGeneration unitGeneration = new UnitGeneration();
        unitGeneration.typeOfBlock = EnumTypeOfBlock.THUNDER;
        Thunder thunderLeft = new Thunder();
        Thunder thunderRight = new Thunder();
        thunderLeft.x = 0;
        thunderLeft.y = BUFFER_Y - ITEM_THUNDER_HEIGHT;
        thunderRight.x = WORLD_WIDTH - ITEM_THUNDER_WIDTH;
        thunderRight.y = BUFFER_Y - ITEM_THUNDER_HEIGHT;

        GroundDynamicBlock groundFirstBlock = new GroundDynamicBlock();
        GroundDynamicBlock groundLastBlock = new GroundDynamicBlock();
        groundFirstBlock.y = BUFFER_Y - ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE * 2;
        groundLastBlock.y = thunderRight.y + ITEM_DOUBLE_DYNAMIC_BLOCK_SIDE;
        unitGeneration.blocks.add(thunderLeft);
        unitGeneration.blocks.add(thunderRight);
        unitGeneration.blocks.add(groundFirstBlock);
        unitGeneration.blocks.add(groundLastBlock);

        unitGeneration.minPointY = (int) thunderLeft.y;

        units.add(unitGeneration);
    }

    private Rectangle[] makeTopBlocks(int currentY) {
        Rectangle[] rect = new VertBlock[COUNT_OF_BLOCKS];
        for (int i = 0; i < COUNT_OF_BLOCKS; i++){
            VertBlock rectangle = new VertBlock();
            rectangle.isTop = true;
            if (i == 0){
                rectangle.x = 0;
            }else if (i == 1){
                rectangle.x = WORLD_WIDTH / 2 - ITEM_TOP_VERT_WIDTH / 2;
            }else {
                rectangle.x = WORLD_WIDTH - ITEM_TOP_VERT_WIDTH;
            }
            rectangle.y = currentY;
            rectangle.width     = ITEM_TOP_VERT_WIDTH;
            rectangle.height    = ITEM_TOP_VERT_HEIGHT;
            rect[i] = rectangle;
        }
        return rect;
    }


    private Rectangle[] makeBlockLine(int y, boolean isBody){
        Rectangle[] rect = new VertBlock[3];
        for (int i = 0; i < COUNT_OF_BLOCKS; i++){
            VertBlock rectangle = new VertBlock();
            if (isBody){
                rectangle.isBody = true;
            }else {
                rectangle.isBot = true;
            }
            if (i == 0){
                rectangle.x = (ITEM_TOP_VERT_WIDTH - ITEM_VERT_WIDTH) / 2;
            }else if (i == 1){
                rectangle.x = WORLD_WIDTH / 2 - ITEM_VERT_WIDTH / 2;
            }else {
                rectangle.x = WORLD_WIDTH - ITEM_VERT_WIDTH - (ITEM_TOP_VERT_WIDTH - ITEM_VERT_WIDTH) / 2;
            }
            rectangle.y = y;
            rectangle.width  = ITEM_VERT_WIDTH;
            rectangle.height = ITEM_VERT_HEIGHT;
            rect[i] = rectangle;
        }
        return rect;
    }

    private Rectangle[] makeActionItems(int y, int iter){
        ActionItem[] actionItems = new ActionItem[2];
        ActionItem rectLeft;
        ActionItem rectRight;
        if (iter % 2 == 0){
            rectLeft  = new Pill();
            rectRight = new Burger();
        }else {
            rectLeft  = new Burger();
            rectRight = new Pill();
        }

        rectLeft.x = (WORLD_WIDTH / 2 - ITEM_VERT_WIDTH - ITEM_VERT_WIDTH / 2) / 2 + ITEM_VERT_WIDTH - ACTION_ITEM_SIDE / 2;
        rectRight.x = WORLD_WIDTH / 2 + (WORLD_WIDTH / 2 - ITEM_VERT_WIDTH - ITEM_VERT_WIDTH / 2) / 2 + ITEM_VERT_WIDTH / 2 - ACTION_ITEM_SIDE / 2;


        rectLeft.y  = y + ITEM_VERT_HEIGHT / 2 - ACTION_ITEM_SIDE / 2;
        rectRight.y = y + ITEM_VERT_HEIGHT / 2 - ACTION_ITEM_SIDE / 2;

        actionItems[0] = rectLeft;
        actionItems[1] = rectRight;

        return actionItems;
    }

    private void makeFiveBlock(){
        UnitGeneration unitGeneration = new UnitGeneration();
        unitGeneration.typeOfBlock = EnumTypeOfBlock.HORiZONTAL_BLOCK;
        HorizBlock rect1    = new HorizBlock();
        HorizBlock rect2    = new HorizBlock();
        rect1.x             = 0;
        rect2.x             = WORLD_WIDTH - ITEM_WIDTH;
        rect1.y             = BUFFER_Y - ITEM_HEIGHT;
        rect2.y             = BUFFER_Y - ITEM_HEIGHT;

        Pill p1 = new Pill();
        p1.x    = WORLD_WIDTH / 2 - ACTION_ITEM_SIDE / 2;
        p1.y    = rect1.y;

        HorizBlock rect3 = new HorizBlock();
        rect3.x          = WORLD_WIDTH / 2 - ITEM_WIDTH / 2;
        rect3.y          = rect1.y - DISTANCE_FIVE_BLOCK - ITEM_HEIGHT;

        Pill p2 = new Pill();
        p2.x    = (WORLD_WIDTH / 2 - rect3.width / 2 - ACTION_ITEM_SIDE / 2) / 2;
        p2.y    = rect3.y;

        Burger b1 = new Burger();
        b1.x      = WORLD_WIDTH / 2 + rect3.width / 2 + (WORLD_WIDTH / 2 - rect3.width / 2 - ACTION_ITEM_SIDE / 2) / 2;
        b1.y      = rect3.y;

        HorizBlock rect4 = new HorizBlock();
        HorizBlock rect5 = new HorizBlock();
        rect4.x          = 0;
        rect5.x          = WORLD_WIDTH - ITEM_WIDTH;
        rect4.y          = rect3.y - DISTANCE_FIVE_BLOCK - ITEM_HEIGHT;
        rect5.y          = rect3.y - DISTANCE_FIVE_BLOCK - ITEM_HEIGHT;

        Burger b2 = new Burger();
        b2.x      = WORLD_WIDTH / 2 - ACTION_ITEM_SIDE / 2;
        b2.y      = rect5.y;

        unitGeneration.blocks.add(rect1);
        unitGeneration.blocks.add(rect2);
        unitGeneration.blocks.add(rect3);
        unitGeneration.blocks.add(rect4);
        unitGeneration.blocks.add(rect5);
        unitGeneration.actionItems.add(p1);
        unitGeneration.actionItems.add(p2);
        unitGeneration.actionItems.add(b1);
        unitGeneration.actionItems.add(b2);
        unitGeneration.minPointY = (int) rect5.y;

        units.add(unitGeneration);
    }
}
