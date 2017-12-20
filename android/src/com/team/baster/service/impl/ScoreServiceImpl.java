package com.team.baster.service.impl;

import com.android.volley.toolbox.RequestFuture;
import com.team.baster.service.PlayerService;
import com.team.baster.service.ScoreService;
import com.team.baster.storage.ScoreStorage;
import com.team.baster.storage.model.Score;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import util.RequestUtil;

/**
 * Created by dmitriychalienko on 06.11.17.
 */

public final class ScoreServiceImpl implements ScoreService{
    private ScoreStorage scoreStorage;
    private PlayerService playerService;
    private static final String SERVER_URL = "http://192.162.132.33:8080";

    public ScoreServiceImpl(ScoreStorage scoreStorage, PlayerService playerService) {
        this.scoreStorage  = scoreStorage;
        this.playerService = playerService;
    }

    @Override
    public void saveScoreToBack(final Score score){
        try {
            RequestUtil.instance.postObj(new URL(SERVER_URL + "/score/saveScore"),
                    response -> {
                    }, error -> {
                        score.setForBack(true);
//                        scoreStorage.saveForQueue(score);
                        System.out.println("Cannot send score on the back " + error);
                    }, score.toJson());
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }
    //todo
    @Override
    public List<Score> getTopX(int x){
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        try {
            RequestUtil.instance.getArray(new URL(SERVER_URL + "/score/getTopX?x=" + x),
                    future, future, null);
            JSONArray array = future.get(1, TimeUnit.SECONDS);
            return parseJSONArrayToList(array);
        } catch (MalformedURLException | InterruptedException | ExecutionException | TimeoutException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(int score) {
        scoreStorage.save(score, playerService.getCurrentUser().getLogin());
    }

    @Override
    public List<Score> readFromBackupQueue() {
        return scoreStorage.readFromBackupQueue();
    }

    @Override
    public List<Long> readLastBestScore() {
        return scoreStorage.readLastBestScore();
    }

    @Override
    public void saveScoresToBack(final List<Score> scores) {
        try {
            RequestUtil.instance.postArray(new URL(SERVER_URL + "/score/saveScores"),
                    response -> {
                    }, error -> {

//                        scoreStorage.saveForQueue(scores);
                        System.out.println("Cannot send score on the back " + error);
                    }, parseListToJSONArray(scores));
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static JSONArray parseListToJSONArray(List<Score> scores) {
        JSONArray jsonArray = new JSONArray();
        scores.forEach(score -> jsonArray.put(score.toJson()));
        return jsonArray;
    }

    private static List<Score> parseJSONArrayToList(JSONArray array) throws JSONException {
        if (array != null) {
            List<Score> scores = new ArrayList<>();
            int len = array.length();
            for (int i =0; i < len; i++) {
                Score score = new Score();
                JSONObject object = array.getJSONObject(i);
                score.setScore(object.getInt("score"));
                score.setLogin(object.getString("login"));
                score.setDate(new Date(object.getLong("date")));
                scores.add(score);
            }
            return scores;
        }
        return Collections.emptyList();
    }
}
