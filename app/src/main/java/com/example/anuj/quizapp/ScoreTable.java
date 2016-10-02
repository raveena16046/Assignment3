package com.example.anuj.quizapp;

import android.provider.BaseColumns;

/**
 * Created by Anuj on 10/2/2016.
 */
public final class ScoreTable {


    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ScoreTable() {
    }

    /* Inner class that defines the table contents */
    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_SCORE = "score";
    }

}
