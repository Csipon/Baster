package com.team.baster.domain;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.team.baster.dialog.ActionResolver;
import com.team.baster.screens.MenuScreen;


public class BasterGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont customFont;
    public ActionResolver actionResolver;
    public Context context;

    public BasterGame(ActionResolver actionResolver, Context context) {
        this.actionResolver = actionResolver;
        this.context = context;
    }

    @Override
    public void create() {
        batch       = new SpriteBatch();
        font        = new BitmapFont();
        customFont  = generateCustomFont();
        setScreen(new MenuScreen(this));
    }

    private BitmapFont generateCustomFont() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size  = 45;
        parameter.color = Color.WHITE;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
        return generator.generateFont(parameter);
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
        customFont.dispose();
    }
}
