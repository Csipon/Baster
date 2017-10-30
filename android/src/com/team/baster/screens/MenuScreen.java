package com.team.baster.screens;

import android.widget.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.domain.BasterGame;
import com.team.baster.style.button.ButtonStyleGenerator;
import com.team.baster.style.font.FontGenerator;
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
    private PlayerStatusStorage playerStatusStorage;
    private FontGenerator fontGenerator;
    private ButtonStyleGenerator buttonStyleGenerator;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Table mainTable;
    private ImageButton playImg;
    private Image exitImg;
    private Image scoreStatsImg;

    private Integer coins;
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
        buttonStyleGenerator = new ButtonStyleGenerator();
        playerStatusStorage = PlayerStatusStorage.getInstance();
        fontGenerator = new FontGenerator();
        playerStatusStorage.readPlayerStatus();
    }


    @Override
    public void show() {
        scores = scoreStorage.readLastBestScore();

        int scores = PlayerStatusStorage.overallScore;
        coins = PlayerStatusStorage.actualCoins;

        System.out.println("COINS = " + coins);
        System.out.println("SCORES = " + scores);
        Gdx.input.setInputProcessor(stage);

        setNavigation();
        setImage();
        setTable();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
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
        stage.dispose();

    }

    public void setNavigation() {

        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/bg.png"))));

        Label.LabelStyle labelStyle = fontGenerator.getLabelStyle72();
        Label.LabelStyle labelStyleYellow = fontGenerator.getLabelStyle72Yellow();

        Label labelScore = new Label("0", labelStyle);
        Label labelCoins = new Label("0", labelStyleYellow);
        if(scores.size != 0) {
            String strScore = scores.get(0).toString();
            labelScore = new Label(strScore, labelStyle);
        }
        if(coins != null) {
            labelCoins = new Label(coins.toString(), labelStyleYellow);
        }

        ImageButton scoreImgButton = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/score.png", 7,7));
        Table scoreTable = new Table();
        scoreTable.setPosition(-10, WORLD_HEIGHT - 150);
        scoreTable.setSize(scoreImgButton.getWidth() + labelScore.getWidth() + 60, 180);
        scoreTable.setBackground(bg);
        scoreTable.add(scoreImgButton).center();
        scoreTable.add(labelScore).center();

        ImageButton coinsImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/coins.png", 7, 7));
        Table coinsTable = new Table();
        coinsTable.setPosition(0, WORLD_HEIGHT - 270);
        coinsTable.setSize(coinsImgBtn.getWidth() + labelCoins.getWidth() + 50, 150);
        coinsTable.setBackground(bg);
        coinsTable.add(coinsImgBtn).center();
        coinsTable.add(labelCoins).padLeft(10).center();


        ImageButton marketImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/shop.png", 7, 7));
        Table marketTable = new Table();
        marketTable.setPosition(WORLD_WIDTH - 180, WORLD_HEIGHT - 150);
        marketTable.setSize(marketImgBtn.getWidth() + 60, 180);
        marketTable.setBackground(bg);
        marketTable.add(marketImgBtn).center();

        ImageButton achieveImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/quality.png", 7, 7));
        ImageButton rankImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/ranking.png", 7, 7));

        Table bottomBar = new Table();
        bottomBar.setPosition(-50, -15);
        bottomBar.setSize(WORLD_WIDTH + 130, 170);
        bottomBar.setBackground(bg);

        bottomBar.add(achieveImgBtn).size(90, 90);
        bottomBar.add(rankImgBtn).size(90, 90);


        scoreImgButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScoreScreen(game));
                dispose();
            }
        });


        stage.addActor(coinsTable);
        stage.addActor(scoreTable);
        stage.addActor(marketTable);
        stage.addActor(bottomBar);
    }

    private void setImage() {
        playImg = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/start.png", 15, 15));
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
                .padBottom(10)
                .size(300, 300);
        mainTable.row();
        mainTable.add(exitImg)
                .padTop(50)
                .size(150, 150);

        stage.addActor(mainTable);
    }

}

