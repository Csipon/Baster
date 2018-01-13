package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.team.baster.AndroidInstanceHolder;
import com.team.baster.GameConstants;
import com.team.baster.domain.BasterGame;
import com.team.baster.style.button.ButtonStyleGenerator;
import com.team.baster.style.font.FontGenerator;

import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Smeet on 21.10.2017.
 */

public class GameOverScreen implements Screen {

    private BasterGame game;
    private Stage stage;
    private Table mainTable;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private FontGenerator fontGenerator;
    private ButtonStyleGenerator buttonStyleGenerator;

    private Label labelScore;
    private Label labelCoins;
    private Label labelAchievements;
    private Label labelLose;

    private ImageButton imgButtonPlay;
    private ImageButton imgButtonMenu;
    private ImageButton imgButtonAchieve;
    private Image imgScore;
    private Image imgCoins;
    private TextureRegionDrawable bg;

    private int score;
    private int coins;

    public GameOverScreen(BasterGame game, int score, int coins) {
        this.game   = game;
        this.coins  = coins;
        this.score  = score;
        camera      = new OrthographicCamera();
        viewport    =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();

        fontGenerator        = new FontGenerator();
        buttonStyleGenerator = new ButtonStyleGenerator();
        stage                = new Stage(viewport, game.batch);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        bg = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/bg.9.png"))));
        loadLabel();
        loadImgButton();
        loadTable();
        AndroidInstanceHolder.getAdController().setInterstitialCount(GameConstants.INTERSTITIAL_COUNT);
    }

    private void loadLabel() {
        Label.LabelStyle labelStyle         = fontGenerator.getLabelStyle72();
        Label.LabelStyle labelStyleYellow   = fontGenerator.getLabelStyle72Yellow();
        Label.LabelStyle labelStyle35       = fontGenerator.getLabelStyle35();

        labelAchievements                   = new Label("No achievements completed", labelStyle35);
        labelLose                           = new Label("You lose", labelStyle);
        labelScore                          = new Label("" + score, labelStyle);
        labelCoins                          = new Label("+" + coins, labelStyleYellow);
    }

    private void loadTable() {

        Table scoreTable = new Table();
        scoreTable.add(labelScore).padRight(15);
        scoreTable.add(imgScore).size(75, 75);

        Table coinsTable = new Table();
        coinsTable.add(labelCoins).padRight(15);
        coinsTable.add(imgCoins).size(75, 75);

        mainTable = new Table();
        mainTable.setPosition(WORLD_WIDTH/2 - 300, WORLD_HEIGHT/2 - 250);
        mainTable.setSize(600, WORLD_HEIGHT/2 + 100);
        mainTable.setBackground(bg);
        mainTable.add(labelLose).padBottom(40);
        mainTable.row();
        mainTable.add(scoreTable);
        mainTable.row();
        mainTable.add(coinsTable).padTop(10);
        mainTable.row();
        mainTable.add(imgButtonPlay).size(200, 200).padTop(30);

        Table bottomBar = new Table();
        bottomBar.setPosition(0, 100);
        bottomBar.setSize(WORLD_WIDTH, 200);
        bottomBar.setBackground(bg);
        bottomBar.add(imgButtonAchieve).size(100, 100).left();
        bottomBar.add(labelAchievements).padLeft(20).left();

        stage.addActor(imgButtonMenu);
        stage.addActor(bottomBar);
        stage.addActor(mainTable);
    }

    private void loadImgButton() {

        imgButtonPlay = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/start.png", 15, 15));
        imgButtonMenu = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/back-arrow.png", 10, 10));
        imgButtonMenu.setY(WORLD_HEIGHT - 130);
        imgButtonMenu.setX(0);
        imgButtonAchieve = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/quality.png", 7, 7));

        imgCoins = new Image(new Texture(Gdx.files.internal("icons/piggy.png")));
        imgScore = new Image(new Texture(Gdx.files.internal("icons/score.png")));


        imgButtonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BasterScreen(game));
                dispose();
            }
        });


        imgButtonMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        AndroidInstanceHolder.getAdController().showInterstellar();

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
        stage.dispose();
    }
}
