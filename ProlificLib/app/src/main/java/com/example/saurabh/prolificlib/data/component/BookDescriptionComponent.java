package com.example.saurabh.prolificlib.data.component;


import com.example.saurabh.prolificlib.data.module.BookDescriptionModule;
import com.example.saurabh.prolificlib.screen.BookDescriptionFragment;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Component;

/**
 * Created by saurabh on 10/17/16.
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = BookDescriptionModule.class)
public interface BookDescriptionComponent
{
    void inject(BookDescriptionFragment fragment);
}
