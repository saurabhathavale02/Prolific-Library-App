package com.example.saurabh.prolificlib.screen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.saurabh.prolificlib.R;
import com.example.saurabh.prolificlib.data.component.DaggerSplashScreenComponent;
import com.example.saurabh.prolificlib.data.module.SplashScreenModule;
import com.example.saurabh.prolificlib.presenter.SplashScreenContract;
import com.example.saurabh.prolificlib.presenter.SplashScreenPresenter;

import javax.inject.Inject;

public class Splash_Screen extends AppCompatActivity implements SplashScreenContract.View
{

    @Inject
    SplashScreenPresenter splashScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        DaggerSplashScreenComponent.builder()
                .splashScreenModule(new SplashScreenModule(this))
                .build().inject(this);

        WaitForSecond();
    }

    @Override
    public void WaitForSecond() {

        splashScreenPresenter.WaitForSecond();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete()
    {
        Intent intent =new Intent(Splash_Screen.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
