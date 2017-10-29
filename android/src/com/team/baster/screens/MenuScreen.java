package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.domain.BasterGame;
import com.team.baster.font.FontGenerator;
import com.team.baster.storage.PlayerStatusStorage;
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
        viewport =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();
        viewport.apply();


        stage = new Stage(viewport, batch);
        scoreStorage = new ScoreStorage();
        fontGenerator = new FontGenerator();
    }


    @Override
    public void show() {
        scores = scoreStorage.readLastBestScore();
        int coins = PlayerStatusStorage.actualCoins;
        int scores = PlayerStatusStorage.overallScore;

        System.out.println("COINS = " + coins);
        System.out.println("SCORES = " + scores);
        Gdx.input.setInputProcessor(stage);

        setNavigation();
        setImage();
        setTable();

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

    public void setNavigation() {

        Label.LabelStyle labelStyle = new Label.LabelStyle(fontGenerator.generateFont("fonts/GoodDog.otf"), Color.WHITE);
        Label labelScore = new Label("0", labelStyle);
        if(scores.size != 0) {
            String strScore = scores.get(0).toString();
            labelScore = new Label(strScore, labelStyle);
            labelScore.setX(130);
            labelScore.setY(WORLD_HEIGHT - 100);
            stage.addActor(labelScore);
        }


        scoreStatsImg = new Image(new Texture(Gdx.files.internal("icons/score.png")));
        scoreStatsImg.setY(WORLD_HEIGHT - 130);
        scoreStatsImg.setX(5);

        scoreStatsImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScoreScreen(game));
                dispose();
            }
        });

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

