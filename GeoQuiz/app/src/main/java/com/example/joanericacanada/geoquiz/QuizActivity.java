package com.example.joanericacanada.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button btnTrue;
    private Button btnFalse;
    private ImageButton ImgBtnNext;
    private ImageButton ImgBtnPrevious;
    private TextView txtVwQuestion;

    private TrueFalse[] questionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)};

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtVwQuestion = (TextView) findViewById(R.id.question_text_view);
        txtVwQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        btnTrue = (Button) findViewById(R.id.true_button);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        btnFalse = (Button) findViewById(R.id.false_button);
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        ImgBtnNext = (ImageButton) findViewById(R.id.next_button);
        ImgBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        ImgBtnPrevious = (ImageButton)findViewById(R.id.previous_button);
        ImgBtnPrevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = ((currentIndex -1) % questionBank.length);
                if (currentIndex < 0){
                    currentIndex = questionBank.length + currentIndex;
                }
                updateQuestion();
            }
        });
        
        updateQuestion();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateQuestion(){
        int question = questionBank[currentIndex].getQuestion();
        txtVwQuestion.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = questionBank[currentIndex].isTrueQuestion();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show();
    }
}

    