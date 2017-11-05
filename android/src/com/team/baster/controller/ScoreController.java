package com.team.baster.controller;

import com.badlogic.gdx.utils.Array;
import com.team.baster.storage.ScoreStorage;

/**
 * Created by Pasha on 10/28/2017.
 */

public class ScoreController {
    private int score;
    private ScoreStorage storage;

    public ScoreController() {
        storage = new ScoreStorage();
    }

    public void calculateScore(int speed) {
        score += speed / 200;
    }

    public int getScore() {
        return score;
    }

    public void saveCurrentScore(){
        storage.save(score);
    }

    public Array<Long> fetchLastBestScores(){
        return storage.readLastBestScore();
    }
}
