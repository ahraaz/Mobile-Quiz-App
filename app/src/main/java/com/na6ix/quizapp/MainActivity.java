package com.na6ix.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private static  final int REQUEST_CODE=2;
    private static  final int REQUEST_CODE2=3;
    private static  final int REQUEST_CODE3=4;
    private static  final int REQUEST_CODE4=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        ImageButton geoButton = findViewById(R.id.geoButton);
        ImageButton hisButton = findViewById(R.id.hisButton);
        ImageButton sciButton = findViewById(R.id.sciButton);
        ImageButton artButton = findViewById(R.id.artButton);

        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGeoQuiz();
            }
        });

        hisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHisQuiz();
            }
        });

        sciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSciQuiz();
            }
        });

        artButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startArtQuiz();
            }
        });

    }

    private void startGeoQuiz() {
        Intent i = new Intent(this,QuizActivity.class);
        startActivity(i);
    }

    private void startHisQuiz() {
        Intent i = new Intent(this,QuizActivity2.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    private void startSciQuiz() {
        Intent i = new Intent(this,QuizActivity3.class);
        startActivity(i);
    }

    private void startArtQuiz() {
        Intent i = new Intent(this,QuizActivity4.class);
        startActivity(i);
    }







}
