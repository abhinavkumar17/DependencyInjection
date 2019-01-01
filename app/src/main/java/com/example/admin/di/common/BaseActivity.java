package com.example.admin.di.common;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.admin.di.MyApplication;
import com.example.admin.di.puredependency.CompositionRoot;
import com.example.admin.di.puredependency.PresentationCompositionRoot;

public class BaseActivity extends AppCompatActivity {
    private PresentationCompositionRoot mPresentationCompositionRoot;

    protected CompositionRoot getAppCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }


    protected PresentationCompositionRoot getCompositionRoot() {
        if (mPresentationCompositionRoot == null) {
            mPresentationCompositionRoot = new PresentationCompositionRoot(
                    getAppCompositionRoot(),
                    getSupportFragmentManager(),
                    LayoutInflater.from(this)
            );
        }

        return mPresentationCompositionRoot;
    }
}
