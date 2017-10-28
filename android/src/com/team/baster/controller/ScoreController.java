package com.team.baster.controller;

import com.badlogic.gdx.utils.Array;
import com.team.baster.storage.ScoreStorage;

/**
 * Created by Pasha on 10/28/2017.
 */

public class ScoreController {
    private long score;
    private ScoreStorage storage;

    public ScoreController() {
        storage = new ScoreStorage();
    }

    public void calculateScore(int speed) {
        score += speed / 200;
    }

    public long getScore() {
        return score;
    }

    public void saveCurrentScore(){
        storage.save(score);
    }

    public Array<Long> fetchLastBestScores(){
        return storage.readLastBestScore();
    }
}
