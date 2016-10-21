package com.example.saurabh.prolificlib.data.module;


import com.example.saurabh.prolificlib.presenter.BookDescriptionContract;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saurabh on 10/17/16.
 */

@Module
public class BookDescriptionModule
{
    private final BookDescriptionContract.View mView;

    public BookDescriptionModule(BookDescriptionContract.View mView)
    {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    BookDescriptionContract.View providesBookDescriptionContractView()
    {
        return mView;
    }
}
