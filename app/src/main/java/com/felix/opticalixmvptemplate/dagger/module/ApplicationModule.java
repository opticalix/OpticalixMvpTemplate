package com.felix.opticalixmvptemplate.dagger.module;

import android.app.Application;
import android.content.Context;

import com.felix.opticalixmvptemplate.base.Navigator;
import com.felix.opticalixmvptemplate.base.UIThread;
import com.felix.opticalixmvptemplate.base.WorkThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Felix on 2016/4/19.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    WorkThread provideWorkThread(WorkThread workThread){
        return workThread;
    }

    @Provides
    @Singleton
    UIThread provideMainScheduler(UIThread uiThread){
        return uiThread;
    }
}
