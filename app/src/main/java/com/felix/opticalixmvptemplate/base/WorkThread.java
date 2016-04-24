package com.felix.opticalixmvptemplate.base;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Felix on 2016/4/19.
 */
public class WorkThread extends ThreadPoolExecutor implements IOpxThread{
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 10;
    private static final int KEEP_ALIVE_TIME = 10;

    private static final BlockingQueue<Runnable> mWorkQueue = new LinkedBlockingQueue<>();
    private static final WorkThreadFactory mWorkThreadFactory = new WorkThreadFactory();

    @Inject
    public WorkThread() {
        super(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, mWorkQueue, mWorkThreadFactory);
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.from(this);
    }

    @Override
    public Executor getExecutor() {
        return this;
    }

    private static class WorkThreadFactory implements ThreadFactory {
        private static final String THREAD_NAME = "android_";
        private AtomicInteger mAtomicInteger = new AtomicInteger(0);

        @Override public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, THREAD_NAME + mAtomicInteger.getAndIncrement());
        }
    }
}
