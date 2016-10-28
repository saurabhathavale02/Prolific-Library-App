package com.example.saurabh.prolificlib.presenter;

import com.example.saurabh.prolificlib.data.ResponseParameter;

/**
 * Created by saurabh on 10/17/16.
 */

public interface BookDescriptionContract
{
    interface View
    {

        void deleteBooks(String url, ResponseParameter responseParameter);

        void showError(String message);

        void showComplete(ResponseParameter responseParameter);



    }

    interface Presenter
    {
        void DeleteBookCall(String url, ResponseParameter responseParameter);
    }
}
