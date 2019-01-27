package com.tomergoldst.jobqueue;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class JobQueueDao {

    /** Tag used on log messages.*/
    private final String TAG = this.getClass().getSimpleName();

    private static final int ERROR = -1;

    private SQLiteDatabase database;

    JobQueueDao(SQLiteDatabase database) {
        this.database = database;
    }

    long store(@NonNull String queueUid, @NonNull JobTask jobTask) {
        ContentValues values = new ContentValues();
        values.put(JobQueueDbContract.QueueJobEntry.COLUMN_QUEUE_UID, queueUid);
        values.put(JobQueueDbContract.QueueJobEntry.COLUMN_JOB_UID, jobTask.getName());
        values.put(JobQueueDbContract.QueueJobEntry.COLUMN_DATA, jobTask.getData());
        values.put(JobQueueDbContract.QueueJobEntry.COLUMN_CREATED_AT, jobTask.getCreatedAt());

        // Store in database
        long insertId = database.insert(JobQueueDbContract.QueueJobEntry.TABLE_NAME, null, values);

        if (insertId == ERROR) {
            Log.i(TAG,"Store jobTask failed");
            return ERROR;
        }

        Log.i(TAG, "Store jobTask succeed");
        return insertId;

    }

    JobTask getNext(@NonNull String queueUid) {
        Cursor cursor;
        JobTask JobTask = null;

        String selection = JobQueueDbContract.QueueJobEntry.COLUMN_QUEUE_UID + " = ?";
        String selectionArgs[] = {queueUid};
        String orderBy = JobQueueDbContract.QueueJobEntry._ID + " ASC";
        String limit = String.valueOf(1);

        // get all jobs
        cursor = database.query(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                JobQueueDbContract.getAllJobColumns(), selection, selectionArgs, null, null, orderBy, limit);

        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_JOB_UID));
            String data = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_DATA));
            long createdAt = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_CREATED_AT));

            JobTask = new JobTask(id, name, data, createdAt);
        }

        cursor.close();

        return JobTask;
    }

    List<JobTask> getJobs(@NonNull String queueUid) {
        ArrayList<JobTask> jobsList = new ArrayList<>();
        Cursor cursor;

        String selection = JobQueueDbContract.QueueJobEntry.COLUMN_QUEUE_UID + " = ?";
        String selectionArgs[] = {queueUid};
        String orderBy = JobQueueDbContract.QueueJobEntry._ID + " ASC";

        // get all jobs
        cursor = database.query(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                JobQueueDbContract.getAllJobColumns(), selection, selectionArgs, null, null, orderBy, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            long id = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_JOB_UID));
            String data = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_DATA));
            long createdAt = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_CREATED_AT));

            JobTask jobTask = new JobTask(id, name, data, createdAt);
            jobsList.add(jobTask);

            cursor.moveToNext();
        }
        cursor.close();

        return jobsList;
    }

    List<JobTask> getJob(@NonNull String queueUid, @NonNull String name) {
        ArrayList<JobTask> jobsList = new ArrayList<>();
        Cursor cursor;

        String selection = JobQueueDbContract.QueueJobEntry.COLUMN_QUEUE_UID + " = ? AND " +
                JobQueueDbContract.QueueJobEntry.COLUMN_JOB_UID + " = ?";
        String selectionArgs[] = {queueUid, name};
        String orderBy = JobQueueDbContract.QueueJobEntry._ID + " ASC";

        // get all jobs
        cursor = database.query(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                JobQueueDbContract.getAllJobColumns(), selection, selectionArgs, null, null, orderBy, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            long id = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry._ID));
            String _name = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_JOB_UID));
            String data = cursor.getString(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_DATA));
            long createdAt = cursor.getLong(cursor.getColumnIndex(JobQueueDbContract.QueueJobEntry.COLUMN_CREATED_AT));

            JobTask jobTask = new JobTask(id, _name, data, createdAt);
            jobsList.add(jobTask);

            cursor.moveToNext();
        }
        cursor.close();

        return jobsList;
    }

    boolean update(@NonNull JobTask jobTask) {
        ContentValues values = new ContentValues();
        values.put(JobQueueDbContract.QueueJobEntry.COLUMN_JOB_UID, jobTask.getName());
        values.put(JobQueueDbContract.QueueJobEntry.COLUMN_DATA, jobTask.getData());
        values.put(JobQueueDbContract.QueueJobEntry.COLUMN_CREATED_AT, jobTask.getCreatedAt());

        String whereClause = JobQueueDbContract.QueueJobEntry._ID +" = ?";
        String whereArgs[] = {String.valueOf(jobTask.getId())};

        long rows = database.update(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                values, whereClause, whereArgs);

        if (rows == 0) {
            Log.i(TAG,"update jobTask [" + jobTask.getId() + "] failed");
            return false;
        }

        Log.i(TAG, "update jobTask [" + jobTask.getId() + "] succeed");
        return true;

    }

    boolean delete(@NonNull JobTask jobTask) {
        String selection = JobQueueDbContract.QueueJobEntry._ID + " = ?";
        String selectionArgs[] = {String.valueOf(jobTask.getId())};

        long rows = database.delete(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                selection, selectionArgs);

        // If no rows where deleted
        if (rows == 0){
            Log.i(TAG,"jobTask [" + jobTask.getId() + "] doesn't exist");
            return false;
        }

        Log.i(TAG, "jobTask [" + jobTask.getId() + "] deleted");
        return true;
    }

    void delete(@NonNull String queueUid, @NonNull String name) {
        String selection = JobQueueDbContract.QueueJobEntry.COLUMN_QUEUE_UID + " = ?" +
                JobQueueDbContract.QueueJobEntry.COLUMN_JOB_UID + " = ?";
        String selectionArgs[] = {queueUid, name};

        long rows = database.delete(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                selection, selectionArgs);

        // If no rows where deleted
        if (rows == 0){
            Log.i(TAG,"jobs [" + name + "] don't exist");
        }

        Log.i(TAG, rows + " jobs [" + name + "] were deleted");
    }

    void clear(@NonNull String queueUid) {
        String whereClause = JobQueueDbContract.QueueJobEntry.COLUMN_QUEUE_UID + " = ?";
        String whereArgs[] = {queueUid};

        long rows = database.delete(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                whereClause, whereArgs);

        // If no rows where deleted
        if (rows == 0){
            Log.i(TAG,"job queue [" + queueUid + "] is empty");
        }

        Log.i(TAG, "job queue [" + queueUid + "] cleared");
    }

    void clear() {
        long rows = database.delete(JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                null, null);

        // If no rows where deleted
        if (rows == 0){
            Log.i(TAG,"job queues are empty");
        }

        Log.i(TAG, "job queues cleared");
    }

    long size(@NonNull String queueUid){
        String selection = JobQueueDbContract.QueueJobEntry.COLUMN_QUEUE_UID + " = ?";
        String selectionArgs[] = {queueUid};
        return DatabaseUtils.queryNumEntries(database, JobQueueDbContract.QueueJobEntry.TABLE_NAME,
                selection, selectionArgs);
    }

}
