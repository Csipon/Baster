package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.controller.BackgroundController;
import com.team.baster.controller.BlockController;
import com.team.baster.controller.HeroController;
import com.team.baster.controller.ParatrooperController;
import com.team.baster.controller.ScoreController;
import com.team.baster.domain.BasterGame;
import com.team.baster.generator.UnitGeneration;
import com.team.baster.model.Burger;
import com.team.baster.model.DynamicBlock;
import com.team.baster.model.HorizBlock;
import com.team.baster.model.Pill;
import com.team.baster.model.VertBlock;

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

    OrthographicCamera camera;
    Texture blockImg;
    Texture blockVertImg;
    Texture tubeTopImg;
    Texture tubeBodyImg;
    Texture tubeBotImg;
    Texture airplaneLeftImg;
    Texture airplaneRightImg;
    Texture backgroundImg;
    Texture topNavImg;
    Texture paratrooperImg;

    ShapeRenderer shapeRenderer;

    ParatrooperController paratrooperController;
    HeroController heroController;
    ScoreController scoreController;
    BlockController blockController;
    BackgroundController backgroundController;

    private int speed = DEFAULT_SPEED;
    private long startDate;
    private Texture burgerImg;
    private Texture pillImg;

    private ParticleEffect particleEffect;


    public BasterScreen(BasterGame game) {
        this.game       = game;
        startDate       = TimeUtils.nanoTime();
        initCamera();
        initTexture();
        initObjects();
        backgroundController.dropStartBackground();
        blockController.dropItem();
    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.batch.begin();
        drawBackground();
        drawHero();
        drawBlocks();
        drawParatrooper();
        drawNavBar();
        drawScoreCounter();
        particleEffect.draw(game.batch, delta);

//        shapeRenderer.setColor(0, 1, 0, 1);
//        shapeRenderer.circle(heroController.circleHead.x, heroController.circleHead.y, heroController.circleHead.radius);
//        shapeRenderer.circle(heroController.circleBody.x, heroController.circleBody.y, heroController.circleBody.radius);
        game.batch.end();
//        shapeRenderer.end();

        calculateSpeed();
        blockController.controlItemsPosition(heroController.circleHead, heroController.circleBody, speed, scoreController.getScore(), 0, particleEffect);
        heroController.resizeHero();
        scoreController.calculateScore(speed);
        backgroundController.checkLasDropBackground();
        backgroundController.controlBackgroundPosition(speed);
        heroController.controlHeroInput();
        blockController.checkLasDropItemTime();
        heroController.controlHeroPosition();
        paratrooperController.controlGenerated();
    }

    @Override
    public void
    resize(int width, int height) {

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
        blockVertImg.dispose();
        tubeTopImg.dispose();
        tubeBodyImg.dispose();
        tubeBotImg.dispose();
        airplaneLeftImg.dispose();
        airplaneRightImg.dispose();
        backgroundImg.dispose();
        topNavImg.dispose();
        paratrooperImg.dispose();
    }

    @Override
    public void show() {

    }


    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    }

    private void initTexture() {
        topNavImg = new Texture("Test.png");
        if (WORLD_WIDTH == 720) {
            airplaneLeftImg     = new Texture("air_left.png");
            airplaneRightImg    = new Texture("air_right.png");
            burgerImg           = new Texture("burger.png");
            pillImg             = new Texture("pills.png");
            backgroundImg       = new Texture("bg_sky.jpg");
            blockImg            = new Texture("block.jpg");
            tubeTopImg          = new Texture("mario_tube_top.png");
            tubeBodyImg         = new Texture("mario_tube_body.png");
            tubeBotImg          = new Texture("mario_tube_bot.png");
            paratrooperImg      = new Texture("paratrooper_720.png");
        }
    }

    private void drawScoreCounter() {
        String strScore = "Score " + scoreController.getScore();
        game.customFont.draw(game.batch, strScore, 0, WORLD_HEIGHT - 10);

    }

    private void drawParatrooper(){
        if (paratrooperController.isFly){
            game.batch.draw(paratrooperImg,
                    paratrooperController.paratrooper.body.x,
                    paratrooperController.paratrooper.body.y,
                    paratrooperController.paratrooper.body.width,
                    paratrooperController.paratrooper.body.height
            );
        }
    }

    private void drawNavBar() {
        game.batch.draw(topNavImg, 0, WORLD_HEIGHT - 55);
    }

    private void drawHero() {
//        particleEffect.setPosition(heroController.hero.x + heroController.hero.width/2, heroController.hero.y + heroController.hero.height);
//        particleEffect.start();
        game.batch.draw(heroController.heroTexture,
                heroController.hero.x,
                heroController.hero.y,
                heroController.currentHeroWidth,
                heroController.currentHeroHeight
        );
    }

    private void drawBackground() {
        for (Rectangle rectangle : backgroundController.background) {
            game.batch.draw(backgroundImg,
                    rectangle.x,
                    rectangle.y,
                    rectangle.width,
                    rectangle.height
            );
        }
    }

    private void initObjects() {
        particleEffect          = new ParticleEffect();
        shapeRenderer           = new ShapeRenderer();
        heroController          = new HeroController();
        paratrooperController   = new ParatrooperController();
        scoreController         = new ScoreController();
        blockController         = new BlockController(game, heroController, paratrooperController);
        backgroundController    = new BackgroundController(backgroundImg);
        particleEffect.load(Gdx.files.internal("particles/testEffect.p"), Gdx.files.internal("particles"));
    }

    private void drawBlocks() {
        for (UnitGeneration unit : blockController.units){
            for (Rectangle rectangle : unit.blocks) {
                if (rectangle instanceof DynamicBlock) {
                    if (((DynamicBlock) rectangle).isLeft()){
                        game.batch.draw(airplaneLeftImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    }else {
                        game.batch.draw(airplaneRightImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    }
                } else if (rectangle instanceof HorizBlock){
                    game.batch.draw(blockImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                }else if (rectangle instanceof VertBlock){
                    if (((VertBlock) rectangle).isTop){
                        game.batch.draw(tubeTopImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    }else if (((VertBlock) rectangle).isBody){
                        game.batch.draw(tubeBodyImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    }else if (((VertBlock) rectangle).isBot){
                        game.batch.draw(tubeBotImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    }
                }
            }
            for (Rectangle rectangle : unit.actionItems){
                if (rectangle instanceof Pill){
                    game.batch.draw(pillImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                }else if (rectangle instanceof Burger){
                    game.batch.draw(burgerImg, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                }
            }

        }
    }

    private void calculateSpeed() {
        long period = TimeUtils.nanoTime() - startDate;

        if (period / PERIOD_ACCELERATION > 1) {
            startDate  = TimeUtils.nanoTime();
            int result = (int) (speed + (DEFAULT_SPEED * PART_ACCELERATION));
            if (result < DEFAULT_SPEED * 5) {
                speed  = result;
            }
        }
    }
}
