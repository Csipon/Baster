package com.team.baster;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.team.baster.dialog.ActionResolverAndroid;
import com.team.baster.domain.BasterGame;
import com.team.baster.security.FirebaseAuthentication;
import com.team.baster.storage.core.SQLiteJDBC;

import util.RequestUtil;

public class AndroidLauncher extends AndroidApplication {

    private static final String TAG = "Firebase";
    public ActionResolverAndroid actionResolverAndroid;

    public static AndroidApplicationConfiguration config;
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-2621238173452183/1253776111";


    private AdView bannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteJDBC.jdbc 						= new SQLiteJDBC(this);
        FirebaseAuthentication.auth             = new FirebaseAuthentication(this);
        RequestUtil.instance 					= new RequestUtil(this);

        actionResolverAndroid = new ActionResolverAndroid(this);
        config = new AndroidApplicationConfiguration();

        config.useAccelerometer = false;
        config.useCompass = false;

        View gameView = initializeForView(new BasterGame(actionResolverAndroid, this), config);
        setupAds();

//         Define the layout
        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAd, params);

        setContentView(layout);
    }


    public void setupAds() {
        bannerAd = new AdView(this);
//        bannerAd.setVisibility(View.);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
    }

//    private void authentication(){
//        if (getCurrentUser() == null){
//            Log.d(TAG, "Try to create new User");
//            List<String> credentials = actionResolverAndroid.showDialogLogin();
//            Log.d(TAG, "Credentials was inputed");
//            Log.d(TAG, "Credentials = " + credentials);
//            while (true) {
//                if(credentials.size() == 2) {
//                    Log.d(TAG, "Try to enter credentials");
//                    String email = credentials.get(0);
//                    String password = credentials.get(1);
//                    createAccount(EMAIL, PASSWORD);
//                    if (getCurrentUser() != null){
//                        Log.d(TAG, "User is created");
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//
//    public void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        Log.d(TAG, "createUserWithEmail:success");
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                        Toast.makeText(this, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    public void signIn(String email, String password) {
//        Log.d(TAG, "signIn:" + email);
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        Log.d(TAG, "signInWithEmail:success");
//                    } else {
//                        Log.w(TAG, "signInWithEmail:failure", task.getException());
//                        Toast.makeText(this, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    if (!task.isSuccessful()) {
//                        Log.w(TAG, "Auth failed");
//                    }
//                });
//    }
//
//    public void signOut() {
//        mAuth.signOut();
//    }

//    public void sendEmailVerification() {
//        FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(this,
//                                "Verification email sent to " + user.getEmail(),
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        Log.e(TAG, "sendEmailVerification", task.getException());
//                        Toast.makeText(this,
//                                "Failed to send verification email.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

//    public FirebaseUser getCurrentUser() {
//        return currentUser;
//    }
}
