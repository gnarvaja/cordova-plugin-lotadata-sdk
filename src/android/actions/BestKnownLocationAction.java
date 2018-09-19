package com.lotadata.moments.plugin.actions;

import android.location.Location;
import android.os.Bundle;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.actions.callback.Callback;
import com.lotadata.moments.plugin.executors.Executor;
import com.lotadata.moments.plugin.utils.JsonParser;

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
            callback.onError("MomentsClient not initialized");
        } else {
            try {
                Location bestKnownLocation = momentsClient.bestKnownLocation();
                PluginResult result = new PluginResult(PluginResult.Status.OK, location2Json(bestKnownLocation));
                callback.sendPluginResult(result);
            } catch (JSONException ex) {
                callback.onError("Error while parsing location data");
            } catch (Exception ex) {
                callback.onError("No known location yet");
            }
        }
    }

    private JSONObject location2Json(final Location location) throws JSONException {
        JSONObject outputJson = new JSONObject();
        JSONObject jsonExtras = new JSONObject();
        outputJson.put("latitude", location.getLatitude());
        outputJson.put("longitude", location.getLongitude());
        outputJson.put("provider", location.getProvider());

        Bundle extras = location.getExtras();
        if (extras != null) {
            Set<String> keys = extras.keySet();
            for (String key : keys) {
                Object value = extras.get(key);
                jsonExtras.put(key, JsonParser.wrap(value));
            }
            outputJson.put("extras", jsonExtras);
        }

        return outputJson;
    }
}
