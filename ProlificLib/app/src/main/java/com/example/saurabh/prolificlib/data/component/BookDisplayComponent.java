package com.example.saurabh.prolificlib.data.component;


import com.example.saurabh.prolificlib.data.module.BookDisplayModule;
import com.example.saurabh.prolificlib.screen.BookDisplayFragment;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Component;

/**
 * Created by saurabh on 10/17/16.
 */


@CustomScope
@Component(dependencies = NetComponent.class, modules = BookDisplayModule.class)
public interface BookDisplayComponent
{
    void inject(BookDisplayFragment fragment);
}

