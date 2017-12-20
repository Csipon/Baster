package com.team.baster.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.team.baster.generator.BackgroundGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.team.baster.GameConstants.WORLD_HEIGHT;

/**
 * Created by Pasha on 10/28/2017.
 */

public class BackgroundController {
    private BackgroundGenerator backgroundGenerator;
    public List<Rectangle> background;

    public BackgroundController(Texture bgImg) {
        background          = new ArrayList<>();
        backgroundGenerator = new BackgroundGenerator(bgImg, background);
    }


    public void controlBackgroundPosition(int speed) {
        background.forEach((background) -> background.y += speed * Gdx.graphics.getDeltaTime());
        background.stream().filter((background) -> background.y + WORLD_HEIGHT < WORLD_HEIGHT * 2);
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
