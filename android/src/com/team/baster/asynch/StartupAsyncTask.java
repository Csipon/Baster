package com.team.baster.asynch;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Window;

import com.team.baster.AndroidInstanceHolder;
import com.team.baster.R;

/**
 * Created by Pasha on 13.01.2018.
 */

public class StartupAsyncTask extends AsyncTask {

    private Dialog progressDialog;


    @Override
    protected void onPreExecute() {
        //AndroidInstanceHolder.getAndroidLauncher().setContentView(R.layout.logo);
        progressDialog = new Dialog(AndroidInstanceHolder.getAndroidLauncher());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
//        progressDialog.setContentView(R.layout.logo);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            System.out.println("START");
            Thread.sleep(2000);
            System.out.println("FINISH");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return objects;
    }

    @Override
    protected void onPostExecute(Object o) {
        progressDialog.dismiss();
        super.onPostExecute(o);
    }
}
