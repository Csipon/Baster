package com.team.baster;

import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.ads.AdController;
import com.team.baster.ads.AdControllerImpl;
import com.team.baster.dialog.ActionResolverImpl;
import com.team.baster.domain.BasterGame;
import com.team.baster.security.FirebaseAuthentication;
import com.team.baster.storage.core.SQLiteJDBC;

import util.RequestUtil;

public class AndroidLauncher extends AndroidApplication{
    public static AndroidApplicationConfiguration config;
    public ActionResolverImpl actionResolverAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteJDBC.jdbc = new SQLiteJDBC(this);
        RequestUtil.instance = new RequestUtil(this);
        actionResolverAndroid = new ActionResolverImpl(this);
        config = new AndroidApplicationConfiguration();

        AdController adController = new AdControllerImpl(this);
        BasterGame basterGame = new BasterGame(actionResolverAndroid, this, adController);
        FirebaseAuthentication.auth = new FirebaseAuthentication(this, basterGame);

        config.useAccelerometer = false;
        config.useCompass = false;
        View gameView = initializeForView(basterGame, config);
        setContentView(adController.setBannerOnView(gameView));
    }
}
