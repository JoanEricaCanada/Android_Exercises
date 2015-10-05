package com.example.joanericacanada.geoquiz;

/**
 * Created by joanericacanada on 10/1/15.
 */
public class TrueFalse {
    private int question;
    private boolean trueQuestion;
    private boolean cheated;

    public TrueFalse(int question, boolean trueQuestion, boolean cheated){
        this.question = question;
        this.trueQuestion = trueQuestion;
        this.cheated = cheated;
    }

    public int getQuestion() {
        return question;
    }
    public void setQuestion(int question) {
        this.question = question;
    }
    public boolean isTrueQuestion() {
        return trueQuestion;
    }
    public void setTrueQuestion(boolean trueQuestion) {
        this.trueQuestion = trueQuestion;
    }
    public void setCheated(boolean cheated){this.cheated = cheated;}
    public boolean getCheated(){return cheated;}


}
