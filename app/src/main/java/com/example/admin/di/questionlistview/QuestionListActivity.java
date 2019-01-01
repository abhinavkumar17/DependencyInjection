package com.example.admin.di.questionlistview;

import android.os.Bundle;

import com.example.admin.di.common.BaseActivity;
import com.example.admin.di.common.dialog.DialogsManager;
import com.example.admin.di.common.dialog.ServerErrorDialogFragment;
import com.example.admin.di.questiondetails.QuestionDetailsActivity;
import com.example.admin.di.questions.Question;

import java.util.List;

public class QuestionListActivity extends BaseActivity implements
        FetchQuestionsListUseCase.Listener,QuestionsListViewMvc.Listener {



    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;

    private DialogsManager mDialogsManager;

    private FetchQuestionsListUseCase mFetchQuestionsListUseCase;
    private QuestionsListViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().newInstance(QuestionsListViewMvc.class, null);
        mFetchQuestionsListUseCase = getCompositionRoot().getFetchQuestionsListUseCase();
        mDialogsManager = getCompositionRoot().getDialogsManager();
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

