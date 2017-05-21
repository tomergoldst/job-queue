package com.tomergoldst.jobqueuedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tomergoldst.jobqueue.JobQueue;
import com.tomergoldst.jobqueue.JobTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Stetho.initializeWithDefaults(getApplicationContext());

        Button addJobBtn = (Button) findViewById(R.id.button_add_job);
        Button cancelAllBtn = (Button) findViewById(R.id.button_cancel_all);
        Button cancelFirstBtn = (Button) findViewById(R.id.button_cancel_first);

        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobQueue.add(getBaseContext(),
                        new JobTask("job 1", "some data"));
            }
        });

        cancelAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobQueue.clear(getBaseContext());
            }
        });

        cancelFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobTask task = JobQueue.getNext(getBaseContext());
                if (task != null) {
                    JobQueue.cancel(getBaseContext(), task);
                }
            }
        });

    }
}
