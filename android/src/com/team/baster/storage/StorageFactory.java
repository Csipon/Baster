package com.team.baster.storage;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public final class StorageFactory {
    private static ScoreStorage scoreStorage;

    private static UserStorage userStorage;

    private StorageFactory() {
    }


    public static ScoreStorage getScoreStorage() {
        if (scoreStorage == null) {
            scoreStorage = new ScoreStorage();
        }
        return scoreStorage;
    }
    public static UserStorage getUserStorage() {
        if (userStorage == null) {
            userStorage = new UserStorage();
        }
        return userStorage;
    }
}
