package com.example.admin.di.screens.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.admin.di.R;
import com.example.admin.di.screens.ServerErrorDialogFragment;
import com.example.admin.di.screens.screens.common.dialog.DialogsManager;

public class QuestionDetailsActivity extends AppCompatActivity implements
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

        mViewMvc = new QuestionDetailsViewMvcImpl(LayoutInflater.from(this), null);

        setContentView(mViewMvc.getRootView());

        mFetchQuestionDetailsUseCase = new FetchQuestionDetailsUseCase();

        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

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
