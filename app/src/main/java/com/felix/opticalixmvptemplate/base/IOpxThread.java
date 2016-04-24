package com.felix.opticalixmvptemplate.base;

import java.util.concurrent.Executor;

import rx.Scheduler;

/**
 * Created by Felix on 2016/4/24.
 */
public interface IOpxThread {
    Scheduler getScheduler();
    Executor getExecutor();
}
