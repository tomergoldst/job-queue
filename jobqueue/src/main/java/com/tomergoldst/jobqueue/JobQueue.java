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

    public static void clear(Context context){
        DatabaseManager.getInstance().clear(context);
    }

    public static void Completed(Context context, JobTask jobTask){
        cancel(context, jobTask);
    }

    public static List<JobTask> getJobs(Context context){
        return DatabaseManager.getInstance().getJobs(context);
    }

    public static List<JobTask> getJob(Context context, String name){
        return DatabaseManager.getInstance().getJob(context, name);
    }

}
