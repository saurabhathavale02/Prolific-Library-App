package com.example.saurabh.prolificlib.presenter;


import com.example.saurabh.prolificlib.data.ResponseParameter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saurabh on 10/19/16.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter
{

    public Retrofit retrofit;
    MainActivityContract.View mView;

    @Inject
    public MainActivityPresenter(Retrofit retrofit, MainActivityContract.View mView)
    {
        this.retrofit = retrofit;
        this.mView = mView;
    }
    @Override
    public void DeleteAllbook()
    {
        retrofit.create(MainActivityPresenter.ServerService.class).DeleteAllBooks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Void>()
                {
                    @Override
                    public void onCompleted()

                    {
                        mView.showDeleteAllComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }


                });

    }

    @Override
    public void CheckOutBook(String url, String lastcheckedby)
    {
        retrofit.create(MainActivityPresenter.ServerService.class).UpdateBookData(url,lastcheckedby).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseParameter>() {
                    @Override
                    public void onCompleted()
                    {

                        mView.showCheckoutComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseParameter posts)
                    {
                       // mView.showCheckoutComplete();
                    }
                });


    }

    public interface ServerService
    {
        @DELETE("clean/")
        Observable<Void> DeleteAllBooks();

        @FormUrlEncoded
        @PUT("{url}")
        Observable<ResponseParameter> UpdateBookData(@Path("url") String url, @Field("lastCheckedOutBy") String lastcheckedoutby);
    }

}
