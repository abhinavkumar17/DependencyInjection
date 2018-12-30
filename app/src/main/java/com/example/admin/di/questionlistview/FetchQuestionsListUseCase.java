package com.example.admin.di.questionlistview;

import android.support.annotation.Nullable;

import com.example.admin.di.common.BaseObservable;
import com.example.admin.di.questions.Question;
import com.example.admin.di.questions.QuestionsListResponseSchema;
import com.example.admin.di.questions.StackOverflowApi;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchQuestionsListUseCase extends BaseObservable<FetchQuestionsListUseCase.Listener> {

    private final StackOverflowApi mStackoverflowApi;

    public interface Listener {
        void onFetchOfQuestionsSucceeded(List<Question> questions);

        void onFetchOfQuestionsFailed();
    }

    public FetchQuestionsListUseCase(Retrofit retrofit) {
        mStackoverflowApi = retrofit.create(StackOverflowApi.class);
    }

    public FetchQuestionsListUseCase(StackOverflowApi stackoverflowApi) {
        mStackoverflowApi = stackoverflowApi;
    }

    @Nullable
    Call<QuestionsListResponseSchema> mCall;

    public void fetchLastActiveQuestionsAndNotify(int numOfQuestions) {

        cancelCurrentFetchIfActive();

        mCall = mStackoverflowApi.lastActiveQuestions(numOfQuestions);
        mCall.enqueue(new Callback<QuestionsListResponseSchema>() {
            @Override
            public void onResponse(Call<QuestionsListResponseSchema> call,
                                   Response<QuestionsListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(response.body().getQuestions());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(List<Question> questions) {
        List<Question> unmodifiableQuestions = Collections.unmodifiableList(questions);
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
