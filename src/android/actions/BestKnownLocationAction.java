package com.lotadata.moments.plugin.actions;

import android.location.Location;
import android.os.Bundle;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.actions.callback.Callback;
import com.lotadata.moments.plugin.executors.Executor;

import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class BestKnownLocationAction implements Action {

    private Executor executor;
    private Moments momentsClient;
    private Callback callback;

    public BestKnownLocationAction(Executor executor, Moments momentsClient, Callback callback) {
        this.executor = executor;
        this.momentsClient = momentsClient;
        this.callback = callback;
    }

    @Override
    public void doAction() {
        executor.execute(this);
    }

    @Override
    public void run() {
        if (momentsClient == null) {
            callback.onError("Not initialized!");
        } else {
            final Location bestKnownLocation = momentsClient.bestKnownLocation();
            if (bestKnownLocation == null) {
                callback.onError("Not initialized!");
            } else {
                try {
                    PluginResult result = new PluginResult(PluginResult.Status.OK, location2JSON(bestKnownLocation));
                    callback.sendPluginResult(result);
                } catch (JSONException e) {
                    callback.onError("Not initialized!");
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
