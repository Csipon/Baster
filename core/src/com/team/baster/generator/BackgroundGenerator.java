package com.team.baster.generator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import static com.team.baster.GameConstants.WORLD_HEIGHT;

/**
 * Created by Pasha on 10/23/2017.
 */

public class BackgroundGenerator {

    private Array<Rectangle> background;
    private Rectangle lastDropBack;
    private Texture backgroundImg;

    public BackgroundGenerator(Texture backgroundImg) {
        this.backgroundImg = backgroundImg;
        background = new Array<>();
    }


    private void dropBackground() {
        Rectangle back;
        if (background.size == 5) {
            back = background.removeIndex(0);
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

        background.add(back);
        lastDropBack = back;
    }

    public void checkLasDropBackground() {
        if (lastDropBack.y >= -2) {
            dropBackground();
        }
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


    public Array<Rectangle> getBackground() {
        return background;
    }
}
