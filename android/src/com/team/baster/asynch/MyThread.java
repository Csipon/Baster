package com.team.baster.asynch;

import android.os.AsyncTask;

/**
 * Created by Pasha on 10/29/2017.
 */

public class MyThread extends Thread {
    private AsyncTask asyncTask;

    public MyThread(AsyncTask asyncTask) {
        this.asyncTask = asyncTask;
    }

    @Override
    public void run() {
        asyncTask.execute();
    }
}
