package com.example.admin.di.common;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.admin.di.questiondetails.QuestionDetailsViewMvc;
import com.example.admin.di.questiondetails.QuestionDetailsViewMvcImpl;
import com.example.admin.di.questionlistview.QuestionsListViewMvc;
import com.example.admin.di.questionlistview.QuestionsListViewMvcImpl;

public class ViewMvcFactory {
    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     * @param mvcViewClass the class of the required MVC view
     * @param container this container will be used as MVC view's parent. See {@link LayoutInflater#inflate(int, ViewGroup)}
     * @param <T> the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ViewMvc> T newInstance(Class<T> mvcViewClass, @Nullable ViewGroup container) {

        ViewMvc viewMvc;

        if (mvcViewClass == QuestionsListViewMvc.class) {
            viewMvc = new QuestionsListViewMvcImpl(mLayoutInflater, container);
        }
        else if (mvcViewClass == QuestionDetailsViewMvc.class) {
            viewMvc = new QuestionDetailsViewMvcImpl(mLayoutInflater, container);
        }
        else {
            throw new IllegalArgumentException("unsupported MVC view class " + mvcViewClass);
        }

        //noinspection unchecked
        return (T) viewMvc;
    }
}
