package com.tomergoldst.jobqueue;

import android.provider.BaseColumns;

class JobQueueDbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public JobQueueDbContract() {}

    /* Inner class that defines the table contents */
    static abstract class QueueJobEntry implements BaseColumns {
        static final String TABLE_NAME = "job_queue";
        static final String COLUMN_QUEUE_UID = "queue_uid";
        static final String COLUMN_JOB_UID = "job_uid";
        static final String COLUMN_DATA = "data";
        static final String COLUMN_CREATED_AT = "created_at";
    }

    static String[] getAllJobColumns() {
        return new String[]{
                QueueJobEntry._ID,
                QueueJobEntry.COLUMN_QUEUE_UID,
                QueueJobEntry.COLUMN_JOB_UID,
                QueueJobEntry.COLUMN_DATA,
                QueueJobEntry.COLUMN_CREATED_AT
        };
    }
}

