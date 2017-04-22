package com.tomergoldst.jobqueue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tomer on 18/03/2017.
 *
 */
class JobQueueDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "com.tomergoldst.jobqueue.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String UNIQUE = " UNIQUE";

    private static final String SQL_CREATE_JOB_QUEUE_ENTRIES =
            "CREATE TABLE " + JobQueueDbContract.JobEntry.TABLE_NAME + " (" +
                    JobQueueDbContract.JobEntry._ID + " INTEGER PRIMARY KEY," +
                    JobQueueDbContract.JobEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    JobQueueDbContract.JobEntry.COLUMN_DATA + TEXT_TYPE + COMMA_SEP +
                    JobQueueDbContract.JobEntry.COLUMN_CREATED_AT + INTEGER_TYPE +
            " )";

    private static final String SQL_DELETE_JOB_QUEUE_ENTRIES =
            "DROP TABLE IF EXISTS " + JobQueueDbContract.JobEntry.TABLE_NAME;

    JobQueueDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_JOB_QUEUE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_JOB_QUEUE_ENTRIES);
        onCreate(db);
    }

}
