package com.example.admin.di.screens.screens.questionlistview;

import android.support.annotation.Nullable;
import com.example.admin.di.screens.Constants;
import com.example.admin.di.screens.questionlist.questions.Question;
import com.example.admin.di.screens.screens.common.BaseObservable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.List;

public class FetchQuestionsListUseCase extends BaseObservable<FetchQuestionsListUseCase.Listener> {

    public interface Listener {
        void onFetchOfQuestionsSucceeded(List<Question> questions);

        void onFetchOfQuestionsFailed();
    }

    private final com.example.admin.di.screens.questionlist.questions.StackOverflowApi mStackoverflowApi;

    @Nullable
    Call<com.example.admin.di.screens.questionlist.questions.QuestionsListResponseSchema> mCall;

    public FetchQuestionsListUseCase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowApi = retrofit.create(com.example.admin.di.screens.questionlist.questions.StackOverflowApi.class);
    }

    public void fetchLastActiveQuestionsAndNotify(int numOfQuestions) {

        cancelCurrentFetchIfActive();

        mCall = mStackoverflowApi.lastActiveQuestions(numOfQuestions);
        mCall.enqueue(new Callback<com.example.admin.di.screens.questionlist.questions.QuestionsListResponseSchema>() {
            @Override
            public void onResponse(Call<com.example.admin.di.screens.questionlist.questions.QuestionsListResponseSchema> call, Response<com.example.admin.di.screens.questionlist.questions.QuestionsListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getQuestions());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<com.example.admin.di.screens.questionlist.questions.QuestionsListResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(List<com.example.admin.di.screens.questionlist.questions.Question> questions) {
        List<com.example.admin.di.screens.questionlist.questions.Question> unmodifiableQuestions = Collections.unmodifiableList(questions);
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionsSucceeded(unmodifiableQuestions);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionsFailed();
        }
    }
}
