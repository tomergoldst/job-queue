package com.tomergoldst.jobqueue;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Tomer on 18/03/2017.
 *
 */
class DatabaseManager {

    private static final String TAG = DatabaseManager.class.getSimpleName();

    private static DatabaseManager mDbManagerInstance;

    private DatabaseManager(){
    }

    static synchronized DatabaseManager getInstance() {
        if (mDbManagerInstance == null) {
            mDbManagerInstance = new DatabaseManager();
        }
        return mDbManagerInstance;
    }

    /**
     * Store jobTask to database
     * @param ctx context
     * @param jobTask jobTask
     * @return
     */
    synchronized long storeJob(Context ctx, @NonNull JobTask jobTask) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        long id = jobQueueDao.store(jobTask);
        jobQueueDao.close();
        jobTask.setId(id);
        return id;
    }

    /**
     * Delete JobTask from database
     * @param ctx context
     * @param jobTask jobTask
     * @return
     */
    synchronized boolean deleteJob(Context ctx, @NonNull JobTask jobTask) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        boolean success = jobQueueDao.delete(jobTask);
        jobQueueDao.close();
        return success;
    }

    /**
     * Delete Jobs from database
     * @param ctx context
     * @param name job name
     * @return
     */
    synchronized boolean deleteJob(Context ctx, String name) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        boolean success = jobQueueDao.delete(name);
        jobQueueDao.close();
        return success;
    }

    /**
     * clear Jobs from database
     * @param ctx context
     * @return
     */
    synchronized void clear(Context ctx) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        jobQueueDao.clear();
        jobQueueDao.close();
    }

    /**
     * Update jobTask data
     * @param ctx context
     * @param jobTask jobTask
     * @return
     */
    synchronized boolean updateJob(Context ctx, JobTask jobTask) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        boolean success = jobQueueDao.update(jobTask);
        jobQueueDao.close();
        return success;
    }

    /**
     * Get next job on queue
     * @param ctx context
     * @return
     */
    synchronized JobTask getNextJob(Context ctx) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        JobTask jobTask = jobQueueDao.getNext();
        jobQueueDao.close();
        return jobTask;
    }

    /**
     * get all jobs on queue
     * @param ctx context
     * @return
     */
    synchronized List<JobTask> getJobs(Context ctx) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        List<JobTask> items = jobQueueDao.getJobs();
        jobQueueDao.close();
        return items;
    }

    /**
     * get all jobs on queue with the name provided
     * @param ctx context
     * @return
     */
    synchronized List<JobTask> getJob(Context ctx, String name) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        List<JobTask> items = jobQueueDao.getJob(name);
        jobQueueDao.close();
        return items;
    }

    /**
     * get all jobs on queue with the name provided
     * @param ctx context
     * @return
     */
    synchronized long size(Context ctx) {
        JobQueueDao jobQueueDao = JobQueueDao.getInstance(ctx);
        jobQueueDao.open();
        long size = jobQueueDao.size();
        jobQueueDao.close();
        return size;
    }


}
