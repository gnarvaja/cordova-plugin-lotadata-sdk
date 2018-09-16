package com.lotadata.moments.plugin.actions.callback;

import org.apache.cordova.CallbackContext;

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
}
