package com.team.baster.ads;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Pasha on 07.01.2018.
 */

public class AdControllerImpl implements AdController{
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private int interstitialCount = 1;


    private AdView bannerAd;
    private InterstitialAd interstitialAd;
    private AndroidApplication androidApplication;


    public AdControllerImpl(AndroidApplication androidApplication) {
        this.androidApplication = androidApplication;
        setupAds();
    }


    public void setupAds() {
        bannerAd = new AdView(androidApplication);
        bannerAd.setVisibility(View.INVISIBLE);
//        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);

        interstitialAd = new InterstitialAd(androidApplication);
        interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest ad = builder.build();
        interstitialAd.loadAd(ad);
    }


    public RelativeLayout setBannerOnView(View gameView){
        RelativeLayout layout = new RelativeLayout(androidApplication);
        layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAd, params);
        return layout;
    }

    @Override
    public void showBannedAd() {
        androidApplication.runOnUiThread(() -> {
            AdRequest.Builder builder = new AdRequest.Builder();
            AdRequest ad = builder.build();
            bannerAd.setVisibility(View.VISIBLE);
            bannerAd.loadAd(ad);
        });
    }

    @Override
    public void hideBannerAd() {
        androidApplication.runOnUiThread(() -> bannerAd.setVisibility(View.INVISIBLE));
    }

    @Override
    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) androidApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return ni != null && ni.isConnected();
    }

    @Override
    public void showInterstellarAd(Runnable then) {
//        androidApplication.runOnUiThread(() -> {
//            if (then != null){
//                interstitialAd.setAdListener(new AdListener(){
//                    @Override
//                    public void onAdClosed() {
//                        Gdx.app.postRunnable(then);
//                        AdRequest.Builder builder = new AdRequest.Builder();
//                        AdRequest ad = builder.build();
//                        interstitialAd.loadAd(ad);
//                    }
//                });
//            }
//           AdRequest.Builder builder = new AdRequest.Builder();
//           AdRequest ad = builder.build();
//           interstitialAd.loadAd(ad);
//            interstitialAd.show();
//        });
    }

    @Override
    public void showInterstellar() {
        androidApplication.runOnUiThread(() -> {
            if (interstitialAd.isLoaded() && getInterstitialCount() != 0) {
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        AdRequest.Builder builder = new AdRequest.Builder();
                        AdRequest ad = builder.build();
                        interstitialAd.loadAd(ad);
                        decrementInterstitialCount();
                    }
                });
                interstitialAd.show();
            }
        });
    }

    @Override
    public void hideInterstellarAd(Runnable then) {

    }

    @Override
    public void setInterstitialCount(int countInterstitial) {
        this.interstitialCount = countInterstitial;
    }

    private void decrementInterstitialCount(){
        interstitialCount--;
    }

    private int getInterstitialCount(){
        return interstitialCount;
    }
}
