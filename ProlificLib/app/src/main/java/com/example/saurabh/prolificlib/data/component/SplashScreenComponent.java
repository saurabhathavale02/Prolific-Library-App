package com.example.saurabh.prolificlib.data.component;


import com.example.saurabh.prolificlib.data.module.SplashScreenModule;
import com.example.saurabh.prolificlib.screen.Splash_Screen;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Component;

/**
 * Created by saurabh on 10/19/16.
 */

@CustomScope
@Component(modules = SplashScreenModule.class)
public interface SplashScreenComponent
{
    void inject(Splash_Screen activity);
}
