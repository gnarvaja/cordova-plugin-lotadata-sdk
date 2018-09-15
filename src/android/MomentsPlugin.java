package com.lotadata.moments.plugin;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.actions.Action;
import com.lotadata.moments.plugin.actions.BestKnownLocationAction;
import com.lotadata.moments.plugin.actions.InitializeAction;
import com.lotadata.moments.plugin.actions.RecordEventAction;
import com.lotadata.moments.plugin.actions.SetTrackingModeAction;
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
            handleInitialize(callbackContext, mainThread, context);
            return true;
        } else if (action.equals("recordEvent")) {
            handleRecordEvent(data, callbackContext, backgroundThread);
            return true;
        } else if (action.equals("setFgTrackingMode")) {
            handleSetFgTrackingMode(data, callbackContext, backgroundThread);
            return true;
        } else if (action.equals("setBgTrackingMode")) {
            handleSetBgTrackingMode(data, callbackContext, backgroundThread);
            return true;
        } else if (action.equals("bestKnownLocation")) {
            handleBestKnownLocation(callbackContext, backgroundThread);
            return true;
        } else {
            return false;
        }
    }

    private void handleBestKnownLocation(final CallbackContext callbackContext, Executor backgroundThread) {
        Action<Void, JSONObject> bestKnownLocationAction = new BestKnownLocationAction(backgroundThread, momentsClient);
        bestKnownLocationAction.doAction(null, new Action.Callback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject output) {
                PluginResult result = new PluginResult(Status.OK, output);
                callbackContext.sendPluginResult(result);
            }

            @Override
            public void onError() {
                callbackContext.error("Not initialized!");
            }
        });
    }

    private void handleSetFgTrackingMode(JSONArray data, final CallbackContext callbackContext, Executor backgroundThread) throws JSONException {
        Action<String, Void> setFgTrackingModeAction = new SetTrackingModeAction(backgroundThread, momentsClient, SetTrackingModeAction.STATE.FOREGROUND);
        setFgTrackingModeAction.doAction(data.getString(0), new Action.Callback<Void>() {
            @Override
            public void onSuccess(Void output) {
                callbackContext.success("setFgTrackingMode OK");
            }

            @Override
            public void onError() {
                callbackContext.error("Invalid trackingMode");
            }
        });
    }

    private void handleSetBgTrackingMode(JSONArray data, final CallbackContext callbackContext, Executor backgroundThread) throws JSONException {
        Action<String, Void> setFgTrackingModeAction = new SetTrackingModeAction(backgroundThread, momentsClient, SetTrackingModeAction.STATE.BACKGROUND);
        setFgTrackingModeAction.doAction(data.getString(0), new Action.Callback<Void>() {
            @Override
            public void onSuccess(Void output) {
                callbackContext.success("setBgTrackingMode OK");
            }

            @Override
            public void onError() {
                callbackContext.error("Invalid trackingMode");
            }
        });
    }

    private void handleRecordEvent(JSONArray data, final CallbackContext callbackContext, Executor backgroundThread) throws JSONException {

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
    }

    private void handleInitialize(final CallbackContext callbackContext, Executor mainThread, Context context) {
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
