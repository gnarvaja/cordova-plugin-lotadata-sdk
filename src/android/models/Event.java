package com.lotadata.moments.plugin.models;

public class Event<T> {

    private String eventName;
    private T data;
    private boolean hasData;

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if (data != null) {
            this.data = data;
            hasData = true;
        }
    }

    public boolean hasData() {
        return hasData;
    }
}
