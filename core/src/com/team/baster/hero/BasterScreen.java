package com.team.baster.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.BasterGame;
import com.team.baster.GameConstants;
import com.team.baster.screen.menu.MenuScreen;

import java.util.Iterator;

import static com.team.baster.GameConstants.DEFAULT_SPEED;
import static com.team.baster.GameConstants.HERO_HEIGHT;
import static com.team.baster.GameConstants.HERO_WIDTH;
import static com.team.baster.GameConstants.ITEM_HEIGHT;
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

    private static Sprite backgroundSprite;

    Rectangle hero;
    Rectangle lastDropItem;
    Rectangle lastDropBack;
    Array<Rectangle> items;
    Array<Rectangle> background;
    DropItemCalculator dropItemCalculator;

    Vector3 touchPos;

    private long score;
    private int speed = DEFAULT_SPEED;
    private long startDate;

    public BasterScreen(BasterGame game) {
        this.game = game;

        startDate = TimeUtils.nanoTime();
        initCamera();
        initTexture();
        initObjects();
        dropBackground();
        dropItem();

        System.out.println("WIDTH > " + WORLD_WIDTH);
        System.out.println("HEIGHT > " + WORLD_HEIGHT);
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawBackground();
        drawHero();
        drawAllItems();
        drawScore();
        game.batch.end();

        checkLasDropBackground();
        controlBackgroundPosition();
        controlHeroInput();
        controlHeroPosition();
        checkLasDropTime();
        controlItemPosition();
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
        if (WORLD_WIDTH == 720 && WORLD_HEIGHT == 1280) {
            backgroundImg = new Texture("background_720x1280.jpg");
        }
        backgroundSprite = new Sprite(backgroundImg);
    }


    private void drawScore() {
        game.font.draw(game.batch, "Score: " + score, 0, GameConstants.WORLD_HEIGHT - 10);
    }

    private void drawHero() {
        game.batch.draw(heroImg, hero.x, hero.y);
    }

    private void drawBackground() {
//        backgroundSprite.draw(game.batch);
        for (Rectangle rectangle : background){
            game.batch.draw(backgroundImg, rectangle.x, rectangle.y);
        }
    }

    private void dropBackground() {
        Rectangle back = new Rectangle();
        back.x = 0;
        if (lastDropBack != null){
            back.y = -WORLD_HEIGHT;
        }else {
            back.y = 0;
        }
        back.width = WORLD_WIDTH;
        back.height = WORLD_HEIGHT;
        background.add(back);
        lastDropBack = back;
    }

    private void initObjects() {
        touchPos = new Vector3();
        hero = new Rectangle();
        configHero();
        items = new Array<>();
        background = new Array<>();
        dropItemCalculator = new DropItemCalculator();
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

    private void drawAllItems() {
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
                hero.x = (int) (touchPos.x - 64 / 2);
            }
        }
    }

    private void controlHeroPosition() {
        if (hero.x < 0) hero.x = 0;
        if (hero.x > WORLD_WIDTH - HERO_WIDTH) hero.x = WORLD_WIDTH - HERO_WIDTH;
    }

    private void checkLasDropTime() {
        if (lastDropItem.y > WORLD_HEIGHT / 2) {
            dropItem();
        }
    }

    private void checkLasDropBackground() {
        if (lastDropBack.y >= 0) {
            dropBackground();
        }
    }


    private void controlItemPosition() {
        Iterator<Rectangle> iter = items.iterator();
        while (iter.hasNext()) {
            Rectangle item = iter.next();
            item.y += calculateSpeed() * Gdx.graphics.getDeltaTime();
            if (item.y + ITEM_HEIGHT > WORLD_HEIGHT + ITEM_HEIGHT) {
                iter.remove();
            }
            checkHeroCollision(item);
        }
    }

    private void checkHeroCollision(Rectangle item) {
        if (item.overlaps(hero)) {
            game.setScreen(new MenuScreen(game));
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
}
