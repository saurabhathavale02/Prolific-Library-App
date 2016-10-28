package com.example.saurabh.prolificlib.presenter;


import com.example.saurabh.prolificlib.data.ResponseParameter;

/**
 * Created by saurabh on 10/19/16.
 */

public interface MainActivityContract
{
    interface View
    {
        void displayBookDescription(ResponseParameter responseParameter);

        void checkoutBook( String url, String updatedcheckby, String updatedauthor, String updatetitle, String updatepublisher, String updatedcategory);

        Integer checkLayout();

        void deleteAllBook();

        void showError(String message);

        void showDeleteAllComplete();

        void showCheckoutComplete();

        void onOnePanelCall();

    }

    interface Presenter
    {
        void DeleteAllbook();
        void CheckOutBook( String url, String updatedcheckby, String updatedauthor, String updatetitle, String updatepublisher, String updatedcategory);

    }
}
