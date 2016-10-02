package com.example.anuj.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Random;

import static android.os.Environment.getExternalStorageState;

public class QuizActivity extends AppCompatActivity {

    private int mNumber;
    private int mFactor;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private TextView mHighScoreTextView;
    private static final String NUMBER = "number";
    private static final String FACTOR = "factor";
    private static final String TAG = "Quiz";
    private static final String SCORE = "score";
    private Button mYesButton;
    private Button mNoButton;
    private Button mNextButton;
    private String mQuestion;
    private String mAnswer;
    private int mScore = 0;
    private int mHighScore = 0;


    public int testNumber()// Test whether the number is prime or not returns 0 if not prime else returns 1
    {
        int n = mNumber;
        if (mFactor == 0) {
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) {
                    return 0;
                }

            }
            return 1;
        } else {
            if (mNumber % mFactor == 0)
                return 1;
            else
                return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_preference_filename), Context.MODE_PRIVATE);
        int defaultValue = 0;
        mHighScore = sharedPref.getInt(getString(R.string.high_score), defaultValue);
        mHighScoreTextView = (TextView) findViewById(R.id.highScore);
        mHighScoreTextView.setText(" HIGH SCORE  " + mHighScore + " ");

        if (savedInstanceState != null) {
            mNumber = savedInstanceState.getInt(NUMBER, 0);
            mFactor = savedInstanceState.getInt(FACTOR);
            mScore = savedInstanceState.getInt(SCORE, 0);
            updateScore();

            mQuestionTextView = (TextView) findViewById(R.id.questionTextView);
            if (mFactor == 0)
                mQuestionTextView.setText("Is " + mNumber + " a prime number ? ");
            else
                mQuestionTextView.setText("Is " + mNumber + " divisible by " + mFactor + " ?");
        } else {
            displayQuestion();

        }
        mYesButton = (Button) findViewById(R.id.yesButton);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked Yes");
                if (testNumber() == 1) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                    mAnswer = new String("yes");
                    mScore++;
                    updateScore();

                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                    mAnswer = new String("no");
                }
                displayQuestion();
            }
        });

        mNoButton = (Button) findViewById(R.id.noButton);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked No");
                if (testNumber() == 1) {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                    mAnswer = new String("yes");

                } else {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                    mAnswer = new String("no");
                    mScore++;
                    updateScore();
                }
                displayQuestion();
            }
        });

        mNextButton = (Button) findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked Next");
                displayQuestion();

            }
        });

    }

    private void updateHighScore() {
        if (mHighScore < mScore) {
            mHighScore = mScore;

        }
    }

    private void updateScore() {
        mScoreTextView = (TextView) findViewById(R.id.score);
        mScoreTextView.setText(" SCORE " + mScore + " ");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "Inside onSaveInstance of Quiz");
        savedInstanceState.putInt(NUMBER, mNumber);
        savedInstanceState.putInt(FACTOR, mFactor);
        savedInstanceState.putInt(SCORE, mScore);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Inside OnStart of Quiz");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Inside OnPause of Quiz");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Inside OnResume of Quiz");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Inside OnStop of Quiz");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside OnDestroy of Quiz");


    }

    public void displayQuestion() {
        Random random = new Random();
        int choice = random.nextInt(2);
        mQuestionTextView = (TextView) findViewById(R.id.questionTextView);
        if (choice == 0) {
            mFactor = random.nextInt(20) + 2;
            mNumber = random.nextInt(1000) + 500;
            mQuestion = new String("Is " + mNumber + " divisible by " + mFactor + " ?");
            mQuestionTextView.setText("Is " + mNumber + " divisible by " + mFactor + " ?");

        } else {
            mNumber = random.nextInt(1000) + 1;
            while (mNumber == 1) {
                mNumber = random.nextInt(1000) + 2;
                mFactor = 0;
            }
            mQuestion = new String("Is " + mNumber + " a prime number ?\n");
            mQuestionTextView.setText("Is " + mNumber + " a prime number ?");
        }
    }

    @Override
    public void finish() {

        updateHighScore();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.high_score), mHighScore);
        editor.commit();


        Intent sendData = new Intent();

        sendData.putExtra("myScore", mScore);
        setResult(RESULT_OK, sendData);


        super.finish();
    }

    public boolean isExternalStorageWritable() {
        String state = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void createExternalStoragePrivateFile() {

        if (isExternalStorageWritable()) {
            File file = new File(getExternalFilesDir(null), "DemoFile.txt");

            try {

                OutputStream os = new FileOutputStream(file);

                byte[] data = mQuestion.getBytes();

                os.write(data);

                os.close();
                Log.d(TAG, "Successfully Private file written using getExternalFilesDir()");
            } catch (IOException e) {

                Log.d("ExternalStorage", "Error writing " + file, e);
            }
        } else {
            Log.d(TAG, "ExternalStorage not writable");
        }
    }


    public void saveQuestion(View view) {
        createExternalStoragePrivateFile();


    }

    public void saveScreenshot(View view) {


        File file;
        FileOutputStream outputStream;
        String state = getExternalStorageState();
        Log.d(TAG, state);

        File root = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        File dir = new File(root.getAbsolutePath() + "/download");
        dir.mkdirs();
        file = new File(dir, "myData.txt");

        try {

            outputStream = new FileOutputStream(file);
            outputStream.write(mAnswer.getBytes());
            outputStream.close();
            Log.d(TAG, "Sucessfuly write public file using getExternalStroageDirectory");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
