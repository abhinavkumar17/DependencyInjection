package com.example.admin.di.questionlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.admin.di.MyApplication;
import com.example.admin.di.questiondetails.QuestionDetailsActivity;
import com.example.admin.di.questions.Question;
import com.example.admin.di.screens.ServerErrorDialogFragment;
import com.example.admin.di.screens.screens.common.dialog.DialogsManager;

import java.util.List;

import retrofit2.Retrofit;

public class QuestionListActivity extends AppCompatActivity implements
        FetchQuestionsListUseCase.Listener,QuestionsListViewMvc.Listener {

    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;

    private DialogsManager mDialogsManager;

    private FetchQuestionsListUseCase mFetchQuestionsListUseCase;
    private QuestionsListViewMvcImpl mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = new QuestionsListViewMvcImpl(LayoutInflater.from(this), null);

        Retrofit retrofit = ((MyApplication) getApplication()).getRetrofit();

        mFetchQuestionsListUseCase = new FetchQuestionsListUseCase(retrofit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionsListUseCase.registerListener(this);

        mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionsListUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionsSucceeded(List<Question> questions) {
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onFetchOfQuestionsFailed() {
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
        }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(getApplicationContext(),question.getId());
    }
}
