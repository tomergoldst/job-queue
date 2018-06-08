package com.tomergoldst.jobqueue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomer on 18/03/2017.
 *
 */
class JobQueueDao {

    /** Tag used on log messages.*/
    private final String TAG = this.getClass().getSimpleName();

    private static final int ERROR = -1;

    // Database fields
    private SQLiteDatabase database;
    private JobQueueDbHelper dbHelper;

    private static JobQueueDao mjobQueueDao;

    static synchronized JobQueueDao getInstance(Context context){
        if (mjobQueueDao == null){
            mjobQueueDao = new JobQueueDao(context.getApplicationContext());
            return mjobQueueDao;
        }
        return mjobQueueDao;
    }

    private JobQueueDao(Context context) {
        dbHelper = new JobQueueDbHelper(context);
    }

    // Open connection to database
    void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close connection to database
    void close() {
        dbHelper.close();
    }

    // Store jobTask, return database inserted id on success
    long store(JobTask jobTask) {
        // Set information to store
        ContentValues values = new ContentValues();
        values.put(JobQueueDbContract.JobEntry.COLUMN_NAME, jobTask.getName());
        values.put(JobQueueDbContract.JobEntry.COLUMN_DATA, jobTask.getData());
        values.put(JobQueueDbContract.JobEntry.COLUMN_CREATED_AT, jobTask.getCreatedAt());

        // Store in database
        long insertId = database.insert(JobQueueDbContract.JobEntry.TABLE_NAME, null, values);

        if (insertId == ERROR) {
            Log.i(TAG,"Store jobTask failed");
            return ERROR;
        }

        Log.i(TAG, "Store jobTask succeed");
        return insertId;

    }

    JobTask getNext() {
        Cursor cursor;
        JobTask JobTask = null;

        String orderBy = JobQueueDbContract.JobEntry._ID + " ASC";
        String limit = String.valueOf(1);

        // get all jobs
        cursor = database.query(JobQueueDbContract.JobEntry.TABLE_NAME,
                JobQueueDbContract.getAllJobColumns(), null, null, null, null, orderBy, limit);

        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.JobEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_NAME));
            String data = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_DATA));
            long createdAt = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_CREATED_AT));

            JobTask = new JobTask(id, name, data, createdAt);
        }

        cursor.close();

        return JobTask;
    }

    List<JobTask> getJobs() {

        ArrayList<JobTask> jobsList = new ArrayList<>();
        Cursor cursor;

        String orderBy = JobQueueDbContract.JobEntry._ID + " ASC";

        // get all jobs
        cursor = database.query(JobQueueDbContract.JobEntry.TABLE_NAME,
                JobQueueDbContract.getAllJobColumns(), null, null, null, null, orderBy, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            long id = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.JobEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_NAME));
            String data = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_DATA));
            long createdAt = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_CREATED_AT));

            JobTask jobTask = new JobTask(id, name, data, createdAt);
            jobsList.add(jobTask);

            cursor.moveToNext();
        }
        cursor.close();

        return jobsList;
    }

    List<JobTask> getJob(String name) {

        ArrayList<JobTask> jobsList = new ArrayList<>();
        Cursor cursor;

        String selection = JobQueueDbContract.JobEntry.COLUMN_NAME + " = ?";
        String selectionArgs[] = {name};
        String orderBy = JobQueueDbContract.JobEntry._ID + " ASC";

        // get all jobs
        cursor = database.query(JobQueueDbContract.JobEntry.TABLE_NAME,
                JobQueueDbContract.getAllJobColumns(), selection, selectionArgs, null, null, orderBy, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            long id = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.JobEntry._ID));
            String _name = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_NAME));
            String data = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_DATA));
            long createdAt = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.JobEntry.COLUMN_CREATED_AT));

            JobTask jobTask = new JobTask(id, _name, data, createdAt);
            jobsList.add(jobTask);

            cursor.moveToNext();
        }
        cursor.close();

        return jobsList;
    }

    boolean update(JobTask jobTask) {
        // Set information to store
        ContentValues values = new ContentValues();
        values.put(JobQueueDbContract.JobEntry.COLUMN_NAME, jobTask.getName());
        values.put(JobQueueDbContract.JobEntry.COLUMN_DATA, jobTask.getData());
        values.put(JobQueueDbContract.JobEntry.COLUMN_CREATED_AT, jobTask.getCreatedAt());

        // update info
        String whereClause = JobQueueDbContract.JobEntry._ID +" = ?";
        String whereArgs[] = {String.valueOf(jobTask.getId())};

        long rows = database.update(JobQueueDbContract.JobEntry.TABLE_NAME,
                values, whereClause, whereArgs);

        if (rows == 0) {
            Log.i(TAG,"update jobTask [" + jobTask.getId() + "] failed");
            return false;
        }

        Log.i(TAG, "update jobTask [" + jobTask.getId() + "] succeed");
        return true;

    }

    boolean delete(JobTask jobTask) {
        // Delete jobTask query
        String selection = JobQueueDbContract.JobEntry._ID + " = ?";
        String selectionArgs[] = {String.valueOf(jobTask.getId())};

        long rows = database.delete(JobQueueDbContract.JobEntry.TABLE_NAME,
                selection, selectionArgs);

        // If no rows where deleted
        if (rows == 0){
            Log.i(TAG,"jobTask [" + jobTask.getId() + "] doesn't exist");
            return false;
        }

        Log.i(TAG, "jobTask [" + jobTask.getId() + "] deleted");
        return true;
    }

    boolean delete(String name) {
        // Delete job query
        String selection = JobQueueDbContract.JobEntry.COLUMN_NAME + " = ?";
        String selectionArgs[] = {name};

        long rows = database.delete(JobQueueDbContract.JobEntry.TABLE_NAME,
                selection, selectionArgs);

        // If no rows where deleted
        if (rows == 0){
            Log.i(TAG,"jobs [" + name + "] don't exist");
            return false;
        }

        Log.i(TAG, rows + " jobs [" + name + "] were deleted");
        return true;
    }

    void clear() {
        long rows = database.delete(JobQueueDbContract.JobEntry.TABLE_NAME,
                null, null);

        // If no rows where deleted
        if (rows == 0){
            Log.i(TAG,"job queue is empty");
        }

        Log.i(TAG, "job queue cleared");
    }

    long size(){
        return DatabaseUtils.queryNumEntries(database, JobQueueDbContract.JobEntry.TABLE_NAME);
    }

}
