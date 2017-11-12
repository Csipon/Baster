package com.team.baster.storage;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public final class StorageFactory {
    private static ScoreStorage scoreStorage;
    private static PlayerStatusStorage playerStorage;

    private StorageFactory() {
    }


    public static ScoreStorage getScoreStorage() {
        if (scoreStorage == null) {
            scoreStorage = new ScoreStorage();
        }
        return scoreStorage;
    }
    public static PlayerStatusStorage getPlayerStatusStorage() {
        if (playerStorage == null) {
            playerStorage = new PlayerStatusStorage();
        }
        return playerStorage;
    }
}
