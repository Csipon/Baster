package com.team.baster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.baster.domain.BasterGame;
import com.team.baster.style.ScrollPagePane;
import com.team.baster.style.button.ButtonStyleGenerator;
import com.team.baster.style.font.FontGenerator;

import static com.team.baster.GameConstants.WORLD_HEIGHT;
import static com.team.baster.GameConstants.WORLD_WIDTH;

/**
 * Created by Smeet on 04.11.2017.
 */

public class StoreScreen implements Screen {
    private static final int ITEM_COUNT = 3;
    private static final int ROW_COUNT = 2;


    private BasterGame game;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private FontGenerator fontGenerator;
    private ButtonStyleGenerator buttonStyleGenerator;
    private Label.LabelStyle styleTitle;
    private Label.LabelStyle styleDescribe;

    private ImageButton parashute;
    private ImageButton eat;
    private ImageButton imgButtonMenu;
    private ImageButton money;
    private ImageButton buy;
    private ScrollPagePane scroller;
    private ScrollPagePane scrollerHidden;
    private Table table;
    private Table tableHidden;
    private Table sideNavTable;
    private Table statusBar;
    private Table busterTable;

    private Texture naviPassive;
    private Texture naviActive;
    private Image devider;


    private TextureRegion mainBg;
    private TextureRegionDrawable bg;
    private TextureRegionDrawable sideNavBg;


    public StoreScreen(BasterGame game) {
        initSetting();
        this.game   = game;
        stage       = new Stage(viewport, game.batch);

        initObj();
        initTexture();

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        setImgButton();
        addSideNav();
        addStatusBar();

        createScrollMarket(true);
        createScrollMarket(false);
    }


