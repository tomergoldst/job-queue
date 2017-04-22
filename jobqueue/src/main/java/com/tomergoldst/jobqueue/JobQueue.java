package com.tomergoldst.jobqueue;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Tomer on 18/03/2017.
 */

public class JobQueue {

    private static final String TAG = JobQueue.class.getSimpleName();

    public static void add(Context context, JobTask jobTask){
        DatabaseManager.getInstance().storeJob(context, jobTask);
    }

    @Nullable
    public static JobTask getNext(Context context){
        return DatabaseManager.getInstance().getNextJob(context);
    }

    public static void cancel(Context context, @NonNull JobTask jobTask){
        DatabaseManager.getInstance().deleteJob(context, jobTask);
    }

    public static void cancelAll(Context context){
        List<JobTask> jobTasks = DatabaseManager.getInstance().getJobs(context);
        if (jobTasks != null) {
            for (JobTask jobTask : jobTasks) {
                cancel(context, jobTask);
            }
        }
    }

    public static void Completed(Context context, JobTask jobTask){
        cancel(context, jobTask);
    }

}
