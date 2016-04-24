package com.felix.opticalixmvptemplate.base;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Felix on 2016/4/24.
 */
public class UIThread implements IOpxThread {

    @Inject
    public UIThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Executor getExecutor() {
        throw new RuntimeException("Can not get executor from UIThread!");
    }
}
