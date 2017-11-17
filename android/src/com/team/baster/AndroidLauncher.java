package com.team.baster;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team.baster.dialog.ActionResolverAndroid;
import com.team.baster.domain.BasterGame;
import com.team.baster.storage.core.SQLiteJDBC;

import util.RequestUtil;

public class AndroidLauncher extends AndroidApplication {

	public ActionResolverAndroid actionResolverAndroid;
	public static TelephonyManager telephonyManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		telephonyManager 						=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		SQLiteJDBC.jdbc 						= new SQLiteJDBC(getApplicationContext());
		RequestUtil.instance 					= new RequestUtil(getApplicationContext());

		actionResolverAndroid 					= new ActionResolverAndroid(this);
		AndroidApplicationConfiguration config 	= new AndroidApplicationConfiguration();

		config.useAccelerometer 				= false;
		config.useCompass 						= false;

		initialize(new BasterGame(actionResolverAndroid, this), config);
	}

}
