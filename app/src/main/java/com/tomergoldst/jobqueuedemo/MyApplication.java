package com.tomergoldst.jobqueuedemo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.tomergoldst.jobqueue.JobQueue;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize Stetho for database debug purpose
        Stetho.initializeWithDefaults(this);

        // initialize JobQueue library
        JobQueue.initialize(this);
    }

}
