package com.lotadata.moments.plugin;

import android.content.Context;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.actions.Action;
import com.lotadata.moments.plugin.actions.BestKnownLocationAction;
import com.lotadata.moments.plugin.actions.InitializeAction;
import com.lotadata.moments.plugin.actions.RecordEventAction;
import com.lotadata.moments.plugin.actions.SetTrackingModeAction;
import com.lotadata.moments.plugin.actions.callback.Callback;
import com.lotadata.moments.plugin.actions.callback.CallbackContextWrapper;
import com.lotadata.moments.plugin.executors.BackgroundThreadExecutor;
import com.lotadata.moments.plugin.executors.Executor;
import com.lotadata.moments.plugin.executors.MainThreadExecutor;
import com.lotadata.moments.plugin.models.Event;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class MomentsPlugin extends CordovaPlugin {

    private Moments momentsClient = null;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        Executor mainThread = new MainThreadExecutor(cordova.getActivity());
        Executor backgroundThread = new BackgroundThreadExecutor(cordova.getThreadPool());
        Callback callback = new CallbackContextWrapper(callbackContext);
        Context context = cordova.getActivity();

        if (action.equals("initialize")) {
            Action initializeAction = new InitializeAction(mainThread, context, momentsClient, callback);
            initializeAction.doAction();
            return true;
        } else if (action.equals("recordEvent")) {
            Event<Double> event = new Event<Double>(data.getString(0));
            try {
                event.setData(data.getDouble(1));
            } catch (JSONException ex) { }
            Action recordEventAction = new RecordEventAction(backgroundThread, momentsClient, event, callback);
            recordEventAction.doAction();
            return true;
        } else if (action.equals("setFgTrackingMode")) {
            String trackingMode = data.getString(0);
            Action setFgTrackingModeAction = new SetTrackingModeAction(backgroundThread, momentsClient, SetTrackingModeAction.STATE.FOREGROUND, trackingMode, callback);
            setFgTrackingModeAction.doAction();
            return true;
        } else if (action.equals("setBgTrackingMode")) {
            String trackingMode = data.getString(0);
            Action setBgTrackingModeAction = new SetTrackingModeAction(backgroundThread, momentsClient, SetTrackingModeAction.STATE.BACKGROUND, trackingMode, callback);
            setBgTrackingModeAction.doAction();
            return true;
        } else if (action.equals("bestKnownLocation")) {
            Action bestKnownLocationAction = new BestKnownLocationAction(backgroundThread, momentsClient, callback);
            bestKnownLocationAction.doAction();
            return true;
        } else {
            return false;
        }
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
