package com.lotadata.moments.plugin.actions;

import android.content.Context;

import com.lotadata.moments.Moments;
import com.lotadata.moments.MomentsClient;
import com.lotadata.moments.plugin.executors.Executor;

public class InitializeAction implements Action<Void, Moments> {

    private Context context;

    private Executor executor;
    private Callback<Moments> callback;

    private Moments momentsClient;

    public InitializeAction(Executor executor, Context context, Moments momentsClient) {
        this.executor = executor;
        this.context = context;
        this.momentsClient = momentsClient;
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
                callback.onSuccess(momentsClient);
            } else {
                callback.onSuccess(momentsClient);
            }
        } else {
            callback.onError();
        }
    }

    @Override
    public void doAction(Void input, Callback<Moments> callback) {
        this.callback = callback;
        executor.execute(this);
    }
}
