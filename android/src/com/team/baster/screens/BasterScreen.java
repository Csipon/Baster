package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.controller.BackgroundController;
import com.team.baster.controller.BlockController;
import com.team.baster.controller.CoinController;
import com.team.baster.controller.HeroController;
import com.team.baster.controller.ScoreController;
import com.team.baster.domain.BasterGame;

import static com.team.baster.GameConstants.DEFAULT_SPEED;
import static com.team.baster.GameConstants.PART_ACCELERATION;
import static com.team.baster.GameConstants.PERIOD_ACCELERATION;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/20/2017.
 */

public class BasterScreen implements Screen {

    final BasterGame game;

    private ParticleEffect particleEffect = new ParticleEffect();

    OrthographicCamera camera;
    Texture heroImg;
    Texture blockImg;
    Texture blockVertImg;
    Texture squareImg;
    Texture backgroundImg;
    Texture coinImg;
    Texture topNavImg;



    HeroController heroController;
    CoinController coinController;
    ScoreController scoreController;
    BlockController blockController;
    BackgroundController backgroundController;

    private int speed = DEFAULT_SPEED;
    private long startDate;


    public BasterScreen(BasterGame game) {
        this.game = game;
        startDate = TimeUtils.nanoTime();
        initCamera();
        initTexture();
        initObjects();
        coinController.lastDropCoinTime = startDate;
        backgroundController.dropStartBackground();
        blockController.dropItem();
        coinController.generatePeriod();
    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawBackground();
        drawNavBar();
        drawHero();
        drawItems();
        drawCoins();
        drawScoreCounter();
        drawCoinsCounter();

        particleEffect.draw(game.batch, delta);

        game.batch.end();

        calculateSpeed();
        heroController.resizeHero();
        scoreController.calculateScore(speed);
        backgroundController.checkLasDropBackground();
        backgroundController.controlBackgroundPosition(speed);
        heroController.controlHeroInput();
        blockController.checkLasDropItemTime();
        heroController.controlHeroPosition();
        blockController.controlItemPosition(heroController.heroHead, heroController.heroBody, speed, scoreController.getScore(), coinController.getCoinsCounter());
        coinController.controlCoins(speed);
        coinController.checkCoinGeneration(blockController.blockGenerator.lastDropItem, blockController.blockGenerator.beforeLastDropItem);
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
        heroController.dispose();
        blockImg.dispose();
        squareImg.dispose();
        blockVertImg.dispose();
        backgroundImg.dispose();
        topNavImg.dispose();
    }

    @Override
    public void show() {

    }


    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    }

    private void initTexture() {

        particleEffect.load(Gdx.files.internal("particles/flame.p"), Gdx.files.internal("particles"));

        topNavImg = new Texture("Test.png");
        if (WORLD_WIDTH == 720) {
            backgroundImg = new Texture("bg_sky.jpg");
            blockImg = new Texture("block.jpg");
            blockVertImg = new Texture("block_vertical.jpg");
            squareImg = new Texture("block_square.jpg");
            coinImg = new Texture("coin.png");
        }
    }

    private void drawScoreCounter() {
        String strScore = "Score " + scoreController.getScore();
        game.customFont.draw(game.batch, strScore, 0, WORLD_HEIGHT - 10);

    }

    private void drawCoinsCounter() {
        String strCoins = "Coins " + coinController.getCoinsCounter();
        game.customFont.draw(game.batch, strCoins, 250, WORLD_HEIGHT - 10);
    }


    private void drawNavBar() {
        game.batch.draw(topNavImg, 0, WORLD_HEIGHT - 55);
    }

    private void drawHero() {
        particleEffect.setPosition(heroController.hero.x + HERO_WIDTH/2, heroController.hero.y + HERO_HEIGHT);
        particleEffect.start();
        game.batch.draw(heroImg, heroController.hero.x, heroController.hero.y);
    }

    private void drawBackground() {
        for (Rectangle rectangle : backgroundController.background) {
            game.batch.draw(backgroundImg, rectangle.x, rectangle.y);
        }
    }

    private void drawCoins() {
        for (Rectangle rectangle : coinController.coins) {
            game.batch.draw(coinImg, rectangle.x, rectangle.y);
        }
    }

    private void initObjects() {
        heroController = new HeroController();
        coinController = new CoinController(heroController);
        scoreController = new ScoreController();
        blockController = new BlockController(game);
        backgroundController = new BackgroundController(backgroundImg);
    }

    private void drawItems() {
        for (Rectangle rectangle : blockController.blockGenerator.getBlocks()) {
            if (rectangle.width > rectangle.height) {
                game.batch.draw(blockImg, rectangle.x, rectangle.y);
            } else {
                game.batch.draw(blockVertImg, rectangle.x, rectangle.y);
            }
        }
        for (Rectangle rectangle : blockController.blockGenerator.getSquare()) {
            game.batch.draw(squareImg, rectangle.x, rectangle.y);
        }
    }

    private int calculateSpeed() {
        long period = TimeUtils.nanoTime() - startDate;

        if (period / PERIOD_ACCELERATION > 1) {
            startDate = TimeUtils.nanoTime();
            int result = (int) (speed + (DEFAULT_SPEED * PART_ACCELERATION));
            if (result < DEFAULT_SPEED * 5) {
                speed = result;
            }
        }
        return speed;
    }
}
