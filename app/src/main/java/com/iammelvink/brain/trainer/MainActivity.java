package com.iammelvink.brain.trainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*Class fields/variables*/
    private ConstraintLayout constraintLayout;
    private Button goBtn;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btnPlayAgn;

    private TextView timer;
    private TextView sum;
    private TextView score;
    private TextView result;
    /*
     * Generate random numbers*/
    private SecureRandom rand = new SecureRandom();

    private ArrayList<Integer> answers = new ArrayList<Integer>();
    private int locationOfCorrectAnswer;
    private int scoreGot = 0;
    private int numOfQuestions = 0;
    private CountDownTimer countDownTimer;

    /*Play again*/
    public void playAgn(View view) {

        btn0.setEnabled(true);
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);

        scoreGot = 0;
        numOfQuestions = 0;
        timer.setText("30s");
        result.setText("");
        newQuestion();

        btnPlayAgn.setVisibility(View.INVISIBLE);

        /*Update score accordingly*/
        score.setText(scoreGot + "/" + numOfQuestions);

        /*
         * Countdown timer
         * 30000 milliseconds = 30 seconds
         * + 100 milliseconds for stop lag*/
        countDownTimer = new CountDownTimer(30100, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                newQuestion();
                timer.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                result.setText("Done! ;)");
                btn0.setEnabled(false);
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
                btnPlayAgn.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    /*
     * Checks chosen answer*/
    public void chooseAnswer(View view) {
        Log.i("Tag number: ", view.getTag().toString());

        /*Comparing tags as Strings*/
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            result.setText("Correct! :)");
            scoreGot++;
            Log.i("Correct: ", view.getTag().toString());
        } else {
            result.setText("Wrong! :(");
            Log.i("Wrong: ", view.getTag().toString());
        }
        /*Update score accordingly*/
        numOfQuestions++;
        score.setText(scoreGot + "/" + numOfQuestions);

        newQuestion();
    }

    /*Start trainer*/
    public void startTrainer(View view) {
        goBtn.setVisibility(View.INVISIBLE);
        playAgn((TextView) findViewById(R.id.txtTimer));
        constraintLayout.setVisibility(View.VISIBLE);
    }

    public void newQuestion() {
        /*
         * Generate random number between 0 and 20*/
        int num1 = rand.nextInt(21);
        int num2 = rand.nextInt(21);

        sum.setText(num1 + " + " + num2);

        /*
         * Generate random number between 0 and 3*/
        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(num1 + num2);
            } else {
                /*
                 * Generate random number between 0 and 40*/
                int wrongAnswer = rand.nextInt(41);

                /*Check while wrongAnswer == num1 + num2
                Generate new wrongAnswer/
                 */
                while (wrongAnswer == num1 + num2) {
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        /*Put answers in buttons*/
        btn0.setText(Integer.toString(answers.get(0)));
        btn1.setText(Integer.toString(answers.get(1)));
        btn2.setText(Integer.toString(answers.get(2)));
        btn3.setText(Integer.toString(answers.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*Link objects to code*/
        constraintLayout = (ConstraintLayout) findViewById(R.id.gameLayout);

        goBtn = (Button) findViewById(R.id.btnGo);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btnPlayAgn = (Button) findViewById(R.id.btnPlayAgain);

        timer = (TextView) findViewById(R.id.txtTimer);
        sum = (TextView) findViewById(R.id.txtSum);
        score = (TextView) findViewById(R.id.txtScore);
        result = (TextView) findViewById(R.id.txtResult);

        /*Generate new questions*/
        newQuestion();

        goBtn.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);
    }
}
