package com.lotadata.moments.plugin.actions;

import com.lotadata.moments.Moments;
import com.lotadata.moments.TrackingMode;
import com.lotadata.moments.plugin.actions.callback.Callback;
import com.lotadata.moments.plugin.executors.Executor;

public class SetTrackingModeAction implements Action {

    public enum STATE {
        FOREGROUND,
        BACKGROUND
    }

    private Executor executor;
    private Moments momentsClient;
    private STATE state;

    private Callback callback;
    private String trackingMode;

    public SetTrackingModeAction(Executor executor, Moments momentsClient, STATE state, String trackingMode, Callback callback) {
        this.executor = executor;
        this.momentsClient = momentsClient;
        this.state = state;
        this.callback = callback;
        this.trackingMode = trackingMode;
    }

    @Override
    public void doAction() {
        executor.execute(this);
    }

    @Override
    public void run() {
        if (momentsClient == null) {
            callback.onError("Not initialized");
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
                callback.onSuccess("setTrackingMode OK - " + state.name());
            } catch (IllegalArgumentException err) {
                callback.onError("Invalid trackingMode");
            }
        }
    }
}
