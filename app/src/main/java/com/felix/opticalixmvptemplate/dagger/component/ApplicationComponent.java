package com.felix.opticalixmvptemplate.dagger.component;

import android.content.Context;

import com.felix.opticalixmvptemplate.base.BaseActivity;
import com.felix.opticalixmvptemplate.base.Navigator;
import com.felix.opticalixmvptemplate.base.UIThread;
import com.felix.opticalixmvptemplate.base.WorkThread;
import com.felix.opticalixmvptemplate.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 * Created by Felix on 2016/4/24.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    public Context provideApplicationContext();
    Navigator provideNavigator();
    WorkThread provideWorkThread(WorkThread workThread);
    UIThread provideMainScheduler(UIThread uiThread);
}
