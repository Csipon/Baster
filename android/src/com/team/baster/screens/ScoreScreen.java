package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.domain.BasterGame;
import com.team.baster.service.ScoreService;
import com.team.baster.service.ServiceFactory;
import com.team.baster.storage.model.Score;
import com.team.baster.style.font.FontGenerator;

import java.util.List;

import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Smeet on 29.10.2017.
 */

public class ScoreScreen implements Screen {

    private BasterGame game;
    private SpriteBatch batch;
    private ScoreService scoreService;
    private FontGenerator fontGenerator;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Table scoreTable;
    private Image backImg;

    private List<Long> scores;


    public ScoreScreen(BasterGame game) {
        this.game    = game;
        scoreService = ServiceFactory.getScoreService();
        batch        = new SpriteBatch();
        camera       = new OrthographicCamera();
        viewport     = new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();
        stage        = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        setNavigation();
        showScoreStats();
    }

    @Override
    public void render(float delta) {

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        stage.dispose();
    }

    private void setNavigation() {
        backImg = new Image(new Texture(Gdx.files.internal("icons/back-arrow.png")));
        backImg.setY(WORLD_HEIGHT - 130);
        backImg.setX(0);

        backImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen());
                dispose();
            }
        });

        stage.addActor(backImg);
    }

    private void showScoreStats() {
        scoreTable                  = new Table();
        fontGenerator               = new FontGenerator();
        scores                      = scoreService.readLastBestScore();
        List<Score> backupQueue     = scoreService.readFromBackupQueue();
        Label.LabelStyle labelStyle = fontGenerator.getLabelStyle72();
        if (!backupQueue.isEmpty()) {
            scoreService.saveScoresToBack(backupQueue);
        }
        scoreTable.center();
        scoreTable.setFillParent(true);

        final int[] counter = {1};

        if(scores.size() != 0) {
            scores.forEach(score -> {
                scoreTable.add(new Label(counter[0]++ + ". " + score, labelStyle));
                scoreTable.row();
            });
        } else {
            scoreTable.add(new Label("No records yet", labelStyle));
        }
        stage.addActor(scoreTable);
    }
}
