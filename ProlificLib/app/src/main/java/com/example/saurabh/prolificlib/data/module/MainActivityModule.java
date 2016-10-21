package com.example.saurabh.prolificlib.data.module;


import com.example.saurabh.prolificlib.presenter.MainActivityContract;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saurabh on 10/19/16.
 */
@Module
public class MainActivityModule
{
    private final MainActivityContract.View mView;


    public MainActivityModule(MainActivityContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    MainActivityContract.View providesMainActivityContractView() {
        return mView;
    }
}
