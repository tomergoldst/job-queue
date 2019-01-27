package com.tomergoldst.jobqueue;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Repository
 * The repository is the pipeline to all read/write database operations
 */
final class Repository implements RepositoryDataSource {

    private static final String TAG = Repository.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private JobQueueDbHelper dbHelper;
    private JobQueueDao mJobQueueDao;

    Repository(Context context){
        dbHelper = new JobQueueDbHelper(context);
        open();
        mJobQueueDao = new JobQueueDao(database);
    }

    // Open connection to database
    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close connection to database
    void close() {
        dbHelper.close();
    }

    @Override
    public long storeJob(@NonNull String queueUid, @NonNull JobTask jobTask) {
        long id = mJobQueueDao.store(queueUid, jobTask);
        jobTask.setId(id);
        return id;
    }

    @Override
    public void deleteJob(@NonNull JobTask jobTask) {
        mJobQueueDao.delete(jobTask);
    }

    @Override
    public void deleteJob(@NonNull String queueUid, @NonNull String jobTaskName) {
        mJobQueueDao.delete(queueUid, jobTaskName);
    }

    @Override
    public void clear(@NonNull String queueUid) {
        mJobQueueDao.clear(queueUid);
    }

    @Override
    public void clear() {
        mJobQueueDao.clear();
    }

    @Override
    public void updateJob(@NonNull JobTask jobTask) {
        mJobQueueDao.update(jobTask);
    }

    @Override
    public JobTask getNextJob(@NonNull String queueUid) {
        return mJobQueueDao.getNext(queueUid);
    }

    @Override
    public List<JobTask> getJobs(@NonNull String queueUid) {
        return mJobQueueDao.getJobs(queueUid);
    }

    @Override
    public List<JobTask> getJob(@NonNull String queueUid, @NonNull String jobTaskName) {
        return mJobQueueDao.getJob(queueUid, jobTaskName);
    }

    @Override
    public long size(@NonNull String queueUid) {
        return mJobQueueDao.size(queueUid);
    }

}
