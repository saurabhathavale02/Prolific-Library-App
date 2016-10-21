package com.example.saurabh.prolificlib.presenter;


import com.example.saurabh.prolificlib.data.ResponseParameter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saurabh on 10/18/16.
 */

public class AddNewBookPresenter implements AddNewBookContract.Presenter
{
    public Retrofit retrofit;
    AddNewBookContract.View mView;

    @Inject
    public AddNewBookPresenter(Retrofit retrofit, AddNewBookContract.View mView)
    {
        this.retrofit = retrofit;
        this.mView = mView;
    }


    @Override
    public Integer ValidDataEntered(String author, String categories, String title, String publisher)
    {
        if(title.trim().isEmpty()==true)
        {
            return 1;
        }

        else if(author.trim().isEmpty()==true)
        {
            return 2;
        }

        else if(publisher.trim().isEmpty()==true)
        {
            return 3;
        }

        else if(categories.trim().isEmpty()==true)
        {
            return 4;
        }

        return 0;
    }

    @Override
    public void AddNewBook(String author, String categories,String title,String publisher)
    {
        retrofit.create(AddNewBookPresenter.AddNewBookService.class).AddBook(author,categories,title,publisher).subscribeOn(Schedulers.io())
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



    public interface AddNewBookService {
        @FormUrlEncoded
        @POST("books/")
        Observable<ResponseParameter> AddBook(@Field("author") String author, @Field("categories") String categories, @Field("title") String title, @Field("publisher") String publisher);
    }
}
