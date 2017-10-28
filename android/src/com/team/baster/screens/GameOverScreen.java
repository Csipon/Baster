package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.team.baster.domain.BasterGame;

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
        //comm
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

        String labelStr = "Your score: " + score;
        Label label = new Label("Your score: " + score, new Label.LabelStyle(generateFont(labelStr), Color.WHITE));

        mainTable = new Table();
        mainTable.center();
        mainTable.setFillParent(true);

        mainTable.add(label);
        mainTable.row();
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

    private BitmapFont generateFont(String characters) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = 72;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GoodDog.otf"));
        BitmapFont font = generator.generateFont(parameter);

        generator.dispose();
        return font;
    }
}
