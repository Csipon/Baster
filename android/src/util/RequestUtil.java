package util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.team.baster.AndroidLauncher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by dmitriychalienko on 06.11.17.
 */

public class RequestUtil {
    private RequestQueue queue;
    public static RequestUtil instance;

    public RequestUtil(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }


    public void postObj(URL url, Response.Listener<JSONObject> responseListener,
                        Response.ErrorListener errorListener, JSONObject object) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url.toString(),
                object, responseListener, errorListener);
        queue.add(jsObjRequest);
    }
    public void postArray(URL url, Response.Listener<JSONArray> responseListener,
                               Response.ErrorListener errorListener, JSONArray object) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.POST, url.toString(),
                object, responseListener, errorListener);
        queue.add(jsObjRequest);
    }
    public void getArray(URL url, Response.Listener<JSONArray> responseListener,
                           Response.ErrorListener errorListener, JSONObject object) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url.toString(),
                object, responseListener, errorListener);
        queue.add(jsObjRequest);
    }
    public void getObject(URL url, Response.Listener<JSONObject> responseListener,
                                Response.ErrorListener errorListener, JSONObject object) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url.toString(),
                object, responseListener, errorListener);
        queue.add(jsObjRequest);
    }

}
