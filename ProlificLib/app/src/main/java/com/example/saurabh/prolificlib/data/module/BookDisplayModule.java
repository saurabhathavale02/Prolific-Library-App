package com.example.saurabh.prolificlib.data.module;


import com.example.saurabh.prolificlib.presenter.BookDisplayContract;
import com.example.saurabh.prolificlib.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saurabh on 10/17/16.
 */
@Module
public class BookDisplayModule
{
    private final BookDisplayContract.View mView;

    public BookDisplayModule(BookDisplayContract.View mView)
    {
        this.mView = mView;
    }


    @Provides
    @CustomScope
    BookDisplayContract.View providesBookDisplayContractView()
    {
        return mView;
    }

}
