package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.team.baster.domain.BasterGame;
import com.team.baster.font.FontGenerator;

import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Smeet on 21.10.2017.
 */

public class GameOverScreen implements Screen {

    private BasterGame game;
    private Stage stage;
    private Skin skin;
    private TextButton btnRetry;
    private TextButton btnMenu;
    private Table mainTable;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private FontGenerator fontGenerator;
    private Label labelScore;

    private int score;

    public GameOverScreen(BasterGame game, int score) {

        this.game = game;
        this.score = score;
        System.out.println("------- 2 "  + TimeUtils.millis());
        camera = new OrthographicCamera();
        viewport =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();

        fontGenerator = new FontGenerator();
        stage = new Stage(viewport, game.batch);
        skin = new Skin(Gdx.files.internal("skin/freezing-ui.json"));
        System.out.println("------- 3 "  + TimeUtils.millis());

    }

    @Override
    public void show() {
        System.out.println("------- 4 "  + TimeUtils.millis());
        Gdx.input.setInputProcessor(stage);

        loadButton();

        String strScore = "Your score: " + score;
        labelScore = new Label(strScore, fontGenerator.getLabelStyle());

        System.out.println("------- 5 "  + TimeUtils.millis());
        loadTable();
        System.out.println("------- 6 "  + TimeUtils.millis());

    }

    private void loadTable() {

        mainTable = new Table();
        mainTable.center();
        mainTable.setFillParent(true);

        mainTable.add(labelScore).padBottom(50);
        mainTable.row();
        mainTable.add(btnRetry)
                .width(Value.percentWidth(.75F, mainTable))
                .height(Value.percentHeight(.20F, mainTable));
        mainTable.row();
        mainTable.add(btnMenu)
                .width(Value.percentWidth(.75F, mainTable))
                .height(Value.percentHeight(.20F, mainTable));

        stage.addActor(mainTable);
    }

    private void loadButton() {

        btnMenu = new TextButton("Menu", skin);
        btnRetry = new TextButton("Retry", skin);

        btnRetry.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BasterScreen(game));
                dispose();
            }
        });
        btnMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
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
        skin.dispose();
        stage.dispose();
    }
}
