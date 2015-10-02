package com.example.joanericacanada.geoquiz;

/**
 * Created by joanericacanada on 10/1/15.
 */
public class TrueFalse {
    private int mQuestion;
    private boolean mTrueQuestion;

    public TrueFalse(int question, boolean TrueQuestion){
        mQuestion = question;
        mTrueQuestion = TrueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }
    public void setQuestion(int question) {
        mQuestion = question;
    }
    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }
    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }

}
