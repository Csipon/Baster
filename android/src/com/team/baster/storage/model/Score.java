package com.team.baster.storage.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by dmitriychalienko on 06.11.17.
 */

public class Score {
    private String login;
    private Date date;
    private int score;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("login", login);
            object.put("date", date.getTime());
            object.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
