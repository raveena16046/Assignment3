package com.example.anuj.quizapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    private static final String TAG = "HINT";
    private MySqliteHelper mDbHelper = new MySqliteHelper(this);
    private TextView mHighScoreTextView;

    public MySqliteHelper mdbHelper = new MySqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ScoreTable.ScoreEntry._ID,
                ScoreTable.ScoreEntry.COLUMN_NAME_TIME,
                ScoreTable.ScoreEntry.COLUMN_NAME_SCORE
        };

        String sortOrder =
                ScoreTable.ScoreEntry.COLUMN_NAME_SCORE + " DESC";

        Cursor c = db.query(
                ScoreTable.ScoreEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        mHighScoreTextView = (TextView) findViewById(R.id.highScoreTextView);
        if (c.getCount() == 0) {

            mHighScoreTextView.setText("No records found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {

            buffer.append("Time: " + c.getString(1) + "    ");
            buffer.append("Score: " + c.getString(2) + "\n");
        }
        // Displaying all records
        mHighScoreTextView.setText(buffer.toString());

    }

    public void clearAll(View view) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL("Delete from " + ScoreTable.ScoreEntry.TABLE_NAME);
        Cursor c = db.rawQuery("SELECT * FROM " + ScoreTable.ScoreEntry.TABLE_NAME, null);
        if (c.getCount() == 0) {
            mHighScoreTextView.setText("No records found");

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.shared_preference_filename), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.high_score), 0);
            editor.commit();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Inside OnStart of HighScore");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Inside OnPause of HighScore");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Inside OnResume  of HighScore");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Inside OnStop  of HighScore");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside OnDestroy  of HighScore");
    }
}
