package com.tomergoldst.jobqueue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Tomer on 18/03/2017.
 */

public class JobQueue {

    @SuppressLint("StaticFieldLeak")
    private static JobQueue mInstance;

    private final Context mContext;

    private static final String TAG = JobQueue.class.getSimpleName();

    private JobQueue(Context context) {
        mContext = context.getApplicationContext();
    }

    public static synchronized JobQueue getInstance(Context context){
        if (mInstance == null){
            mInstance = new JobQueue(context);
        }

        return mInstance;
    }

    public void add(JobTask jobTask){
        DatabaseManager.getInstance().storeJob(mContext, jobTask);
    }

    @Nullable
    public JobTask getNext(){
        return DatabaseManager.getInstance().getNextJob(mContext);
    }

    public void cancel(@NonNull JobTask jobTask){
        DatabaseManager.getInstance().deleteJob(mContext, jobTask);
    }

    public void clear(){
        DatabaseManager.getInstance().clear(mContext);
    }

    public void Completed(JobTask jobTask){
        cancel(jobTask);
    }

    public List<JobTask> getJobs(){
        return DatabaseManager.getInstance().getJobs(mContext);
    }

    public List<JobTask> getJob(String name){
        return DatabaseManager.getInstance().getJob(mContext, name);
    }

}
