package com.tomergoldst.jobqueue;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class JobQueue {

    private static Client sClient = null;

    /**
     * Gets the default instance.
     *
     * @return default instance
     */
    private static Client getInstance() {
        if (sClient == null){
            sClient = new Client();
        }
        return sClient;
    }

    /**
     * Initialize the SDK with the Android app context
     * Initializing is required before calling other methods.
     *
     * @param context context
     */
    public static void initialize(Context context) {
        getInstance().initialize(context);
    }

    public static void add(@NonNull String queueUid, @NonNull JobTask jobTask){
        getInstance().add(queueUid, jobTask);
    }

    @Nullable
    public static JobTask peek(@NonNull String queueUid){
        return getInstance().peek(queueUid);
    }

    @Nullable
    public static JobTask pop(@NonNull String queueUid){
        return getInstance().pop(queueUid);
    }

    public static void remove(@NonNull JobTask jobTask){
        getInstance().remove(jobTask);
    }

    public static void clear(@NonNull String queueUid){
        getInstance().clear(queueUid);
    }

    public static List<JobTask> getJobs(@NonNull String queueUid){
        return getInstance().getJobs(queueUid);
    }

    public static List<JobTask> getJob(@NonNull String queueUid, String name){
        return getInstance().getJob(queueUid, name);
    }

    public static long size(@NonNull String queueUid){
        return getInstance().size(queueUid);
    }

}
