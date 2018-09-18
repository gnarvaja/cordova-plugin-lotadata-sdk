package com.lotadata.moments.plugin.actions.callback;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

public class CallbackContextWrapper implements Callback {

    private CallbackContext callbackContext;

    public CallbackContextWrapper(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    @Override
    public void onSuccess(String message) {
        callbackContext.success(message);
    }

    @Override
    public void onError(String error) {
        callbackContext.error(error);
    }

    @Override
    public void sendPluginResult(PluginResult result) {
        callbackContext.sendPluginResult(result);
    }
}
