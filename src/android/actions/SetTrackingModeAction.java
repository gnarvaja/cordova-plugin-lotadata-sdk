package com.lotadata.moments.plugin.actions;

import com.lotadata.moments.Moments;
import com.lotadata.moments.TrackingMode;
import com.lotadata.moments.plugin.executors.Executor;

public class SetTrackingModeAction implements Action<String, Void> {

    public enum STATE {
        FOREGROUND,
        BACKGROUND
    }

    private Executor executor;
    private Moments momentsClient;
    private STATE state;

    private Callback<Void> callback;
    private String trackingMode;

    public SetTrackingModeAction(Executor executor, Moments momentsClient, STATE state) {
        this.executor = executor;
        this.momentsClient = momentsClient;
        this.state = state;
    }

    @Override
    public void doAction(String trackingMode, Callback<Void> callback) {
        this.callback = callback;
        this.trackingMode = trackingMode;
        executor.execute(this);
    }

    @Override
    public void run() {
        if (momentsClient == null) {
            callback.onError();
        } else {
            try {
                TrackingMode mode = TrackingMode.valueOf(trackingMode);
                switch (state) {
                    case FOREGROUND:
                        momentsClient.setFgTrackingMode(mode);
                        break;
                    case BACKGROUND:
                        momentsClient.setBgTrackingMode(mode);
                }
                callback.onSuccess(null);
            } catch (IllegalArgumentException err) {
                callback.onError();
            }
        }
    }
}
