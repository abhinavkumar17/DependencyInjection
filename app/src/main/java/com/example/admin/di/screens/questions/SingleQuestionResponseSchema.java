package com.example.admin.di.screens.questionlist.questions;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class SingleQuestionResponseSchema {
    @SerializedName("items")
    private final List<com.example.admin.di.screens.questionlist.questions.QuestionWithBody> mQuestions;

    public SingleQuestionResponseSchema(com.example.admin.di.screens.questionlist.questions.QuestionWithBody question) {
        mQuestions = Collections.singletonList(question);
    }

    public com.example.admin.di.screens.questionlist.questions.QuestionWithBody getQuestion() {
        return mQuestions.get(0);
    }
}
