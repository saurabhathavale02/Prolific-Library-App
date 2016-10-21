package com.example.saurabh.prolificlib.presenter;

/**
 * Created by saurabh on 10/19/16.
 */

public interface SplashScreenContract
{
    interface View
    {
        void WaitForSecond();

        void showError(String message);

        void showComplete();


    }

    interface Presenter
    {
        void WaitForSecond();
    }
}
