package com.team.baster.service.impl;

import com.team.baster.model.PlayerStatus;
import com.team.baster.service.PlayerService;
import com.team.baster.storage.PlayerStatusStorage;
import com.team.baster.storage.model.User;

import java.util.UUID;

import static com.team.baster.GameConstants.DEFAULT_PLAYER_NAME;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public final class PlayerServiceImpl implements PlayerService {
    private PlayerStatusStorage storage;
    private static User currentUser;

    public PlayerServiceImpl(PlayerStatusStorage storage) {
        this.storage = storage;
    }

    public User getCurrentUser(){
        if (currentUser == null) {
            PlayerStatus playerStatus = storage.readPlayerStatus();
            currentUser = new User(playerStatus.id, playerStatus.player);
        }
        return currentUser;
    }


    public boolean createNewPlayer(String name){
        if (validatePlayerName(name)) {
            String hash = "#" + UUID.randomUUID().toString().replace("-", "");
            currentUser.setLogin(name + hash);
            storage.update(currentUser.getId(), currentUser.getLogin(), null, null, null);
            return true;
        }
        return false;
    }


    private boolean validatePlayerName(String name){
        if (name != null && !name.isEmpty() && !DEFAULT_PLAYER_NAME.equalsIgnoreCase(name) && name.length() >= 2){
            return true;
        }
        return false;
    }


    public boolean isDefaultPlayer(){
        if (DEFAULT_PLAYER_NAME.equalsIgnoreCase(currentUser.getLogin())){
            return true;
        }
        return false;
    }

    @Override
    public void updateCoinsAndScore(int coins, int score) {
        storage.update(coins, score);
    }

    public Integer getOverallScore(){
        return PlayerStatusStorage.overallScore;
    }

    public Integer getActualCoins(){
        return PlayerStatusStorage.actualCoins;
    }

    public Integer getOverallExperience(){
        return PlayerStatusStorage.overallExperience;
    }
}
