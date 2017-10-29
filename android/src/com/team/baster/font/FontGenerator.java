package com.team.baster.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Smeet on 29.10.2017.
 */

public class FontGenerator {

    public BitmapFont generateFont(String font) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 72;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font));
        BitmapFont createdFont = generator.generateFont(parameter);

        generator.dispose();
        return createdFont;
    }

}
