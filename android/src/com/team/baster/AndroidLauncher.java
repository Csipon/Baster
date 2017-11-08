package com.team.baster;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.dialog.ActionResolverAndroid;
import com.team.baster.domain.BasterGame;
import com.team.baster.security.GoogleSecurity;
import com.team.baster.storage.core.SQLiteJDBC;

public class AndroidLauncher extends AndroidApplication {

	public ActionResolverAndroid actionResolverAndroid;
	public GoogleSecurity security = GoogleSecurity.instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		security.signIn();
//		System.out.println(security.account.isConnected());
//		System.out.println(security.account.);
		SQLiteJDBC.jdbc = new SQLiteJDBC(getApplicationContext());
		actionResolverAndroid = new ActionResolverAndroid(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new BasterGame(actionResolverAndroid), config);
	}
}