    @Override
    public void render(float delta) {

        stage.act(delta);

        stage.getBatch().begin();
        stage.getBatch().draw(mainBg, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        addPageSlider();
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
    }

    private void createScrollMarket(boolean visible) {
        Table table;
        ScrollPagePane scroller;
        if (visible){
            table = this.table;
            scroller = this.scroller;
            scroller.setPageSpacing(20);
        }else {
            table = tableHidden;
            scroller = scrollerHidden;
        }
        stage.addActor(table);
        table.setPosition(WORLD_WIDTH/2 - 350, WORLD_HEIGHT/2 - 350);
        table.setSize(WORLD_WIDTH/2 + 350, WORLD_HEIGHT/2 + 150);
        scroller.setFlingTime(0.3f);
        scroller.setFadeScrollBars(false);


        for (int i = 0; i < ITEM_COUNT; i++) {
            Table items = new Table();
            for (int y = 0; y < ROW_COUNT; y++) {
                items.row();
                for (int x = 0; x < ITEM_COUNT; x++) {
                    items.add(addBusterContent()).size(WORLD_WIDTH/2 - 50, WORLD_HEIGHT/2 - 300).pad(25,15,20,30);
                }
            }
            scroller.addPage(items);
        }
        table.add(scroller).expand().fill();
        table.setVisible(visible);
    }

    public void setImgButton() {

        parashute       = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/parashute.png", 7, 7));
        eat             = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/buffet.png", 7, 7));
        money           = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/rich.png", 7, 7));
        buy             = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/buy.png", 7, 7));
        imgButtonMenu   = new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/back-arrow.png", 7, 7));
        devider         = new Image(new Texture(Gdx.files.internal("icons/devider.png")));

        parashute.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Got press");
                table.setVisible(true);
                tableHidden.setVisible(false);
                System.out.println("Do logic");

            }
        });

        eat.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.setVisible(false);
                tableHidden.setVisible(true);

            }
        });

        imgButtonMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen());
                dispose();

            }
        });
    }

    public ClickListener levelClickListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
            System.out.println("Click: " + event.getListenerActor().getName());
        }
    };

    private Table addBusterContent() {
        busterTable = new Table();
        busterTable.center().padTop(25).top();
        busterTable.setBackground(bg);
        busterTable.add(new Label("Item", styleTitle));
        busterTable.row();
        busterTable.add(new ImageButton(buttonStyleGenerator.generateButtonStyle("items/helper.png", 7, 7))).size(100, 100);
        busterTable.row();
        Label label = new Label("Some describe must be here, just imagine it", styleDescribe);
        label.setWrap(true);
        label.setAlignment(Align.center);
        busterTable.add(label).width(260).padTop(10);
        busterTable.row();
        busterTable.add(new ImageButton(buttonStyleGenerator.generateButtonStyle("icons/buy.png", 7, 7))).size(90, 90);
        busterTable.addListener(levelClickListener);


        return busterTable;
    }

    private void addPageSlider() {

        if(table.isVisible()) {
            for(int i = 1; i <= scroller.getSectionsCount(); i++) {
                if ( i == scroller.calculateCurrentSection() ) {
                    stage.getBatch().draw( naviActive, WORLD_WIDTH/2 - scroller.getSectionsCount()*20/2 + i*20 , 50);
                }
                else {
                    stage.getBatch().draw( naviPassive, WORLD_WIDTH/2 - scroller.getSectionsCount()*20/2 + i*20 , 50);
                }
            }
        }

        if(tableHidden.isVisible()) {
            for(int i = 1; i <= scrollerHidden.getSectionsCount(); i++) {
                if ( i == scrollerHidden.calculateCurrentSection() ) {
                    stage.getBatch().draw( naviActive, WORLD_WIDTH/2 - scrollerHidden.getSectionsCount()*20/2 + i*20 , 50);
                }
                else {
                    stage.getBatch().draw( naviPassive, WORLD_WIDTH/2 - scrollerHidden.getSectionsCount()*20/2 + i*20 , 50);
                }
            }
        }

    }


    public void addSideNav() {
        sideNavTable = new Table();
        sideNavTable.center();
        sideNavTable.setSize(WORLD_WIDTH - 20, WORLD_HEIGHT/3 - 335 );
        sideNavTable.setPosition(10, WORLD_HEIGHT - 200);
        sideNavTable.setBackground(sideNavBg);
        sideNavTable.add(parashute).size(80, 80).padRight(15);
        sideNavTable.add(new Image(new Texture(Gdx.files.internal("icons/devider.png")))).size(10, 93).padRight(20);
        sideNavTable.add(eat).size(80, 80).padRight(15);
        sideNavTable.add(new Image(new Texture(Gdx.files.internal("icons/devider.png")))).size(10, 93).padRight(20);
        sideNavTable.add(money).size(80, 80);

        stage.addActor(sideNavTable);
    }

    public void addStatusBar() {

        statusBar = new Table();
        statusBar.center();
        statusBar.setSize(WORLD_WIDTH, WORLD_HEIGHT/3 - 300 );
        statusBar.setPosition(0, WORLD_HEIGHT - 100);
        statusBar.left();
        statusBar.setBackground(sideNavBg);
        statusBar.add(imgButtonMenu).size(90, 90).padLeft(20).padTop(10);

        stage.addActor(statusBar);
    }

    private void initSetting() {
        camera      = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        viewport    =  new ExtendViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);
        viewport.apply();
    }

    private void initTexture() {
        mainBg      = new TextureRegion(new Texture(Gdx.files.internal("icons/main-bg.png")));
        bg          = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/blue-bg.png"))));
        sideNavBg   = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/side-nav-bg.png"))));
        naviPassive = new Texture(Gdx.files.internal("naviPassive.png"));
        naviActive  = new Texture(Gdx.files.internal("naviActive.png"));
    }

    private void initObj() {
        table           = new Table();
        tableHidden     = new Table();
        scroller                = new ScrollPagePane();
        scrollerHidden          = new ScrollPagePane();
        buttonStyleGenerator = new ButtonStyleGenerator();
        fontGenerator        = new FontGenerator();
        styleTitle           = fontGenerator.getStyleTitle();
        styleDescribe        = fontGenerator.getLabelStyle25();
    }

}
