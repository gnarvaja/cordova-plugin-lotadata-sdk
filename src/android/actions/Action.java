package com.lotadata.moments.plugin.actions;

import com.lotadata.moments.Moments;

public interface Action extends Runnable {

    interface PluginView {
        void setMomentsClient(Moments momentsClient);
    }

    void doAction();
}
