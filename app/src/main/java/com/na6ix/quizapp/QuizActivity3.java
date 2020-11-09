package com.na6ix.quizapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizActivity3 extends AppCompatActivity {

    private static final long countdownInMs= 20000;

    private static final String KEY_SCORE ="keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT ="keyQuestionCount";
    private static final String KEY_ANSWERED ="keyMillisLeft";
    private static final String KEY_QUESTION ="question";

    private TextView tv;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private RadioGroup rg;
    private RadioButton rb;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button next;

    private ColorStateList defaultColorRB;
    private ColorStateList getDefaultColorCD;

    private CountDownTimer cdt;
    private long timeLeftMs;

    private ArrayList<QuestionAnswers> qList;
    private int questionCounter;
    private int questionTotalCounter;
    private QuestionAnswers currentQuestion;

    private int point;
    private boolean questionAnswered;

    private long backPressedCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz3);

        tv = findViewById(R.id.points3);
        tv2 = findViewById(R.id.count3);
        tv3 = findViewById(R.id.timer3);
        tv4 = findViewById(R.id.question3);
        rg = findViewById(R.id.radio3);
        rb = findViewById(R.id.aB13);
        rb2 = findViewById(R.id.aB23);
        rb3 = findViewById(R.id.aB33);
        rb4 = findViewById(R.id.aB43);
        next = findViewById(R.id.submit3);

        defaultColorRB = rb.getTextColors();
        getDefaultColorCD = tv2.getTextColors();


        SciDbHelper dbHelper = new SciDbHelper(this);
        qList = dbHelper.getQuestions();

        questionTotalCounter = qList.size();
        Collections.shuffle(qList);

        nextQuestion();



        //When next is clicked
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!questionAnswered){
                    if(rb.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    }
                    else{
                        Toast.makeText(QuizActivity3.this, "Please Select an option", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    nextQuestion();
                }
            }
        });
    }

    //Display next question or end
    private void nextQuestion() {
        rb.setTextColor(defaultColorRB);
        rb2.setTextColor(defaultColorRB);
        rb3.setTextColor(defaultColorRB);
        rb4.setTextColor(defaultColorRB);
        rg.clearCheck();

        if(questionCounter < questionTotalCounter){
            currentQuestion = qList.get(questionCounter);

            tv4.setText(currentQuestion.get_question());
            rb.setText(currentQuestion.get_option1());
            rb2.setText(currentQuestion.get_option2());
            rb3.setText(currentQuestion.get_option3());
            rb4.setText(currentQuestion.get_option4());

            questionCounter++;
            tv2.setText("Question: "+ questionCounter +"/" + questionTotalCounter);
            questionAnswered=false;
            next.setText("Confirm");

            timeLeftMs = countdownInMs;
            startTimer();
        }
        else{
            endQuiz();
        }
    }

    private void startTimer(){
        cdt = new CountDownTimer(timeLeftMs,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMs = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeftMs =0;
                updateTimer();
                checkAnswer();
            }
        }.start();
    }
    private void updateTimer(){
        int minutes = (int) (timeLeftMs / 1000) / 60;
        int seconds = (int) (timeLeftMs / 1000) % 60;

        String time = String.format(Locale.getDefault(),"%02d:%02d",minutes, seconds);
        tv3.setText(time);

        if(timeLeftMs < 5000){
            tv.setTextColor(Color.RED);
        }
        else{
            tv3.setTextColor(getDefaultColorCD);
        }
    }

    //Check if the answer is correct
    private void checkAnswer(){
        questionAnswered =true;

        cdt.cancel();

        RadioButton selectedButton = findViewById(rg.getCheckedRadioButtonId());
        int num = rg.indexOfChild(selectedButton) + 1;

        if(num == currentQuestion.get_answer()){
            point++;
            tv.setText("Score: " + point);
        }
        showAnswer();
    }
    //Change text color based on answer
    private void showAnswer() {
        rb.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        switch (currentQuestion.get_answer()){
            case 1:
                rb.setTextColor(Color.GREEN);
                tv4.setText("Option 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                tv4.setText("Option 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                tv4.setText("Option 3 is correct");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                tv4.setText("Option 4 is correct");
                break;
        }

        if(questionCounter < questionTotalCounter){
            next.setText("Next");
        }
        else{
            next.setText("End");
        }
    }

    //To end quiz
    private void endQuiz() {
        finish();
    }

    //Check for accidental back press
    @Override
    public void onBackPressed(){
        if(backPressedCounter + 2000 > System.currentTimeMillis()){
            endQuiz();
        }
        else{
            Toast.makeText(this,"Press back again to end quiz", Toast.LENGTH_SHORT).show();
        }
        backPressedCounter = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(cdt != null){
            cdt.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,point);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT,timeLeftMs);
        outState.putBoolean(KEY_ANSWERED,questionAnswered);
        outState.putParcelableArrayList(KEY_QUESTION, qList);

    }
}
