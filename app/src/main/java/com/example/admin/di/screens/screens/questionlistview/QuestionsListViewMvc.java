package com.example.admin.di.screens.screens.questionlistview;

import com.example.admin.di.screens.screens.common.ObservableViewMvc;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    interface Listener {
        void onQuestionClicked(com.example.admin.di.screens.questionlist.questions.Question question);
    }

    void bindQuestions(List<com.example.admin.di.screens.questionlist.questions.Question> questions);
}
