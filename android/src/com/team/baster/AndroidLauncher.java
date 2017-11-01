package com.team.baster;

import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.dialog.ActionResolverAndroid;
import com.team.baster.domain.BasterGame;
import com.team.baster.requests.MyRequest;
import com.team.baster.storage.core.SQLiteJDBC;

import java.io.IOException;

public class AndroidLauncher extends AndroidApplication {

	public ActionResolverAndroid actionResolverAndroid;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SQLiteJDBC.jdbc = new SQLiteJDBC(getApplicationContext());
		actionResolverAndroid = new ActionResolverAndroid(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new BasterGame(), config);
	}
}
