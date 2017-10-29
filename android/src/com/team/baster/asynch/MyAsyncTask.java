package com.team.baster.asynch;

import android.os.AsyncTask;

import com.badlogic.gdx.utils.TimeUtils;
import com.team.baster.storage.PlayerStatusStorage;
import com.team.baster.storage.ScoreStorage;

/**
 * Created by Pasha on 10/29/2017.
 */

public class MyAsyncTask extends AsyncTask {
    private ScoreStorage scoreStorage;
    private PlayerStatusStorage playerStatusStorage;
    private int score;
    private int coins;

    public MyAsyncTask(ScoreStorage scoreStorage, PlayerStatusStorage playerStatusStorage, int score, int coins) {
        this.scoreStorage = scoreStorage;
        this.playerStatusStorage = playerStatusStorage;
        this.score = score;
        this.coins = coins;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        scoreStorage.save(score);
        playerStatusStorage.update(coins, score);
        System.out.println("------- 1 "  + TimeUtils.millis());
        System.out.println("EXECUTE");

    }
}
