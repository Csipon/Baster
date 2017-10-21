package com.team.baster.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.team.baster.GameConstants;

/**
 * Created by Smeet on 21.10.2017.
 */

public class QuitGame {

    public void quitGameConfirm(Stage stage) {
        Skin uiSkin = new Skin(Gdx.files.internal("skin/freezing-ui.json"));
        TextButton btnYes = new TextButton("Yes", uiSkin);
        TextButton btnNo = new TextButton("No", uiSkin);

        Skin skinDialog = new Skin(Gdx.files.internal("skin/freezing-ui.json"));
        final Dialog dialog = new Dialog("", skinDialog) {
            @Override
            public float getPrefWidth() {
                return 700f;
            }

            @Override
            public float getPrefHeight() {
                return 400f;
            }
        };
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);

        btnYes.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                Gdx.app.exit();
                dialog.hide();
                dialog.cancel();
                dialog.remove();
                return true;
            }
        });
//
        btnNo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                //Do whatever here for cancel

                dialog.cancel();
                dialog.hide();

                return true;
            }

        });

        Texture texture = new Texture(Gdx.files.internal("Test.png"));
        TextureRegion myTex = new TextureRegion(texture);
//        myTex.flip(false, true);
        myTex.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Drawable drawable = new TextureRegionDrawable(myTex);
        dialog.setBackground(drawable);

        float btnHeight = 150f;
        float btnWidth = 200f;
        Table t = new Table();


//        dialog.getContentTable().add(label1).padTop(40f);

        t.add(btnYes).width(btnWidth).height(btnHeight);
        t.add(btnNo).width(btnWidth).height(btnHeight);

        dialog.getButtonTable().add(t).center().padBottom(80f);
        dialog.show(stage).setPosition(
                (GameConstants.WORLD_WIDTH / 2) - (720 / 2),
                (GameConstants.WORLD_HEIGHT) - (GameConstants.WORLD_HEIGHT - 40));

        dialog.setName("quitDialog");
        stage.addActor(dialog);

    }
}
