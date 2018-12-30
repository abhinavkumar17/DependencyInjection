package com.example.admin.di.questiondetails;


import com.example.admin.di.common.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    interface Listener {
        // currently no user actions
    }

    void bindQuestion(com.example.admin.di.screens.questionlist.questions.QuestionWithBody question);
}
