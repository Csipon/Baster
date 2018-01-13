package com.team.baster.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.AndroidInstanceHolder;
import com.team.baster.domain.BasterGame;

import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 11.01.2018.
 */

public class StartupScreen implements Screen {
    private BasterGame game;
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;



    public StartupScreen() {
        this.game    = (BasterGame) AndroidInstanceHolder.getGame();
        batch        = new SpriteBatch();
        camera       = new OrthographicCamera();
        viewport     = new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();
        stage        = new Stage(viewport, batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
