package com.team.baster.service.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.badlogic.gdx.utils.Array;
import com.team.baster.service.ScoreService;
import com.team.baster.storage.ScoreStorage;
import com.team.baster.storage.UserStorage;
import com.team.baster.storage.model.Score;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import util.RequestUtil;

/**
 * Created by dmitriychalienko on 06.11.17.
 */

public final class ScoreServiceImpl implements ScoreService{
    private ScoreStorage scoreStorage;
    private UserStorage userStorage;
    private static final String SERVER_URL = "http://192.162.132.33:8080";

    public ScoreServiceImpl(ScoreStorage scoreStorage, UserStorage userStorage) {
        this.scoreStorage = scoreStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void saveScoreToBack(final Score score){
        System.out.println("SAVE SCORE");
        try {
            RequestUtil.instance.postObj(new URL(SERVER_URL + "/score/saveScore"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            scoreStorage.saveForQueue(score);
                            System.out.println("Cannot send score on the back " + error);
                        }
                    }, score.toJson());
        } catch (MalformedURLException e) {
            System.out.println(e);
        }
    }
    //todo
    @Override
    public List<Score> getTopX(int x){
        try {
            RequestUtil.instance.getArray(new URL(SERVER_URL + "/score/getTopX?x=" + x),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            System.out.println(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.err.print(error);
                        }
                    }, null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(int score) {
        scoreStorage.save(score, userStorage.getCurrentUser().getLogin());
    }

    @Override
    public List<Score> readFromBackupQueue() {
        return scoreStorage.readFromBackupQueue();
    }

    @Override
    public Array<Long> readLastBestScore() {
        return scoreStorage.readLastBestScore();
    }


}
