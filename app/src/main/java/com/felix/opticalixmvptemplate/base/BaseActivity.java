package com.felix.opticalixmvptemplate.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.felix.opticalixmvptemplate.dagger.component.ApplicationComponent;

import javax.inject.Inject;

/**
 * Created by Felix on 2016/4/24.
 */
public class BaseActivity extends AppCompatActivity {
    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApplicationComponent().inject(this);
    }

    private ApplicationComponent getApplicationComponent(){
        BaseApplication application = (BaseApplication) getApplication();
        return application.getApplicationComponent();
    }
}
