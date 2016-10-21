package com.example.saurabh.prolificlib.presenter;


import com.example.saurabh.prolificlib.data.ResponseParameter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saurabh on 10/17/16.
 */

public class BookDescriptionPresenter  implements BookDescriptionContract.Presenter
{


    public Retrofit retrofit;
    BookDescriptionContract.View mView;

    @Inject
    public BookDescriptionPresenter(Retrofit retrofit, BookDescriptionContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void DeleteBookCall(String url)
    {
        retrofit.create(BookDescriptionPresenter.DeleteService.class).DeleteBook(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseParameter>() {
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
                    public void onNext(ResponseParameter posts)
                    {
                       // mView.showComplete();
                    }
                });

    }

    public interface DeleteService
    {
        @DELETE("{url}")
        Observable<ResponseParameter> DeleteBook(@Path("url") String url);
    }

}
