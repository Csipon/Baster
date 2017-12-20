package com.team.baster.service;

import com.team.baster.storage.model.Score;

import java.util.List;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public interface ScoreService{

    void saveScoreToBack(Score score);

    List<Score> getTopX(int x);

    void save(int score);

    List<Score> readFromBackupQueue();

    List<Long> readLastBestScore();

    void saveScoresToBack(List<Score> scores);
}
