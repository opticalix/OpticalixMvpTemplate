package com.felix.opticalixmvptemplate.base;

import android.app.Application;

import com.felix.opticalixmvptemplate.dagger.component.ApplicationComponent;
import com.felix.opticalixmvptemplate.dagger.component.DaggerApplicationComponent;
import com.felix.opticalixmvptemplate.dagger.module.ApplicationModule;

/**
 * Created by Felix on 2016/4/19.
 */
public class BaseApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
