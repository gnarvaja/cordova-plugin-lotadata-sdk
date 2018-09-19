package com.lotadata.moments.plugin.actions;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.actions.callback.Callback;
import com.lotadata.moments.plugin.executors.Executor;
import com.lotadata.moments.plugin.models.Event;

public class RecordEventAction implements Action {

    private Executor executor;

    private Moments momentsClient;
    private Callback callback;
    private Event<Double> event;

    public RecordEventAction(Executor executor, Moments momentsClient, Event<Double> event, Callback callback) {
        this.executor = executor;
        this.momentsClient = momentsClient;
        this.event = event;
        this.callback = callback;
    }

    @Override
    public void doAction() {
        executor.execute(this);
    }

    @Override
    public void run() {
        if (momentsClient == null) {
            callback.onError("MomentsClient not initialized");
        } else {
            final String eventName = event.getEventName();

            if (event.hasData()) {
                final Double eventData = event.getData();
                momentsClient.recordEvent(eventName, eventData);
            } else {
                momentsClient.recordEvent(eventName);
            }

            callback.onSuccess("Event recorded");
        }
    }
}
