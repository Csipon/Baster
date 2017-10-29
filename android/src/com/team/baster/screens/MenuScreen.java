package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.GameConstants;
import com.team.baster.domain.BasterGame;
import com.team.baster.dialog.QuitGame;
import com.team.baster.font.FontGenerator;
import com.team.baster.storage.ScoreStorage;

import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Smeet on 20.10.2017.
 */

public class MenuScreen implements Screen {


    final BasterGame game;
    private SpriteBatch batch;
    private ScoreStorage scoreStorage;
    private FontGenerator fontGenerator;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Table mainTable;
    private Image playImg;
    private Image exitImg;
    private Image scoreStatsImg;

    private Array<Long> scores;


    public MenuScreen(BasterGame game) {

        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();
        viewport =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();


        stage = new Stage(viewport, batch);
        scoreStorage = new ScoreStorage();
        fontGenerator = new FontGenerator();
    }


    @Override
    public void show() {
        scores = scoreStorage.readLastBestScore();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        setNavigation();
        setImage();
        setTable();

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

    public void setNavigation() {
        String strScore = scores.get(0).toString();
        Label labelScore = new Label(strScore, new Label.LabelStyle(fontGenerator.generateFont("fonts/GoodDog.otf"), Color.WHITE));
        labelScore.setX(130);
        labelScore.setY(WORLD_HEIGHT - 100);

        scoreStatsImg = new Image(new Texture(Gdx.files.internal("icons/score.png")));
        scoreStatsImg.setY(WORLD_HEIGHT - 130);
        scoreStatsImg.setX(5);

        Image coinsImage = new Image(new Texture(Gdx.files.internal("icons/coins.png")));
        coinsImage.setY(WORLD_HEIGHT - 230);
        coinsImage.setX(20);

        Image market = new Image(new Texture(Gdx.files.internal("icons/shop.png")));
        market.setY(WORLD_HEIGHT - 130);
        market.setX(WORLD_WIDTH - 150);

        stage.addActor(coinsImage);
        stage.addActor(scoreStatsImg);
        stage.addActor(market);
        stage.addActor(labelScore);
    }

    private void setImage() {
        playImg = new Image(new Texture(Gdx.files.internal("icons/start.png")));
        exitImg = new Image(new Texture(Gdx.files.internal("icons/exit.png")));

        scoreStatsImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScoreScreen(game));
                dispose();
            }
        });

        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BasterScreen(game));
                dispose();
            }
        });

        exitImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
                Gdx.app.exit();
            }
        });


    }

    private void setTable() {

        mainTable = new Table();
        mainTable.center();
        mainTable.setFillParent(true);
        mainTable.add(playImg)
                .padBottom(30)
                .width(Value.percentWidth(.40F, mainTable))
                .height(Value.percentHeight(.23F, mainTable));
        mainTable.row();
        mainTable.add(exitImg)
                .padTop(50)
                .width(Value.percentWidth(.20F, mainTable))
                .height(Value.percentHeight(.10F, mainTable));

        stage.addActor(mainTable);
    }

}

