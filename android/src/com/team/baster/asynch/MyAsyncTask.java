package com.team.baster.asynch;

import android.os.AsyncTask;

import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.service.PlayerService;
import com.team.baster.service.ScoreService;

/**
 * Created by Pasha on 10/29/2017.
 */

public class MyAsyncTask extends AsyncTask {
    private ScoreService scoreService;
    private PlayerService playerService;
    private int score;
    private int coins;

    public MyAsyncTask(ScoreService scoreService, PlayerService playerService, int score, int coins) {
        this.scoreService = scoreService;
        this.playerService = playerService;
        this.score = score;
        this.coins = coins;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
//        Example for send simple request
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        MyRequest request = new MyRequest();
//        try {
//            request.sendRequest();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        scoreService.save(score);
        playerService.updateCoinsAndScore(coins, score);
        System.out.println("------- 1 "  + TimeUtils.millis());
        System.out.println("EXECUTE");

    }
}
