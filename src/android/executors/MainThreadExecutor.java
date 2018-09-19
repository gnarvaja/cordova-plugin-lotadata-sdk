package com.lotadata.moments.plugin.executors;

import android.app.Activity;

import com.lotadata.moments.plugin.actions.Action;

public class MainThreadExecutor implements Executor {

    private Activity activity;

    public MainThreadExecutor(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void execute(Action action) {
        activity.runOnUiThread(action);
    }
}
