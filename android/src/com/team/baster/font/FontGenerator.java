package com.team.baster.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Smeet on 29.10.2017.
 */

public class FontGenerator {

    Label.LabelStyle labelStyle = new Label.LabelStyle(generateFont("fonts/GoodDog.otf"), Color.WHITE);

    public BitmapFont generateFont(String font) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 72;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font));
        BitmapFont createdFont = generator.generateFont(parameter);

        generator.dispose();
        return createdFont;
    }

    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }

}
