package com.team.baster.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    private final BasterGame game;

    private OrthographicCamera camera;
    private Texture blockImg;
    private Texture tubeTopImg;
    private Texture tubeBodyImg;
    private Texture tubeBotImg;
    private Texture airplaneLeftImg;
    private Texture airplaneRightImg;
    private Texture backgroundImg;
    private Texture paratrooperImg;
    private TextureAtlas rickAtlas;

    private ShapeRenderer shapeRenderer;

    private ParatrooperController paratrooperController;
    private HeroController heroController;
    private ScoreController scoreController;
    private BlockController blockController;
    private BackgroundController backgroundController;

    private int speed = DEFAULT_SPEED;
    private long startDate;
    private Texture burgerImg;
    private Texture pillImg;

    private ParticleEffect particleEffect;
//    private Animation<TextureAtlas.AtlasRegion> rickAnimation;
    private float timePassed = 0;


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
        timePassed += Gdx.graphics.getDeltaTime();
        game.batch.begin();
        drawBackground();
        drawHero();
        drawBlocks();
        drawParatrooper();
        drawScoreCounter();
        drawRick();
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
        tubeTopImg.dispose();
        tubeBodyImg.dispose();
        tubeBotImg.dispose();
        airplaneLeftImg.dispose();
        airplaneRightImg.dispose();
        backgroundImg.dispose();
        paratrooperImg.dispose();
        rickAtlas.dispose();
    }

    @Override
    public void show() {

    }


    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    }

    @SuppressWarnings("uncheced")
    private void initTexture() {
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
        rickAtlas           = new TextureAtlas(Gdx.files.internal("rick/rick.atlas"));
//        rickAnimation       = new Animation<>(1/10f, rickAtlas.getRegions());
    }

    private void drawScoreCounter() {
        String strScore = String.format("Score %s", scoreController.getScore());
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

    private void drawRick(){
//        game.batch.draw(rickAnimation.getKeyFrame(timePassed, true), 100, 300, RICK_WIDTH, RICK_HEIGHT);
    }

    private void drawHero() {
        game.batch.draw(heroController.heroAnimation.getKeyFrame(timePassed, true),
                heroController.hero.x,
                heroController.hero.y,
                heroController.currentHeroWidth,
                heroController.currentHeroHeight
        );
    }

    private void drawBackground() {
        backgroundController.background.forEach((bg) -> game.batch.draw(backgroundImg, bg.x, bg.y, bg.width, bg.height));
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
        blockController.units.forEach(unit -> {
            unit.blocks.forEach(block -> {
                if (block instanceof DynamicBlock) {
                    if (((DynamicBlock) block).isLeft()){
                        game.batch.draw(airplaneLeftImg, block.x, block.y, block.width, block.height);
                    }else {
                        game.batch.draw(airplaneRightImg, block.x, block.y, block.width, block.height);
                    }
                } else if (block instanceof HorizBlock){
                    game.batch.draw(blockImg, block.x, block.y, block.width, block.height);
                }else if (block instanceof VertBlock){
                    if (((VertBlock) block).isTop){
                        game.batch.draw(tubeTopImg, block.x, block.y, block.width, block.height);
                    }else if (((VertBlock) block).isBody){
                        game.batch.draw(tubeBodyImg, block.x, block.y, block.width, block.height);
                    }else if (((VertBlock) block).isBot){
                        game.batch.draw(tubeBotImg, block.x, block.y, block.width, block.height);
                    }
                }
            });
            unit.actionItems.forEach( actyionItem -> {
                if (actyionItem instanceof Pill){
                    game.batch.draw(pillImg, actyionItem.x, actyionItem.y, actyionItem.width, actyionItem.height);
                }else if (actyionItem instanceof Burger){
                    game.batch.draw(burgerImg, actyionItem.x, actyionItem.y, actyionItem.width, actyionItem.height);
                }
            });
        });
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
