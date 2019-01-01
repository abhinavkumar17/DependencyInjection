package com.example.admin.di.puredependency;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.admin.di.common.ViewMvcFactory;
import com.example.admin.di.common.dialog.DialogsManager;
import com.example.admin.di.questiondetails.FetchQuestionDetailsUseCase;
import com.example.admin.di.questionlistview.FetchQuestionsListUseCase;

public class PresentationCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private FragmentManager mFragmentManager;
    private LayoutInflater mLayoutInflater;

    public PresentationCompositionRoot(CompositionRoot compositionRoot,
                                       FragmentManager fragmentManager,
                                       LayoutInflater layoutInflater) {
        mCompositionRoot = compositionRoot;
        mFragmentManager = fragmentManager;
        mLayoutInflater = layoutInflater;
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(mFragmentManager);
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return mCompositionRoot.getFetchQuestionDetailsUseCase();
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return mCompositionRoot.getFetchQuestionsListUseCase();
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(mLayoutInflater);
    }
}
