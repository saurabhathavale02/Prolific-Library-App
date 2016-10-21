package com.example.saurabh.prolificlib.data.module;


import com.example.saurabh.prolificlib.presenter.AddNewBookContract;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saurabh on 10/18/16.
 */
@Module
public class AddNewBookModule
{
    private final AddNewBookContract.View mView;

    public AddNewBookModule(AddNewBookContract.View mView)
    {
        this.mView = mView;
    }


    @Provides
    @CustomScope
    AddNewBookContract.View providesAddNewBookContractView()
    {
        return mView;
    }
}
