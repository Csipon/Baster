package com.team.baster.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.BasterGame;
import com.team.baster.generator.BackgroundGenerator;
import com.team.baster.generator.BlockGenerator;
import com.team.baster.generator.CoinGenerator;

import java.util.Iterator;

import static com.team.baster.GameConstants.COIN_SIDE;
import static com.team.baster.GameConstants.DEFAULT_SPEED;
import static com.team.baster.GameConstants.HERO_HEIGHT;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.MAX_COIN_GENER_TIME;
import static com.team.baster.GameConstants.MIN_COIN_GENER_TIME;
import static com.team.baster.GameConstants.PART_ACCELERATION;
import static com.team.baster.GameConstants.PERIOD_ACCELERATION;
import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Pasha on 10/20/2017.
 */

public class BasterScreen implements Screen {

    final BasterGame game;

    OrthographicCamera camera;
    Texture heroImg;
    Texture blockImg;
    Texture blockVertImg;
    Texture squareImg;
    Texture backgroundImg;
    Texture coinImg;
    Texture topNavImg;

    BlockGenerator blockGenerator;
    BackgroundGenerator backgroundGenerator;
    Rectangle hero;
    CoinGenerator coinGenerator;

    Vector3 touchPos;

    private long score;
    private long coinsCounter;
    private int speed = DEFAULT_SPEED;
    private long startDate;
    private long lastDropCoinTime;
    private long periodCoinDrop;

    private static final double SPEED_FACTOR = 1;

    public BasterScreen(BasterGame game) {
        this.game = game;

        startDate = TimeUtils.nanoTime();
        lastDropCoinTime = startDate;
        initCamera();
        initTexture();
        initObjects();
        backgroundGenerator.dropStartBackground();
        blockGenerator.dropItem();
        generatePeriod();
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
        game.batch.end();

        calculateSpeed();
        calculateScore();
        backgroundGenerator.checkLasDropBackground();
        controlBackgroundPosition();
        controlHeroInput();
        blockGenerator.checkLasDropItemTime();
        controlHeroPosition();
        blockGenerator.controlItemPosition(hero, speed, score);
        controlCoins();
        checkCoinGeneration();
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
        heroImg.dispose();
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
        heroImg = new Texture("human.png");
        blockImg = new Texture("block.jpg");
        blockVertImg = new Texture("block_vertical.jpg");
        squareImg = new Texture("block_square.jpg");
        coinImg = new Texture("coin.png");
        topNavImg = new Texture("Test.png");
        if (WORLD_WIDTH >= 720) {
            backgroundImg = new Texture("bg.jpg");
        }
    }


    private void drawScoreCounter() {
        String strScore = "Score " + score;
        game.customFont.draw(game.batch, strScore, 0, WORLD_HEIGHT - 10);

    }

    private void drawCoinsCounter() {
        String strCoins = "Coins " + coinsCounter;
        game.customFont.draw(game.batch, strCoins, 250, WORLD_HEIGHT - 10);
    }


    private void drawNavBar() {
        game.batch.draw(topNavImg, 0, WORLD_HEIGHT - 55);
    }

    private void drawHero() {
        game.batch.draw(heroImg, hero.x, hero.y);
    }

    private void drawBackground() {
        for (Rectangle rectangle : backgroundGenerator.getBackground()) {
            game.batch.draw(backgroundImg, rectangle.x, rectangle.y);
        }
    }

    private void drawCoins() {
        for (Rectangle rectangle : coinGenerator.getCoins()) {
            game.batch.draw(coinImg, rectangle.x, rectangle.y);
        }
    }


    private void initObjects() {
        blockGenerator = new BlockGenerator(game);
        backgroundGenerator = new BackgroundGenerator(backgroundImg);
        touchPos = new Vector3();
        hero = new Rectangle();
        configHero();
        coinGenerator = new CoinGenerator();
    }


    private void configHero() {
        hero.x = WORLD_WIDTH / 2 - HERO_WIDTH / 2;
        hero.y = WORLD_HEIGHT - (WORLD_HEIGHT / 5) - HERO_HEIGHT;
        hero.width = HERO_WIDTH;
        hero.height = HERO_HEIGHT;
    }


    private void drawItems() {
        for (Rectangle rectangle : blockGenerator.getBlocks()) {
            if (rectangle.width > rectangle.height) {
                game.batch.draw(blockImg, rectangle.x, rectangle.y);
            } else {
                game.batch.draw(blockVertImg, rectangle.x, rectangle.y);
            }
        }
        for (Rectangle rectangle : blockGenerator.getSquare()) {
            game.batch.draw(squareImg, rectangle.x, rectangle.y);
        }
    }

    private void controlHeroInput() {
        if (Gdx.input.isTouched()) {
            hero.x += Gdx.input.getDeltaX() * SPEED_FACTOR;
        }
    }

    private void controlHeroPosition() {
        if (hero.x < 0) hero.x = 0;
        if (hero.x > WORLD_WIDTH - HERO_WIDTH) hero.x = WORLD_WIDTH - HERO_WIDTH;
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

    private void controlBackgroundPosition() {
        Iterator<Rectangle> iter = backgroundGenerator.getBackground().iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += calculateSpeed() * Gdx.graphics.getDeltaTime();
            if (item.y + WORLD_HEIGHT > WORLD_HEIGHT * 2) {
                iter.remove();
            }
        }
    }

    private void checkCoinGeneration() {
        if (periodCoinDrop < TimeUtils.nanoTime() - lastDropCoinTime) {
            coinGenerator.generateCoin(blockGenerator.getLastDropItem(), blockGenerator.getBeforeLastDropItem());
            generatePeriod();
            lastDropCoinTime = TimeUtils.nanoTime();
        }
        coinGenerator.checkLastCoinBlockCollision(blockGenerator.getLastDropItem(), blockGenerator.getBeforeLastDropItem());
    }


    private void controlCoins() {
        Iterator<Rectangle> iter = coinGenerator.getCoins().iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += speed * Gdx.graphics.getDeltaTime();
            if (checkCoinCollisions(item)) {
                iter.remove();
            }
            if (item.y + COIN_SIDE > WORLD_HEIGHT + COIN_SIDE) {
                iter.remove();
            }
        }
    }

    private boolean checkCoinCollisions(Rectangle item) {
        if (item.overlaps(hero)) {
            coinsCounter += 1;
            return true;
        }
        return false;
    }

    private void generatePeriod() {
        periodCoinDrop = MathUtils.random(MIN_COIN_GENER_TIME, MAX_COIN_GENER_TIME);
    }

    private void calculateScore() {
        score += speed / 200;
    }

}
