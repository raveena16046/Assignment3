package com.example.anuj.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Quiz";
    private static final int REQUEST_CODE = 1;
    private MySqliteHelper mDbHelper = new MySqliteHelper(this);

    public void playGame(View view) {
        Intent i = new Intent(this, QuizActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Inside OnStart of Main");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Inside OnPause of Main");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Inside OnResume  of Main");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Inside OnStop  of Main");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside OnDestroy  of Main");
    }

    public void showInstruction(View view) {
        Intent i = new Intent(this, InstructionsActivity.class);
        startActivity(i);
    }

    public void showHighScores(View view) {
        Intent i = new Intent(this, HighScoreActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent receiveData) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
            if (receiveData.hasExtra("myScore")) {
                int score = receiveData.getExtras().getInt("myScore");
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();


                ContentValues values = new ContentValues();
                values.put(ScoreTable.ScoreEntry.COLUMN_NAME_TIME, new String(dateFormat.format(cal.getTime())));
                values.put(ScoreTable.ScoreEntry.COLUMN_NAME_SCORE, score);


                long newRowId = db.insert(ScoreTable.ScoreEntry.TABLE_NAME, null, values);
                Log.d(TAG, newRowId + " inserted in TABLE");
            }

    }
}

