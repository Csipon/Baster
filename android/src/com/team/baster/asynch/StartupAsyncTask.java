package com.team.baster.asynch;

import android.os.AsyncTask;

import com.team.baster.AndroidInstanceHolder;
import com.team.baster.R;

/**
 * Created by Pasha on 13.01.2018.
 */

public class StartupAsyncTask extends AsyncTask {

    @Override
    protected void onPreExecute() {
        AndroidInstanceHolder.getAndroidLauncher().setContentView(R.layout.logo);
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
}
