package com.team.baster.screens;

import android.os.AsyncTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team.baster.AndroidInstanceHolder;
import com.team.baster.dialog.ActionResolverImpl;
import com.team.baster.domain.BasterGame;
import com.team.baster.security.FirebaseAuthentication;

import util.RequestUtil;

/**
 * Created by Pasha on 22.01.2018.
 */

public class SplashScreen implements Screen {
    private SpriteBatch batch;
    private Texture ttrSplash;
    private boolean isFinished;
    private static long SPLASH_PAUSE = 2500;

    public SplashScreen() {
        super();
        batch = ((BasterGame) AndroidInstanceHolder.getGame()).batch;
        ttrSplash = new Texture(Gdx.files.internal("csipon_origin.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        if (isFinished){
            isFinished = false;
            AndroidInstanceHolder.getGame().setScreen(new MenuScreen());
        }
    }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() {
        long startTime = System.currentTimeMillis();
        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                AndroidInstanceHolder.setActionResolverAndroid(new ActionResolverImpl(AndroidInstanceHolder.getAndroidLauncher()));
                RequestUtil.instance = new RequestUtil();
                AndroidInstanceHolder.setAuth(new FirebaseAuthentication());
                isFinished = true;
                while (System.currentTimeMillis() - startTime <= SPLASH_PAUSE){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return objects;
            }
        }.execute();

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void dispose() {
        ttrSplash.dispose();
        batch.dispose();
    }
}
