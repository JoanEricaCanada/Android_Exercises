package com.example.joanericacanada.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by joanericacanada on 10/2/15.
 */
public class CheatActivity extends Activity {

    private boolean answerIsTrue;
    
    private TextView txtVwAnswer;
    private TextView txtVwVersion;
    private Button showAnswer;
    
    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.joanericacanada.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.joanericacanada.geoquiz.answer_shown";
    private static final String KEY_CHEAT = "isCheater";
    private static final String TAG = "CheatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        
        //version text view
        txtVwVersion = (TextView) findViewById(R.id.versionTextView);
        txtVwVersion.setText("API level " + Integer.toString(Build.VERSION.SDK_INT));

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        txtVwAnswer = (TextView) findViewById(R.id.answerTextView);
        setAnswerShownResult(false);
        
        showAnswer = (Button) findViewById(R.id.showAnswerButton);
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerIsTrue) {
                    txtVwAnswer.setText(R.string.true_button);
                } else {
                    txtVwAnswer.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });

        if (savedInstanceState != null){
            //currentIndex = savedInstanceState.getBoolean(KEY_CHEAT);
            answerIsTrue = savedInstanceState.getBoolean(KEY_CHEAT);
            txtVwAnswer.setText(Boolean.toString(answerIsTrue));
            setAnswerShownResult(answerIsTrue);

        }
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //save state: answerIsTrue
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_CHEAT, answerIsTrue);
    }
}
