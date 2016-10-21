package com.example.saurabh.prolificlib.presenter;

/**
 * Created by saurabh on 10/18/16.
 */

public interface AddNewBookContract
{

    interface View
    {

        void addnewbook(String author, String categories, String title, String publisher);

        void showError(String message);

        void showComplete();
        Integer validdataentered(String author, String categories, String title, String publisher);



    }

    interface Presenter
    {
        void AddNewBook(String author, String categories, String title, String publisher);
        Integer ValidDataEntered(String author, String categories, String title, String publisher);

    }
}
