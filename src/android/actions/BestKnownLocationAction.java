package com.lotadata.moments.plugin.actions;

import android.location.Location;
import android.os.Bundle;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.actions.callback.Callback;
import com.lotadata.moments.plugin.executors.Executor;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
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
                jsonExtras.put(key, wrap(value));
            }
            outputJson.put("extras", jsonExtras);
        }

        return outputJson;
    }

    private Object wrap(Object o) {
        if (o == null) {
            return "null";
        }

        if (o.getClass().isArray()) {
            JSONArray innerArray = new JSONArray();
            final int length = Array.getLength(o);
            for (int i = 0; i < length; ++i) {
                innerArray.put(wrap(Array.get(o, i)));
            }
            return innerArray;
        }

        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return new JSONArray((Collection) o);
            }
            if (o instanceof Map) {
                return new JSONObject((Map) o);
            }
            if (o instanceof Boolean ||
                    o instanceof Byte ||
                    o instanceof Character ||
                    o instanceof Double ||
                    o instanceof Float ||
                    o instanceof Integer ||
                    o instanceof Long ||
                    o instanceof Short ||
                    o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        } catch (Exception ignored) {
        }
        return "null";
    }
}
