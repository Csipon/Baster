package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.domain.BasterGame;
import com.team.baster.storage.PlayerStatusStorage;
import com.team.baster.storage.ScoreStorage;
import com.team.baster.style.button.ButtonStyleGenerator;
import com.team.baster.style.font.FontGenerator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Smeet on 20.10.2017.
 */

public class MenuScreen implements Screen {

    final BasterGame game;
    private ScoreStorage scoreStorage;
    private PlayerStatusStorage playerStatusStorage;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private FontGenerator fontGenerator;
    private ButtonStyleGenerator buttonStyleGenerator;
    private Image playImg;
    private ImageButton scoreImgButton;
    private ImageButton coinsImgBtn;
    private ImageButton marketImgBtn;
    private ImageButton achieveImgBtn;
    private ImageButton rankImgBtn;
    private TextureRegionDrawable bg;
    private TextureRegion mainBg;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelStyleYellow;
    private Label labelScore;
    private Label labelCoins;

    private Integer coins;
    private Array<Long> scores;

    private ParticleEffect particleEffect = new ParticleEffect();


    public MenuScreen(BasterGame game) {

        initSetting();
        this.game = game;
        stage = new Stage(viewport, game.batch);

        initObj();
        initTexture();

    }


    @Override
    public void show() {
        scores = scoreStorage.readLastBestScore();

        int scores = PlayerStatusStorage.overallScore;
        coins = PlayerStatusStorage.actualCoins;

        Gdx.input.setInputProcessor(stage);

        particleEffect.setPosition(WORLD_WIDTH/2 - 300, WORLD_HEIGHT/2 + 500);
        particleEffect.start();


        setLabel();
        setButtons();
        setNavigation();
        loadListeners();

    }

    @Override
    public void render(float delta) {
        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(mainBg, 1, 1, WORLD_WIDTH, WORLD_HEIGHT);
        particleEffect.update(Gdx.graphics.getDeltaTime());
        particleEffect.draw(game.batch, delta);
        stage.getBatch().end();

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
        particleEffect.dispose();

    }

    private void setLabel() {
        labelStyle = fontGenerator.getLabelStyle72();
        labelStyleYellow = fontGenerator.getLabelStyle72Yellow();

        labelScore = new Label("0", labelStyle);
        labelCoins = new Label("0", labelStyleYellow);
    }

    public void setNavigation() {

        if(scores.size != 0) {
            String strScore = scores.get(0).toString();
            labelScore = new Label(strScore, labelStyle);
        }
        if(coins != null) {
            labelCoins = new Label(coins.toString(), labelStyleYellow);
        }

        Table scoreTable = new Table();
        scoreTable.setPosition(-10, WORLD_HEIGHT - 160);
        scoreTable.setSize(scoreImgButton.getWidth() + labelScore.getWidth() + 50, 180);
        scoreTable.setBackground(bg);
        scoreTable.add(scoreImgButton).center();
        scoreTable.add(labelScore).center();

        Table coinsTable = new Table();
        coinsTable.setPosition(-5, WORLD_HEIGHT - 290);
        coinsTable.setSize(coinsImgBtn.getWidth() + labelCoins.getWidth() + 50, 150);
        coinsTable.setBackground(bg);
        coinsTable.add(coinsImgBtn).center();
        coinsTable.add(labelCoins).padLeft(10).center();


        Table marketTable = new Table();
        marketTable.setPosition(WORLD_WIDTH - 180, WORLD_HEIGHT - 150);
        marketTable.setSize(marketImgBtn.getWidth() + 60, 180);
        marketTable.setBackground(bg);
        marketTable.add(marketImgBtn).center();


        Table bottomBar = new Table();
        bottomBar.setPosition(-15, 0);
        bottomBar.setSize(WORLD_WIDTH + 50, 150);
        bottomBar.setBackground(bg);
        bottomBar.add(achieveImgBtn).size(90, 90).height(140);
        bottomBar.add(rankImgBtn).size(90, 90);

        Table mainTable = new Table();
        mainTable.center();
        mainTable.setFillParent(true);
        mainTable.add(playImg)
                .size(350, 350);

        RepeatAction actionLoop = Actions.forever(Actions.sequence(Actions.scaleTo(1f, 1f, 1f, Interpolation.bounceOut), Actions.scaleTo(1.05f, 1.05f, 1.05f)));
        playImg.addAction(actionLoop);


        stage.addActor(mainTable);
        stage.addActor(coinsTable);
        stage.addActor(scoreTable);
        stage.addActor(marketTable);
        stage.addActor(bottomBar);
    }

    private void setButtons() {

        playImg = new Image(new Texture(Gdx.files.internal("icons/start.png")));
        achieveImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/quality.png", 7, 7));
        rankImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/ranking.png", 7, 7));
        marketImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/shop.png", 7, 7));
        coinsImgBtn = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/coins.png", 7, 7));
        scoreImgButton = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/score.png", 7,7));
    }


    public void loadListeners() {

        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new BasterScreen(game));
            }
        });

        achieveImgBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.showDialog();
            }
        });

        coinsImgBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.showDialog();
            }
        });

        marketImgBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StoreScreen(game));
            }
        });

        rankImgBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.showDialog();
            }
        });

        scoreImgButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.actionResolver.showDialogWithBestScore();
            }
        });
    }

    private void initSetting() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        viewport =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();
    }

    private void initObj() {
        scoreStorage = new ScoreStorage();
        buttonStyleGenerator = new ButtonStyleGenerator();
        playerStatusStorage = PlayerStatusStorage.getInstance();
        fontGenerator = new FontGenerator();
        playerStatusStorage.readPlayerStatus();
    }

    private void initTexture() {
        mainBg = new TextureRegion(new Texture(Gdx.files.internal("icons/main-bg.png")));
        bg = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/bg.9.png"))));
        particleEffect.load(Gdx.files.internal("particles/rain.p"), Gdx.files.internal("particles"));
    }

}

