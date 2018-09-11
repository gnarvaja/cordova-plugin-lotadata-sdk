package com.lotadata.moments.plugin;

import org.apache.cordova.*;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import java.util.Set;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

import android.location.Location;
import android.Manifest;

import android.util.Log;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.lotadata.moments.MomentsClient;
import com.lotadata.moments.Moments;
import com.lotadata.moments.TrackingMode;

public class MomentsPlugin extends CordovaPlugin {
    private static final String TAG = "MomentsPlugin";

    private Moments momentsClient = null;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        if (action.equals("initialize")) {
            if (momentsClient != null) {
                momentsClient.disconnect();
                momentsClient = null;
            }

            Log.i(TAG, "Initializing MomentsPlugin - In new Thread");
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    Log.i(TAG, "Permissions OK, not getInstance disabled");
                    momentsClient = MomentsClient.getInstance(cordova.getActivity());
                    if (momentsClient != null) {
                        if (momentsClient.isConnected()) {
                            callbackContext.success("isConnected");
                        } else {
                            callbackContext.success("is Not Connected");
                        }
                    } else {
                        callbackContext.error("Error, permission OK but momentsClient still == null");
                    }
                }
            });
            return true;
        } else if (action.equals("recordEvent")) {
            if (momentsClient == null) {
                callbackContext.error("Not initialized!");
            } else {
                final String eventName = data.getString(0);
                if (data.length() > 1) {
                    final Double eventData = data.getDouble(1);
                    momentsClient.recordEvent(eventName, eventData);
                } else {
                    momentsClient.recordEvent(eventName);
                }
                callbackContext.success("Event recorded");
            }
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

    public void onDestroy() {
        if (momentsClient != null) {
            momentsClient.disconnect();
            momentsClient = null;
        }
        super.onDestroy();
    }
}
