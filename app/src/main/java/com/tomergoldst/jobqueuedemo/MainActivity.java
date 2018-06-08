package com.tomergoldst.jobqueuedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.tomergoldst.jobqueue.JobQueue;
import com.tomergoldst.jobqueue.JobTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private JobQueue mJobQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(getApplicationContext());

        Button addJobBtn = findViewById(R.id.button_add_job);
        Button cancelAllBtn = findViewById(R.id.button_cancel_all);
        Button cancelFirstBtn = findViewById(R.id.button_next);
        Button sizeBtn = findViewById(R.id.button_size);
        Button popBtn = findViewById(R.id.button_pop);

        mJobQueue = JobQueue.getInstance(this);

        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobQueue.add(new JobTask("myJobName", "myJobData"));
            }
        });

        cancelAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobQueue.clear();
            }
        });

        cancelFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobTask jobTask = mJobQueue.next();
                Toast.makeText(MainActivity.this,
                        jobTask != null ? jobTask.toString() : "Empty",
                        Toast.LENGTH_SHORT).show();
            }
        });

        sizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Queue size = " + mJobQueue.size(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobTask jobTask = mJobQueue.pop();
                Toast.makeText(MainActivity.this,
                        jobTask != null ? jobTask.toString() : "Empty",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
