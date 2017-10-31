package com.team.baster.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Pasha on 10/30/2017.
 */

public class MyRequest {


    public void sendRequest() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://192.162.132.33/");
        request.setHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity stringEntity = new StringEntity("{}", "UTF8");
        request.setEntity(stringEntity);
        HttpResponse response = client.execute(request);

// Get the response
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(
                        response.getEntity().getContent()));

        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println("RESPONSE = " + line);
        }
    }



}

