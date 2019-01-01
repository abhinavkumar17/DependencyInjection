package com.example.admin.di;

import android.app.Application;

import com.example.admin.di.puredependency.CompositionRoot;

public class MyApplication extends Application {

    CompositionRoot compositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        compositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }


}
