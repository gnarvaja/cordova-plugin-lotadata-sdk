package com.lotadata.moments.plugin;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.lotadata.moments.Moments;
import com.lotadata.moments.TrackingMode;
import com.lotadata.moments.plugin.actions.Action;
import com.lotadata.moments.plugin.actions.InitializeAction;
import com.lotadata.moments.plugin.actions.RecordEventAction;
import com.lotadata.moments.plugin.executors.BackgroundThreadExecutor;
import com.lotadata.moments.plugin.executors.Executor;
import com.lotadata.moments.plugin.executors.MainThreadExecutor;
import com.lotadata.moments.plugin.models.Event;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class MomentsPlugin extends CordovaPlugin {

    private Moments momentsClient = null;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        Executor mainThread = new MainThreadExecutor(cordova.getActivity());
        Executor backgroundThread = new BackgroundThreadExecutor(cordova.getThreadPool());
        Context context = cordova.getActivity();

        if (action.equals("initialize")) {

            Action<Void, Moments> initializeAction = new InitializeAction(mainThread, context, momentsClient);
            initializeAction.doAction(null, new Action.Callback<Moments>() {
                @Override
                public void onSuccess(Moments output) {
                    momentsClient = output;
                    callbackContext.success("isConnected");
                }

                @Override
                public void onError() {
                    callbackContext.error("Error, permission OK but momentsClient still == null");
                }
            });

            return true;
        } else if (action.equals("recordEvent")) {

            Event<Double> event = new Event<Double>(data.getString(0));
            try {
                event.setData(data.getDouble(1));
            } catch (JSONException ex) { }

            Action<Event<Double>, Void> recordEventAction = new RecordEventAction(backgroundThread, momentsClient);
            recordEventAction.doAction(event, new Action.Callback<Void>() {
                @Override
                public void onSuccess(Void output) {
                    callbackContext.success("Event recorded");
                }

                @Override
                public void onError() {
                    callbackContext.error("Not initialized!");
                }
            });

            return true;
        } else if (action.equals("setFgTrackingMode")) {
            if (momentsClient == null) {
                callbackContext.error("Not initialized!");
            } else {
                final String trackingMode = data.getString(0);
                TrackingMode mode = null;
                try {
                    mode = TrackingMode.valueOf(trackingMode);
                } catch (IllegalArgumentException err) {
                    callbackContext.error("Invalid trackingMode");
                } catch (NullPointerException err) {
                    callbackContext.error("trackingMode not specified");
                }
                if (mode != null) {
                    momentsClient.setFgTrackingMode(mode);
                    callbackContext.success("setFgTrackingMode OK");
                }
            }
            return true;
        } else if (action.equals("setBgTrackingMode")) {
            if (momentsClient == null) {
                callbackContext.error("Not initialized!");
            } else {
                final String trackingMode = data.getString(0);
                TrackingMode mode = null;
                try {
                    mode = TrackingMode.valueOf(trackingMode);
                } catch (IllegalArgumentException err) {
                    callbackContext.error("Invalid trackingMode");
                } catch (NullPointerException err) {
                    callbackContext.error("trackingMode not specified");
                }
                if (mode != null) {
                    momentsClient.setBgTrackingMode(mode);
                    callbackContext.success("setBgTrackingMode OK");
                }
            }
            return true;
        } else if (action.equals("bestKnownLocation")) {
            if (momentsClient == null) {
                callbackContext.error("Not initialized!");
            } else {
                final Location bestKnownLocation = momentsClient.bestKnownLocation();
                if (bestKnownLocation == null) {
                    callbackContext.error("No known location yet");
                } else {
                    PluginResult result = new PluginResult(Status.OK, location2JSON(bestKnownLocation));
                    callbackContext.sendPluginResult(result);
                }
            }
            return true;
        } else {
            return false;
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

    @Override
    public void onDestroy() {
        if (momentsClient != null) {
            momentsClient.disconnect();
            momentsClient = null;
        }
        super.onDestroy();
    }
}
