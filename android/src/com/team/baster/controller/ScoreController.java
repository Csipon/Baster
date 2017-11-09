package com.team.baster.controller;

import com.badlogic.gdx.utils.Array;
import com.team.baster.service.ScoreService;
import com.team.baster.service.ServiceFactory;

/**
 * Created by Pasha on 10/28/2017.
 */

public class ScoreController {
    private int score;
    private ScoreService scoreService;

    public ScoreController() {
        scoreService = ServiceFactory.getScoreService();
    }

    public void calculateScore(int speed) {
        score += speed / 200;
    }

    public int getScore() {
        return score;
    }

    public void saveCurrentScore(){
        scoreService.save(score);
    }

    public Array<Long> fetchLastBestScores(){
        return scoreService.readLastBestScore();
    }
}
