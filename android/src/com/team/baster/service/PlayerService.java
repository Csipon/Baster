package com.team.baster.service;

import com.team.baster.storage.model.User;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public interface PlayerService {

    User getCurrentUser();
    boolean createNewPlayer(String name);
    boolean isDefaultPlayer();
    Integer getOverallScore();
    Integer getActualCoins();
    Integer getOverallExperience();
    void updateCoinsAndScore(int coins, int score);
}
