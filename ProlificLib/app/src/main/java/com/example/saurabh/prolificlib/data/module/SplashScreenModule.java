package com.example.saurabh.prolificlib.data.module;


import com.example.saurabh.prolificlib.presenter.SplashScreenContract;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saurabh on 10/19/16.
 */
@Module
public class SplashScreenModule
{
    private final SplashScreenContract.View mView;


    public SplashScreenModule(SplashScreenContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    SplashScreenContract.View providesSplashScreenContractView() {
        return mView;
    }
}
