package com.example.admin.di.screens.questionlist.questions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionsListResponseSchema {

    @SerializedName("items")
    private final List<com.example.admin.di.screens.questionlist.questions.Question> mQuestions;

    public QuestionsListResponseSchema(List<com.example.admin.di.screens.questionlist.questions.Question> questions) {
        mQuestions = questions;
    }

    public List<com.example.admin.di.screens.questionlist.questions.Question> getQuestions() {
        return mQuestions;
    }
}

