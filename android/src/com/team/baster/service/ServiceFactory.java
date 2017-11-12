package com.team.baster.service;

import com.team.baster.service.impl.PlayerServiceImpl;
import com.team.baster.service.impl.ScoreServiceImpl;
import com.team.baster.storage.StorageFactory;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public class ServiceFactory {
    private static PlayerService playerService;
    private static ScoreService scoreService;

    public static PlayerService getPlayerService(){
        if (playerService == null) {
            playerService = new PlayerServiceImpl(StorageFactory.getPlayerStatusStorage());
        }
        return playerService;
    }

    public static ScoreService getScoreService(){
        if (scoreService == null) {
            scoreService = new ScoreServiceImpl(StorageFactory.getScoreStorage(), getPlayerService());
        }
        return scoreService;
    }
}
