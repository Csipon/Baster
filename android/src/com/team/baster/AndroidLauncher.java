package com.team.baster;

import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.ads.AdControllerImpl;
import com.team.baster.domain.BasterGame;

public class AndroidLauncher extends AndroidApplication{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInstanceHolder.setAndroidLauncher(this);
        BasterGame basterGame = new BasterGame();
        AndroidInstanceHolder.setGame(basterGame);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        AndroidInstanceHolder.setConfig(config);
        AndroidInstanceHolder.setAdController(new AdControllerImpl());
        View gameView = AndroidInstanceHolder.getAndroidLauncher().initializeForView(AndroidInstanceHolder.getGame(), AndroidInstanceHolder.getConfig());
        AndroidInstanceHolder.getAndroidLauncher().setContentView(AndroidInstanceHolder.getAdController().setBannerOnView(gameView));
    }

}