package com.example.admin.di.screens.questionlist.questions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackOverflowApi {

    @GET("/questions?order=desc&sort=activity&site=stackoverflow")
    Call<com.example.admin.di.screens.questionlist.questions.QuestionsListResponseSchema> lastActiveQuestions(@Query("pagesize") Integer pageSize);

    @GET("/questions/{questionId}?site=stackoverflow&filter=withbody")
    Call<com.example.admin.di.screens.questionlist.questions.SingleQuestionResponseSchema> questionDetails(@Path("questionId") String questionId);
}
