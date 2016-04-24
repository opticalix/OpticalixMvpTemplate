package com.felix.opticalixmvptemplate.base;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 1.实体类直接写singleton/Module的provide方法加singleton
 * 2.在Component加singleton
 * Created by Felix on 2016/4/19.
 */
@Singleton
public class Navigator {
    @Inject
    public Navigator() {
    }
}
