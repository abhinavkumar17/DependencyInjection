package com.example.admin.di.screens.screens.questiondetails;

import com.example.admin.di.screens.screens.common.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    interface Listener {
        // currently no user actions
    }

    void bindQuestion(com.example.admin.di.screens.questionlist.questions.QuestionWithBody question);
}
