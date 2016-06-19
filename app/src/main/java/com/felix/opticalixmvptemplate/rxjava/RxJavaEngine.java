package com.felix.opticalixmvptemplate.rxjava;

import android.content.Context;

import com.felix.opticalixmvptemplate.utils.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Felix on 2016/6/19.
 * https://gank.io/post/560e15be2dca930e00da1083#toc_2
 * 如何简化异步任务流程。
 * 能否模板化
 */
public class RxJavaEngine {
    private Context mContext;
    private static final String TAG = "RxJavaEngine";

    public RxJavaEngine(Context context) {
        mContext = context;
    }

    public RxJavaEngine() {
    }

    private <T> Subscriber<T> provideSubscriber(){
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(T t) {
                System.out.println("onNext[T="+t+"]");
            }
        };
    }
    public void test(){
        //create Observable
        String[] strings = new String[]{"1", "2", "3"};
        Observable<String> stringObservable = Observable.from(strings);
        Observable<Integer> integerObservable = Observable.just(1, 2);
        Observable<Integer> customObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Integer[] integers = new Integer[]{4,5,6,7};
                for (Integer i:integers){
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });

        //subscribe
        stringObservable.subscribe(this.provideSubscriber());
        integerObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("onNext[int=" + integer+"]");
                int a=1/0;
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("onError");
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("onComplete");
            }
        });
        customObservable.subscribe(this.<Integer>provideSubscriber());

        //scheduler

        //map..
    }
}
