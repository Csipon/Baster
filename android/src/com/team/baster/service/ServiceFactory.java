package com.team.baster.service;

import com.team.baster.service.impl.ScoreServiceImpl;
import com.team.baster.service.impl.UserServiceImpl;
import com.team.baster.storage.StorageFactory;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public class ServiceFactory {
    private static UserService userService;
    private static ScoreService scoreService;

    public static UserService getUserService(){
        if (userService == null) {
            userService = new UserServiceImpl(StorageFactory.getUserStorage());
        }
        return userService;
    }

    public static ScoreService getScoreService(){
        if (scoreService == null) {
            scoreService = new ScoreServiceImpl(StorageFactory.getScoreStorage(), StorageFactory.getUserStorage());
        }
        return scoreService;
    }
}
