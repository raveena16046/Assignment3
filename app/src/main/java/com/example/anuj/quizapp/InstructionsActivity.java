package com.example.anuj.quizapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InstructionsActivity extends AppCompatActivity {

    private static final String TAG = "Quiz";
    private TextView mTextView;
    private String mFileName = "myfile.txt";
    private String mInstructions = "Hello! this is an android application QuizApp. Press Yes if a number is prime and if the number is divisible by another number.  ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        writeInstruction();
        showInstruction();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Inside OnStart  of Instruction");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Inside OnPause  of Instruction");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Inside OnResume   of Instruction");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Inside OnStop   of Instruction");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Inside OnDestroy   of Instruction");
    }

    private void writeInstruction() {
        FileOutputStream mOutputStream;
        try {
            mOutputStream = openFileOutput(mFileName, Context.MODE_PRIVATE);
            mOutputStream.write(mInstructions.getBytes());
            mOutputStream.close();
            Log.d(TAG, "File saved internal");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showInstruction() {
        mTextView = (TextView) findViewById(R.id.instructionTextView);
        String inputString;
        StringBuffer stringBuffer = new StringBuffer();
        try {

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput(mFileName)));


            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        mTextView.setText(stringBuffer.toString());


    }

}
