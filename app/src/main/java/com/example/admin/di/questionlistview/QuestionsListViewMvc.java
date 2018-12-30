package com.example.admin.di.questionlistview;


import com.example.admin.di.common.ObservableViewMvc;
import com.example.admin.di.questions.Question;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestions(List<Question> questions);
}
