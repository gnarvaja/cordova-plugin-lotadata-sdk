package com.lotadata.moments.plugin.executors;

import com.lotadata.moments.plugin.actions.Action;

import java.util.concurrent.ExecutorService;

public class BackgroundThreadExecutor implements Executor {

    private ExecutorService executorService;

    public BackgroundThreadExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(Action action) {
        executorService.execute(action);
    }
}
