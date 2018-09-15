package com.lotadata.moments.plugin.actions;

public interface Action<I, O> extends Runnable {

    interface Callback<O> {
        void onSuccess(O output);
        void onError();
    }

    void doAction(I input, Callback<O> callback);
}
