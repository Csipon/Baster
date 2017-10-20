package com.team.baster.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.BasterGame;
import com.team.baster.hero.BasterScreen;

import static com.team.baster.GameConstants.*;

/**
 * Created by Smeet on 20.10.2017.
 */

public class MenuScreen implements Screen {


    final BasterGame game;

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;


    public MenuScreen(BasterGame game)
    {

        this.game = game;
        atlas = new TextureAtlas(Gdx.files.internal("skin/freezing-ui.atlas"));
        skin = new Skin(Gdx.files.internal("skin/freezing-ui.json"), atlas);


        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        mainTable.center();
        //Set table to fill stage
        mainTable.setFillParent(true);

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BasterScreen(game));
                dispose();
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        mainTable.add(playButton)
                .width(Value.percentWidth(.75F, mainTable))
                .height(Value.percentHeight(.10F, mainTable));
        mainTable.row();
        mainTable.add(optionsButton)
                .width(Value.percentWidth(.75F, mainTable))
                .height(Value.percentHeight(.10F, mainTable));
        mainTable.row();
        mainTable.add(exitButton)
                .width(Value.percentWidth(.75F, mainTable))
                .height(Value.percentHeight(.10F, mainTable));

        //Add table to stage
        stage.addActor(mainTable);
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
        skin.dispose();
        atlas.dispose();
    }
}

