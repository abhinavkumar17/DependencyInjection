package com.example.admin.di.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.admin.di.R;
import com.example.admin.di.common.BaseActivity;
import com.example.admin.di.common.dialog.DialogsManager;
import com.example.admin.di.common.dialog.ServerErrorDialogFragment;

public class QuestionDetailsActivity extends BaseActivity implements
        QuestionDetailsViewMvc.Listener,FetchQuestionDetailsUseCase.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";
    private DialogsManager mDialogsManager;

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }
    private QuestionDetailsViewMvc mViewMvc;

    private String mQuestionId;

    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question_details);

        mViewMvc = getCompositionRoot().getViewMvcFactory().newInstance(QuestionDetailsViewMvc.class, null);
        setContentView(mViewMvc.getRootView());
        mFetchQuestionDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();
        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);
        mDialogsManager = getCompositionRoot().getDialogsManager();
        }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionDetailsUseCase.registerListener(this);
        mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(mQuestionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionDetailsUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(com.example.admin.di.screens.questionlist.questions.QuestionWithBody question) {
        mViewMvc.bindQuestion(question);
    }
}
