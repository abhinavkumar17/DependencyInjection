package com.example.admin.di.screens.screens.questiondetails;

import android.support.annotation.Nullable;
import com.example.admin.di.screens.Constants;
import com.example.admin.di.screens.screens.common.BaseObservable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    public interface Listener {
        void onFetchOfQuestionDetailsSucceeded(com.example.admin.di.screens.questionlist.questions.QuestionWithBody question);
        void onFetchOfQuestionDetailsFailed();
    }

    private final com.example.admin.di.screens.questionlist.questions.StackOverflowApi mStackoverflowApi;

    @Nullable
    Call<com.example.admin.di.screens.questionlist.questions.SingleQuestionResponseSchema> mCall;

    public FetchQuestionDetailsUseCase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowApi = retrofit.create(com.example.admin.di.screens.questionlist.questions.StackOverflowApi.class);
    }

    public void fetchQuestionDetailsAndNotify(String questionId) {

        cancelCurrentFetchIfActive();

        mCall = mStackoverflowApi.questionDetails(questionId);
        mCall.enqueue(new Callback<com.example.admin.di.screens.questionlist.questions.SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<com.example.admin.di.screens.questionlist.questions.SingleQuestionResponseSchema> call, Response<com.example.admin.di.screens.questionlist.questions.SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getQuestion());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<com.example.admin.di.screens.questionlist.questions.SingleQuestionResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(com.example.admin.di.screens.questionlist.questions.QuestionWithBody question) {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsSucceeded(question);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsFailed();
        }
    }
}
