package com.lotadata.moments.plugin.actions.callback;

import org.apache.cordova.PluginResult;

public interface Callback {
    void onSuccess(String message);
    void onError(String error);

    void sendPluginResult(PluginResult result);
}
