package com.example.admin.di;

import android.app.Application;

import com.example.admin.di.questions.StackOverflowApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    private Retrofit mRetrofit;
    private StackOverflowApi mStackoverflowApi;


    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public StackOverflowApi getStackoverflowApi() {
        if (mStackoverflowApi == null) {
            mStackoverflowApi = getRetrofit().create(StackOverflowApi.class);
        }
        return mStackoverflowApi;
    }
}
