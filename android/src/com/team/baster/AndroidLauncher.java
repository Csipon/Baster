package com.team.baster;

import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.ads.AdControllerImpl;
import com.team.baster.asynch.StartupAsyncTask;
import com.team.baster.dialog.ActionResolverImpl;
import com.team.baster.domain.BasterGame;
import com.team.baster.security.FirebaseAuthentication;
import com.team.baster.storage.core.SQLiteJDBC;

import util.RequestUtil;

public class AndroidLauncher extends AndroidApplication{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInstanceHolder.setAndroidLauncher(this);
        SQLiteJDBC.jdbc = new SQLiteJDBC();
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        BasterGame basterGame = new BasterGame();
        AndroidInstanceHolder.setActionResolverAndroid(new ActionResolverImpl());
        AndroidInstanceHolder.setConfig(config);
        AndroidInstanceHolder.setGame(basterGame);
        new StartupAsyncTask().execute();
        RequestUtil.instance = new RequestUtil();
        AndroidInstanceHolder.setAdController(new AdControllerImpl());
        AndroidInstanceHolder.setAuth(new FirebaseAuthentication());
        View gameView = initializeForView(basterGame, config);
        setContentView(AndroidInstanceHolder.getAdController().setBannerOnView(gameView));
    }
}