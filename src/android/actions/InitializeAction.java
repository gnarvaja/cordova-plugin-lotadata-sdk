package com.lotadata.moments.plugin.actions;

import android.content.Context;

import com.lotadata.moments.Moments;
import com.lotadata.moments.MomentsClient;
import com.lotadata.moments.plugin.actions.callback.Callback;
import com.lotadata.moments.plugin.executors.Executor;

public class InitializeAction implements Action {

    private Context context;

    private Executor executor;
    private Callback callback;

    private Moments momentsClient;

    public InitializeAction(Executor executor, Context context, Moments momentsClient, Callback callback) {
        this.executor = executor;
        this.context = context;
        this.momentsClient = momentsClient;
        this.callback = callback;
    }

    @Override
    public void run() {
        if (momentsClient != null) {
            momentsClient.disconnect();
            momentsClient = null;
        }

        momentsClient = MomentsClient.getInstance(context);
        if (momentsClient != null) {
            if (momentsClient.isConnected()) {
                callback.onSuccess("isConnected");
            } else {
                callback.onSuccess("isConnected");
            }
        } else {
            callback.onError("Error, permission OK but momentsClient still == null");
        }
    }

    @Override
    public void doAction() {
        executor.execute(this);
    }
}
