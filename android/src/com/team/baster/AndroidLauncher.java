package com.team.baster;

import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.ads.AdController;
import com.team.baster.ads.AdControllerImpl;
import com.team.baster.dialog.ActionResolverAndroid;
import com.team.baster.domain.BasterGame;
import com.team.baster.security.FirebaseAuthentication;
import com.team.baster.storage.core.SQLiteJDBC;

import util.RequestUtil;

public class AndroidLauncher extends AndroidApplication{
    public static AndroidApplicationConfiguration config;
    public ActionResolverAndroid actionResolverAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteJDBC.jdbc = new SQLiteJDBC(this);
        FirebaseAuthentication.auth = new FirebaseAuthentication(this);
        RequestUtil.instance = new RequestUtil(this);

        actionResolverAndroid = new ActionResolverAndroid(this);
        config = new AndroidApplicationConfiguration();

        config.useAccelerometer = false;
        config.useCompass = false;
        AdController adController = new AdControllerImpl(this);
        View gameView = initializeForView(new BasterGame(actionResolverAndroid, this, adController), config);
        setContentView(adController.setBannerOnView(gameView));
    }
}
