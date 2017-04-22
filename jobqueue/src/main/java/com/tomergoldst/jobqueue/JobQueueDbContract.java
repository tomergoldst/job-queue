package com.tomergoldst.jobqueue;

import android.provider.BaseColumns;

/**
 * Created by Tomer on 18/03/2017.
 *
 */
class JobQueueDbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public JobQueueDbContract() {}

    /* Inner class that defines the table contents */
    static abstract class JobEntry implements BaseColumns {
        static final String TABLE_NAME = "job_queue";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_DATA = "data";
        static final String COLUMN_CREATED_AT = "created_at";
    }

    static String[] getAllJobColumns() {
        return new String[]{
                JobEntry._ID,
                JobEntry.COLUMN_NAME,
                JobEntry.COLUMN_DATA,
                JobEntry.COLUMN_CREATED_AT
        };
    }
}

