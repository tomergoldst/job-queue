package com.tomergoldst.jobqueue;

import android.support.annotation.NonNull;

import java.util.List;

public interface RepositoryDataSource {

    long storeJob(@NonNull String queueUid, @NonNull JobTask jobTask);
    void deleteJob(@NonNull JobTask jobTask);
    void deleteJob(@NonNull String queueUid, @NonNull String name);
    void clear(@NonNull String queueUid);
    void clear();
    void updateJob(@NonNull JobTask jobTask);
    JobTask getNextJob(@NonNull String queueUid);
    List<JobTask> getJobs(@NonNull String queueUid);
    List<JobTask> getJob(@NonNull String queueUid, @NonNull String name);
    long size(@NonNull String queueUid);

}