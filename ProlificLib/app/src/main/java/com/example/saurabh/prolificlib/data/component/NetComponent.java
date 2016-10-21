package com.example.saurabh.prolificlib.data.component;


import com.example.saurabh.prolificlib.data.module.AppModule;
import com.example.saurabh.prolificlib.data.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by saurabh on 10/17/16.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent
{
    // downstream components need these exposed with the return type
    // method name does not really matter
    Retrofit retrofit();
}
