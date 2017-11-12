package com.team.baster.style.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Smeet on 30.10.2017.
 */

public class ButtonStyleGenerator {


    public ImageButton.ImageButtonStyle generateButtonStyle(String pathToImg, int x, int y) {
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetY = -x;
        imageButtonStyle.pressedOffsetX = -y;
        imageButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathToImg))));

        return imageButtonStyle;
    }

    public ImageButton.ImageButtonStyle generateButtonStyleWithoutPress(String pathToImg) {
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathToImg))));

        return imageButtonStyle;
    }
}
