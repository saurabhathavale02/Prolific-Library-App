package com.example.saurabh.prolificlib.data.component;


import com.example.saurabh.prolificlib.data.module.AddNewBookModule;
import com.example.saurabh.prolificlib.screen.AddNewBook;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Component;

/**
 * Created by saurabh on 10/18/16.
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = AddNewBookModule.class)
public interface AddNewBookComponent
{
    void inject(AddNewBook activity);
}
