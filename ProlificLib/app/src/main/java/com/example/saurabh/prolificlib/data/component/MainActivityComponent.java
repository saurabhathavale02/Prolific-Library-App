package com.example.saurabh.prolificlib.data.component;


import com.example.saurabh.prolificlib.data.module.MainActivityModule;
import com.example.saurabh.prolificlib.screen.MainActivity;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Component;

/**
 * Created by saurabh on 10/19/16.
 */
@CustomScope
@Component(dependencies = NetComponent.class,modules = MainActivityModule.class)
public interface MainActivityComponent
{
    void inject(MainActivity activity);
}
