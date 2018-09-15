package com.lotadata.moments.plugin.actions;

import com.lotadata.moments.Moments;
import com.lotadata.moments.plugin.executors.Executor;
import com.lotadata.moments.plugin.models.Event;

public class RecordEventAction implements Action<Event<Double>, Void> {

    private Executor executor;

    private Moments momentsClient;
    private Callback<Void> callback;
    private Event<Double> event;

    public RecordEventAction(Executor executor, Moments momentsClient) {
        this.executor = executor;
        this.momentsClient = momentsClient;
    }

    @Override
    public void doAction(Event<Double> input, Callback<Void> callback) {
        this.event = input;
        this.callback = callback;
        executor.execute(this);
    }

    @Override
    public void run() {
        if (momentsClient == null) {
            callback.onError();
        } else {
            final String eventName = event.getEventName();

            if (event.hasData()) {
                final Double eventData = event.getData();
                momentsClient.recordEvent(eventName, eventData);
            } else {
                momentsClient.recordEvent(eventName);
            }

            callback.onSuccess(null);
        }
    }
}
