package com.team.baster.service.impl;

import com.team.baster.service.UserService;
import com.team.baster.storage.UserStorage;
import com.team.baster.storage.model.User;

/**
 * Created by dmitriychalienko on 09.11.17.
 */

public final class UserServiceImpl implements UserService {
    private UserStorage storage;

    public UserServiceImpl(UserStorage storage) {
        this.storage = storage;
    }

    public  User getCurrentUser(){
        return storage.getCurrentUser();
    }
}
