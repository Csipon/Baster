package com.team.baster.style.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Smeet on 29.10.2017.
 */

public class FontGenerator {

    Label.LabelStyle labelStyle72 = new Label.LabelStyle(generateFont("fonts/kenvector_future_thin.ttf", 40), Color.WHITE);
    Label.LabelStyle styleTitle = new Label.LabelStyle(generateFont("fonts/kenvector_future_thin.ttf", 35), Color.WHITE);
    Label.LabelStyle labelStyle72Yellow = new Label.LabelStyle(generateFont("fonts/kenvector_future_thin.ttf", 40), Color.YELLOW);
    Label.LabelStyle labelStyle35 = new Label.LabelStyle(generateFont("fonts/GoodDog.otf", 35), Color.WHITE);
    Label.LabelStyle labelStyle25 = new Label.LabelStyle(generateFont("fonts/kenvector_future_thin.ttf", 20), Color.WHITE);

    public BitmapFont generateFont(String font, int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font));
        BitmapFont createdFont = generator.generateFont(parameter);

        generator.dispose();
        return createdFont;
    }

    public Label.LabelStyle getLabelStyle72() {
        return labelStyle72;
    }
    public Label.LabelStyle getStyleTitle() {
        return styleTitle;
    }
    public Label.LabelStyle getLabelStyle35() {
        return labelStyle35;
    }
    public Label.LabelStyle getLabelStyle25() {
        return labelStyle25;
    }
    public Label.LabelStyle getLabelStyle72Yellow() {
        return labelStyle72Yellow;
    }

}
