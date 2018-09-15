package com.lotadata.moments.plugin.actions;

import android.location.Location;
import android.os.Bundle;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.executors.Executor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class BestKnownLocationAction implements Action<Void, JSONObject> {

    private Executor executor;
    private Moments momentsClient;
    private Callback<JSONObject> callback;

    public BestKnownLocationAction(Executor executor, Moments momentsClient) {
        this.executor = executor;
        this.momentsClient = momentsClient;
    }

    @Override
    public void doAction(Void input, Callback<JSONObject> callback) {
        this.callback = callback;
        executor.execute(this);
    }

    @Override
    public void run() {
        if (momentsClient == null) {
            callback.onError();
        } else {
            final Location bestKnownLocation = momentsClient.bestKnownLocation();
            if (bestKnownLocation == null) {
                callback.onError();
            } else {
                try {
                    callback.onSuccess(location2JSON(bestKnownLocation));
                } catch (JSONException e) {
                    callback.onError();
                }
            }
        }
    }

    private JSONObject location2JSON(final Location location) throws JSONException {
        JSONObject json = new JSONObject();
        Bundle extras = location.getExtras();
        if (extras != null) {
            JSONObject json_extras = new JSONObject();
            Set<String> keys = extras.keySet();
            for (String key : keys) {
                // json.put(key, bundle.get(key)); see edit below
                json_extras.put(key, JSONObject.wrap(extras.get(key)));
            }
            json.put("extras", json_extras);
        }
        json.put("latitude", location.getLatitude());
        json.put("longitude", location.getLongitude());
        json.put("provider", location.getProvider());
        return json;
    }
}
