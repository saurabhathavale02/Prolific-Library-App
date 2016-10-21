package com.example.saurabh.prolificlib.presenter;


import com.example.saurabh.prolificlib.data.ResponseParameter;

import java.util.List;

/**
 * Created by saurabh on 10/17/16.
 */

public interface BookDisplayContract
{
    interface View
    {

        void showBooks(List<ResponseParameter> responseParameters);

        void showError(String message);

        void showComplete();

        void initRecyclerView();

        void UpdateBookList();


    }

    interface Presenter
    {
        void loadBooks();

    }
}
