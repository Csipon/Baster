package com.team.baster.ads;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Pasha on 06.01.2018.
 */

public interface AdController {
    void setupAds();
    RelativeLayout setBannerOnView(View gameView);
    void showBannedAd();
    void hideBannerAd();
    boolean isWifiConnected();
    void showInterstellarAd(Runnable then);
    void showInterstellar();
    void setInterstitialCount(int countInterstitial);
    void hideInterstellarAd(Runnable then);
}
