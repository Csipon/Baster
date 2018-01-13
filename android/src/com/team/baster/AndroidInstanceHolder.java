package com.team.baster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.ads.AdController;
import com.team.baster.dialog.ActionResolverImpl;
import com.team.baster.security.FirebaseAuthentication;

/**
 * Created by Pasha on 11.01.2018.
 */

public class AndroidInstanceHolder {
    private static AndroidLauncher androidLauncher;
    private static AndroidApplicationConfiguration config;
    private static AdController adController;
    private static ActionResolverImpl actionResolverAndroid;
    private static Game game;
    private static FirebaseAuthentication auth;

    public static AndroidLauncher getAndroidLauncher() {
        return androidLauncher;
    }

    public static void setAndroidLauncher(AndroidLauncher androidLauncher) {
        AndroidInstanceHolder.androidLauncher = androidLauncher;
    }

    public static AdController getAdController() {
        return adController;
    }

    public static void setAdController(AdController adController) {
        AndroidInstanceHolder.adController = adController;
    }

    public static ActionResolverImpl getActionResolverAndroid() {
        return actionResolverAndroid;
    }

    public static void setActionResolverAndroid(ActionResolverImpl actionResolverAndroid) {
        AndroidInstanceHolder.actionResolverAndroid = actionResolverAndroid;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        AndroidInstanceHolder.game = game;
    }

    public static AndroidApplicationConfiguration getConfig() {
        return config;
    }

    public static void setConfig(AndroidApplicationConfiguration config) {
        AndroidInstanceHolder.config = config;
    }

    public static FirebaseAuthentication getAuth() {
        return auth;
    }

    public static void setAuth(FirebaseAuthentication auth) {
        AndroidInstanceHolder.auth = auth;
    }
}
