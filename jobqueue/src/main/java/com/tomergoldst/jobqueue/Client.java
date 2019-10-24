package com.tomergoldst.jobqueue;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import static com.tomergoldst.jobqueue.Preconditions.checkNotNull;

class Client {

    private static final String TAG = Client.class.getSimpleName();

    private static final String DEFAULT_QUEUE_UID = "default";

    private RepositoryDataSource mRepository;

    void initialize(final Context context){
        Context appContext = context.getApplicationContext();
        mRepository = new Repository(appContext);
    }

    void add(@NonNull String queueUid, @NonNull JobTask jobTask){
        checkNotNull(jobTask, "job task cannot be null");
        mRepository.storeJob(queueUid, jobTask);
    }

    @Nullable
    JobTask peek(@NonNull String queueUid){
        return mRepository.getNextJob(queueUid);
    }

    @Nullable
    JobTask pop(@NonNull String queueUid){
        JobTask jobTask = peek(queueUid);
        if (jobTask == null) {
            return null;
        }

        mRepository.deleteJob(jobTask);
        return jobTask;
    }

    void remove(@NonNull JobTask jobTask){
        checkNotNull(jobTask, "job task cannot be null");
        mRepository.deleteJob(jobTask);
    }

    void update(@NonNull JobTask jobTask){
        checkNotNull(jobTask, "job task cannot be null");
        mRepository.updateJob(jobTask);
    }

    void remove(@NonNull String queueUid, @NonNull String jobTaskName){
        mRepository.deleteJob(queueUid, jobTaskName);
    }

    void clearQueue(@NonNull String queueUid){
        mRepository.clear(queueUid);
    }

    void clearAll(){
        mRepository.clear();
    }

    List<JobTask> getJobs(@NonNull String queueUid){
        return mRepository.getJobs(queueUid);
    }

    List<JobTask> getJob(@NonNull String queueUid, String jobTaskName){
        return mRepository.getJob(DEFAULT_QUEUE_UID, jobTaskName);
    }

    long size(@NonNull String queueUid){
        return mRepository.size(queueUid);
    }

}
