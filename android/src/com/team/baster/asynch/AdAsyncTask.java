package com.team.baster.asynch;

import android.os.AsyncTask;

import com.google.android.gms.ads.AdRequest;
import com.team.baster.AndroidInstanceHolder;

/**
 * Created by Pasha on 11.01.2018.
 */

public class AdAsyncTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {
        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest ad = builder.build();
        AndroidInstanceHolder.getAndroidLauncher().runOnUiThread(
                () -> AndroidInstanceHolder.getAdController().getInterstitialAd().loadAd(ad));
        return objects;
    }
}
