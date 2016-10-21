package com.example.saurabh.prolificlib.presenter;

/**
 * Created by saurabh on 10/17/16.
 */

public interface BookDescriptionContract
{
    interface View
    {

        void deleteBooks(String url);

        void showError(String message);

        void showComplete();



    }

    interface Presenter
    {
        void DeleteBookCall(String url);
    }
}
