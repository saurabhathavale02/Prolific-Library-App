package com.example.saurabh.prolificlib.presenter;


import com.example.saurabh.prolificlib.data.ResponseParameter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saurabh on 10/17/16.
 */

public class BookDisplayPresenter implements BookDisplayContract.Presenter
{

    public Retrofit retrofit;
    BookDisplayContract.View mView;

    @Inject
    public BookDisplayPresenter(Retrofit retrofit, BookDisplayContract.View mView)
    {
        this.retrofit = retrofit;
        this.mView = mView;
    }
    @Override
    public void loadBooks()
    {
        retrofit.create(BookDisplayPresenter.GetService.class).getPostList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<ResponseParameter>>() {
                    @Override
                    public void onCompleted()
                    {

                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ResponseParameter> posts)
                    {
                        mView.showBooks(posts);
                    }
                });

    }



    public interface GetService
    {
        @GET("books")
        Observable<List<ResponseParameter>> getPostList();
    }
}
