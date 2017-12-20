package com.team.baster.controller;

import com.team.baster.service.ScoreService;
import com.team.baster.service.ServiceFactory;

import java.util.List;

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

    public List<Long> fetchLastBestScores(){
        return scoreService.readLastBestScore();
    }
}
