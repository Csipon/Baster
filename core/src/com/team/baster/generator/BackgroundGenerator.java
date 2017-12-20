package com.team.baster.generator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.List;

import static com.team.baster.GameConstants.WORLD_HEIGHT;

/**
 * Created by Pasha on 10/23/2017.
 */

public class BackgroundGenerator {



    private List<Rectangle> backgrounds;
    public Rectangle lastDropBack;
    private Texture backgroundImg;

    public BackgroundGenerator(Texture backgroundImg, List<Rectangle> backgrounds) {
        this.backgroundImg = backgroundImg;
        this.backgrounds = backgrounds;
    }


    public void dropBackground() {
        Rectangle back;
        if (backgrounds.size() == 5) {
            back = backgrounds.remove(0);
        } else {
            back = new Rectangle();
        }

        back.x = 0;
        if (lastDropBack != null) {
            back.y = lastDropBack.y - backgroundImg.getHeight();
        } else {
            back.y = WORLD_HEIGHT - backgroundImg.getHeight();
        }
        back.width = backgroundImg.getWidth();
        back.height = backgroundImg.getHeight();

        backgrounds.add(back);
        lastDropBack = back;
    }

    public void dropStartBackground() {
        for (int counter = 1; true; counter++) {
            if (counter * backgroundImg.getHeight() <= WORLD_HEIGHT) {
                dropBackground();
            } else {
                dropBackground();
                break;
            }
        }
    }
}
