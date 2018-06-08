package com.example.razli.braintrainer;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    android.support.v7.widget.GridLayout gridView;
    TextView timerTextView, scoreTextView, questionTextView, correctWrongTextView;
    TextView optionTextView1, optionTextView2, optionTextView3, optionTextView4;
    TextView timeLabelTextView, scoreLabelTextView, questionLabelTextView;
    Button playAgainButton;

    int positionOfCorrectAnswer;
    int questionsTotal, questionsCorrect;

    boolean isGameActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references for all views
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        questionTextView = findViewById(R.id.questionTextView);
        timeLabelTextView = findViewById(R.id.timeLabelTextView);
        scoreLabelTextView = findViewById(R.id.scoreLabelTextView);
        questionLabelTextView = findViewById(R.id.questionLabelTextView);
        correctWrongTextView = findViewById(R.id.correctWrongTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gridView = findViewById(R.id.gridView);

        optionTextView1 = findViewById(R.id.optionTextView1);
        optionTextView2 = findViewById(R.id.optionTextView2);
        optionTextView3 = findViewById(R.id.optionTextView3);
        optionTextView4 = findViewById(R.id.optionTextView4);

        questionsTotal = 0;
        questionsCorrect = 0;

        generateNewQuestion();
    }

    public void startQuiz(View view) {

        view.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.VISIBLE);
        timeLabelTextView.setVisibility(View.VISIBLE);
        questionLabelTextView.setVisibility(View.VISIBLE);
        scoreLabelTextView.setVisibility(View.VISIBLE);

        isGameActive = true;    // Game starts
        questionsTotal = 0;
        questionsCorrect = 0;
        scoreTextView.setText(questionsCorrect + "/" + questionsTotal);
        correctWrongTextView.setText("");

        // Start timer
        new CountDownTimer(31000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, "onTick: " + millisUntilFinished/1000 + " seconds left");
                timerTextView.setText(millisUntilFinished/1000 + "s");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: Done!");
                isGameActive = false;
                playAgainButton.setVisibility(View.VISIBLE);
                correctWrongTextView.setText("Game Over! Your score: " + questionsCorrect + "/" + questionsTotal);
            }
        }.start();
    }

    public void optionPressed(View view) {

        if(isGameActive) {

            int optionPicked = 0;

            switch (view.getId()) {
                case R.id.optionTextView1:
                    optionPicked = 1;
                    break;
                case R.id.optionTextView2:
                    optionPicked = 2;
                    break;
                case R.id.optionTextView3:
                    optionPicked = 3;
                    break;
                case R.id.optionTextView4:
                    optionPicked = 4;
                    break;
                default:
                    break;
            }

            Log.i(TAG, "optionPressed: You chose: " + Integer.toString(optionPicked) + " : Correct Position: " + Integer.toString(positionOfCorrectAnswer));

            // Check if answer is correct
            if (optionPicked == positionOfCorrectAnswer) {
                correctWrongTextView.setText("Correct!");
                questionsCorrect++;
            } else {
                correctWrongTextView.setText("Wrong :(");
            }

            questionsTotal++;
            scoreTextView.setText(questionsCorrect + "/" + questionsTotal);
            generateNewQuestion();
        }
    }

    public void generateNewQuestion() {

        int max = 30;   // Range for random operand
        List<Integer> options = new ArrayList<Integer>();   // Holds the values of the 4 options

        // Randomize new question (2 operands). Operator is always +
        int operand1 = new Random().nextInt(max) + 1;
        int operand2 = new Random().nextInt(max) + 1;

        // Get answer to question
        int currentAnswer = operand1 + operand2;

        // Get values for 3 other options. Shuffle them in the ArrayList
        options.add(currentAnswer);
        options.add(currentAnswer - 3);
        options.add(currentAnswer + 3);
        options.add(currentAnswer + 5);
        Collections.shuffle(options);

        // Update all views
        String newQuestionText = Integer.toString(operand1) + " + " + Integer.toString(operand2);
        questionTextView.setText(newQuestionText);
        optionTextView1.setText(options.get(0).toString());
        optionTextView2.setText(options.get(1).toString());
        optionTextView3.setText(options.get(2).toString());
        optionTextView4.setText(options.get(3).toString());

        // Set position of correct option (1-4)
        positionOfCorrectAnswer =  options.indexOf(currentAnswer) + 1;
    }


}
