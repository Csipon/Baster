package com.team.baster.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.team.baster.BasterGame;
import com.team.baster.hero.BasterScreen;

import sun.font.TextLabel;

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
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private Long score;

    public GameOverScreen(BasterGame game, Long score) {

        this.game = game;
        this.score = score;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        skin = new Skin(Gdx.files.internal("skin/freezing-ui.json"));

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        btnMenu = new TextButton("Menu", skin);
        btnRetry = new TextButton("Retry", skin);

        Label label = new Label("Your score " + score, skin);
        label.setPosition(300, 900);
        label.setWidth(200);
        label.setHeight(50);
        label.setWrap(true);

        mainTable = new Table();
        mainTable.center();
        mainTable.setFillParent(true);
        mainTable.addActor(label);

        mainTable.add(btnRetry)
                .width(Value.percentWidth(.75F, mainTable))
                .height(Value.percentHeight(.20F, mainTable));
        mainTable.row();
        mainTable.add(btnMenu)
                .width(Value.percentWidth(.75F, mainTable))
                .height(Value.percentHeight(.20F, mainTable));

        stage.addActor(mainTable);

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
