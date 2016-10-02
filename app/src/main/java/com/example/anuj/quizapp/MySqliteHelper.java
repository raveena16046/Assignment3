package com.example.anuj.quizapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Anuj on 10/2/2016.
 */
public class MySqliteHelper extends SQLiteOpenHelper {


    private final String DATABASE_NAME = "scores.db";
    public final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + ScoreTable.ScoreEntry.TABLE_NAME + "( " + ScoreTable.ScoreEntry._ID
            + " integer primary key autoincrement, " + ScoreTable.ScoreEntry.COLUMN_NAME_TIME
            + " text not null , " + ScoreTable.ScoreEntry.COLUMN_NAME_SCORE +
            " text not null )";

    public MySqliteHelper(Context context) {

        super(context, "scores.db", null, 1);


    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MySqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + ScoreTable.ScoreEntry.TABLE_NAME);
        onCreate(db);
    }

}
