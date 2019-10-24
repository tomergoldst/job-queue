package com.tomergoldst.jobqueue;

import androidx.annotation.NonNull;

import java.util.List;

public interface RepositoryDataSource {

    long storeJob(@NonNull String queueUid, @NonNull JobTask jobTask);
    void deleteJob(@NonNull JobTask jobTask);
    void deleteJob(@NonNull String queueUid, @NonNull String jobTaskName);
    void clear(@NonNull String queueUid);
    void clear();
    void updateJob(@NonNull JobTask jobTask);
    JobTask getNextJob(@NonNull String queueUid);
    List<JobTask> getJobs(@NonNull String queueUid);
    List<JobTask> getJob(@NonNull String queueUid, @NonNull String jobTaskName);
    long size(@NonNull String queueUid);

}