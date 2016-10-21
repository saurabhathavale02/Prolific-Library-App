package com.example.saurabh.prolificlib;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.saurabh.prolificlib.data.component.DaggerNetComponent;
import com.example.saurabh.prolificlib.data.component.NetComponent;
import com.example.saurabh.prolificlib.data.module.AppModule;
import com.example.saurabh.prolificlib.data.module.NetModule;


/**
 * Created by saurabh on 10/17/16.
 */

public class App extends Application {

    private NetComponent mNetComponent;
    public Integer NumberOfBooks=0;
    public int ChangeState=0;
    @Override
    public void onCreate()
    {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://prolific-interview.herokuapp.com/57ffb26db56b840009eddc21/"))
                .build();
        NumberOfBooks=0;

    }


    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
