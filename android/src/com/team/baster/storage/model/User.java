package com.team.baster.storage.model;

/**
 * Created by dmitriychalienko on 09.11.17.
 */



// STUB
public class User {
    private Integer id;
    private String login;

    public User(Integer id, String login) {
        this.id = id;
        this.login = login;
    }

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
