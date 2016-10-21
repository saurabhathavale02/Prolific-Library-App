package com.example.saurabh.prolificlib.presenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by saurabh on 10/19/16.
 */

public class SplashScreenPresenter implements SplashScreenContract.Presenter
{
    SplashScreenContract.View mView;

    @Inject
    public SplashScreenPresenter(SplashScreenContract.View mView)
    {
        this.mView = mView;
    }
    @Override
    public void WaitForSecond()
    {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {


                        subscriber.onNext("complete");

            }
        })
                .debounce(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String s)
                    {
                     mView.showComplete();
                    }
                });
    }

}
