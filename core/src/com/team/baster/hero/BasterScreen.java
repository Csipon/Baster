package com.team.baster.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.BasterGame;
import com.team.baster.GameConstants;
import com.team.baster.screen.menu.GameOverScreen;

import java.util.Iterator;

import static com.team.baster.GameConstants.COIN_SIDE;
import static com.team.baster.GameConstants.DEFAULT_SPEED;
import static com.team.baster.GameConstants.HERO_HEIGHT;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.ITEM_HEIGHT;
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
    Texture bathImg;
    Texture backgroundImg;
    Texture coinImg;
    Texture topNavImg;


    Rectangle hero;
    Rectangle lastDropItem;
    Rectangle lastDropBack;
    Array<Rectangle> items;
    Array<Rectangle> background;
    Array<Rectangle> coins;
    DropItemCalculator dropItemCalculator;
    CoinGenerator coinGenerator;

    Vector3 touchPos;

    private long score;
    private long coinsCounter;
    private int speed = DEFAULT_SPEED;
    private long startDate;
    private long lastDropCoinTime;
    private long periodCoinDrop;

    public BasterScreen(BasterGame game) {
        this.game = game;

        startDate = TimeUtils.nanoTime();
        lastDropCoinTime = startDate;
        initCamera();
        initTexture();
        initObjects();
        dropStartBackground();
        dropItem();
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
        checkLasDropBackground();
        controlBackgroundPosition();
        controlHeroInput();
        checkLasDropItemTime();
        controlHeroPosition();
        controlItemPosition();
        checkCoinGeneration();
        controlCoins();
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
        bathImg.dispose();
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
        heroImg = new Texture("hero.png");
        bathImg = new Texture("lego.jpg");
        coinImg = new Texture("coin.png");
        topNavImg = new Texture("Test.png");
        if (WORLD_WIDTH == 720 && WORLD_HEIGHT == 1280) {
//            backgroundImg = new Texture("background_720x1280.jpg");
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
        for (Rectangle rectangle : background) {
            game.batch.draw(backgroundImg, rectangle.x, rectangle.y);
        }
    }

    private void drawCoins() {
        for (Rectangle rectangle : coins) {
            game.batch.draw(coinImg, rectangle.x, rectangle.y);
        }
    }

    private void dropBackground() {
        Rectangle back = new Rectangle();
        back.x = 0;
        if (lastDropBack != null) {
            back.y = lastDropBack.y - backgroundImg.getHeight();
        } else {
            back.y = WORLD_HEIGHT - backgroundImg.getHeight();
        }
        back.width = backgroundImg.getWidth();
        back.height = backgroundImg.getHeight();

        background.add(back);
        lastDropBack = back;
    }

    private void initObjects() {
        touchPos = new Vector3();
        hero = new Rectangle();
        configHero();
        items = new Array<>();
        background = new Array<>();
        coins = new Array<>();

        dropItemCalculator = new DropItemCalculator();
        coinGenerator = new CoinGenerator();
    }


    private void configHero() {
        hero.x = WORLD_WIDTH / 2 - HERO_WIDTH / 2;
        hero.y = WORLD_HEIGHT - (WORLD_HEIGHT / 5) - HERO_HEIGHT;
        hero.width = HERO_WIDTH;
        hero.height = HERO_HEIGHT;
    }

    private void dropItem() {
        Rectangle item = dropItemCalculator.generateItem(lastDropItem);
        items.add(item);
        score += 5;
        lastDropItem = item;
    }

    private void drawItems() {
        for (Rectangle rectangle : items) {
            game.batch.draw(bathImg, rectangle.x, rectangle.y);
        }
    }

    private void controlHeroInput() {
        if (Gdx.input.isTouched()) {
            int inputX = Gdx.input.getX();
            if (inputX >= hero.x && inputX <= hero.x + hero.width) {
                touchPos.set(inputX, Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                hero.x = (int) (touchPos.x - hero.width / 2);
            }
        }
    }

    private void controlHeroPosition() {
        if (hero.x < 0) hero.x = 0;
        if (hero.x > WORLD_WIDTH - HERO_WIDTH) hero.x = WORLD_WIDTH - HERO_WIDTH;
    }

    private void checkLasDropItemTime() {
        if (lastDropItem.y > WORLD_HEIGHT / 2) {
            dropItem();
        }
    }

    private void checkLasDropBackground() {
        if (lastDropBack.y >= -2) {
            dropBackground();
        }
    }


    private void controlItemPosition() {
        Iterator<Rectangle> iter = items.iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += speed * Gdx.graphics.getDeltaTime();
            if (item.y + ITEM_HEIGHT > WORLD_HEIGHT + ITEM_HEIGHT) {
                iter.remove();
            }
            checkHeroCollision(item);
        }
    }

    private void checkHeroCollision(Rectangle item) {
        if (item.overlaps(hero)) {
            game.setScreen(new GameOverScreen(game, score));
            dispose();
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

    private void controlBackgroundPosition() {
        Iterator<Rectangle> iter = background.iterator();
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
            coins.add(coinGenerator.generateCoin(lastDropItem));
            generatePeriod();
            lastDropCoinTime = TimeUtils.nanoTime();
        }
    }

    private void controlCoins() {
        Iterator<Rectangle> iter = coins.iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += speed * Gdx.graphics.getDeltaTime();
            if (item.y + COIN_SIDE > WORLD_HEIGHT + COIN_SIDE || checkCoinCollisions(item)) {
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

    private void dropStartBackground(){
        for (int counter = 1; true; counter++){
            if (counter * backgroundImg.getHeight() <= WORLD_HEIGHT){
                dropBackground();
            }else {
                dropBackground();
                break;
            }
        }
    }

}
