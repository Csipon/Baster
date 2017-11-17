package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.generator.BackgroundGenerator;

import java.util.Iterator;

import static com.team.baster.GameConstants.WORLD_HEIGHT;

/**
 * Created by Pasha on 10/28/2017.
 */

public class BackgroundController {
    public Array<Rectangle> background;
    public BackgroundGenerator backgroundGenerator;

    public BackgroundController(Texture bgImg) {
        background          = new Array<>();
        backgroundGenerator = new BackgroundGenerator(bgImg, background);
    }


    public void controlBackgroundPosition(int speed) {
        Iterator<Rectangle> iter = background.iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += speed * Gdx.graphics.getDeltaTime();
            if (item.y + WORLD_HEIGHT > WORLD_HEIGHT * 2) {
                iter.remove();
            }
        }
    }

    public void checkLasDropBackground() {
        if (backgroundGenerator.lastDropBack.y >= -2) {
            backgroundGenerator.dropBackground();
        }
    }

    public void dropStartBackground(){
        backgroundGenerator.dropStartBackground();
    }
}
