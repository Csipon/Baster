package com.team.baster.domain;

import android.os.AsyncTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.team.baster.screens.SplashScreen;


public class BasterGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont customFont;
    public BasterGame() {

    }

    @Override
    public void create() {
        batch       = new SpriteBatch();
        font        = new BitmapFont();
        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {
                customFont  = generateCustomFont();
                return objects;
            }
        }.execute();
        setScreen(new SplashScreen());
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
