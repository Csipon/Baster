package com.team.baster.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.BasterGame;
import com.team.baster.screen.menu.MenuScreen;

import java.util.Iterator;

import static com.team.baster.GameConstants.*;

/**
 * Created by Pasha on 10/20/2017.
 */

public class BasterScreen implements Screen{

    final BasterGame game;

    OrthographicCamera camera;
    Texture heroImg;
    Texture bathImg;

    Rectangle hero;
    Array<Rectangle> items;

    Vector3 touchPos;

    private long lastDropTime;
    private long startDate;
    private int speed = DEFAULT_SPEED;
    public BasterScreen(BasterGame game) {
        this.game = game;

        initCamera();
        initObjects();
        initTexture();
        dropItem();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(heroImg, hero.x, hero.y);
        drawAllItems();

        game.batch.end();

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
    }

    @Override
    public void show() {
    }


    private void initCamera(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    }
    private void initTexture(){
        heroImg = new Texture("shero.png");
        bathImg = new Texture("lego.jpg");
    }

    private void initObjects(){
        touchPos = new Vector3();
        hero = new Rectangle();
        configHero();
        items = new Array<Rectangle>();
    }


    private void configHero(){
        hero.x = WORLD_WIDTH / 2 - HERO_WIDTH / 2;
        hero.y = WORLD_HEIGHT - 20 - HERO_HEIGHT;
        hero.width = HERO_WIDTH;
        hero.height = HERO_HEIGHT;
    }

    private void dropItem(){
        Rectangle item = new Rectangle();
        item.x = MathUtils.random(0, WORLD_WIDTH - 192);
        item.y = 0;
        item.width = ITEM_WIDTH;
        item.height = ITEM_HEIGHT;
        items.add(item);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void drawAllItems(){
        for (Rectangle rectangle : items){
            game.batch.draw(bathImg, rectangle.x, rectangle.y);
        }
    }

    private void controlHeroInput(){
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            hero.x = (int) (touchPos.x -64 / 2);
        }
    }

    private void controlHeroPosition(){
        if (hero.x < 0) hero.x = 0;
        if (hero.x > WORLD_WIDTH - 64) hero.x = WORLD_WIDTH - 64;
    }

    private void checkLasDropTime(){
        if (TimeUtils.nanoTime() - lastDropTime > 1500000000) {
            dropItem();
        }
    }


    private void controlItemPosition(){
        Iterator<Rectangle> iter = items.iterator();
        while (iter.hasNext()){
            Rectangle item = iter.next();
            item.y += calculateSpeed() * Gdx.graphics.getDeltaTime();
            if (item.y + ITEM_HEIGHT > WORLD_HEIGHT + ITEM_HEIGHT) {
                iter.remove();
            }
            checkHeroCollision(item);
        }
    }

    private void checkHeroCollision(Rectangle item){
        if (item.overlaps(hero)){
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }


    private int calculateSpeed(){
        long period = TimeUtils.nanoTime() - startDate;
        if (period / PERIOD_ACCELERATION > 1) {
            int result = (int) (speed + (DEFAULT_SPEED * PART_ACCELERATION));
            if (result > DEFAULT_SPEED * 2){
                return speed;
            }
            return result;
        }
        return speed;
    }
}
