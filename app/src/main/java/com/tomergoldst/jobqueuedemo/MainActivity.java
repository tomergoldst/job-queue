package com.tomergoldst.jobqueuedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.tomergoldst.jobqueue.JobQueue;
import com.tomergoldst.jobqueue.JobTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String QUEUE_1 = "queue1";
    private static final String QUEUE_2 = "queue2";
    private static final String QUEUE_3 = "queue3";

    private String mCurrentQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Stetho for database debug purpose
        Stetho.initializeWithDefaults(getApplicationContext());

        // initialize JobQueue library
        JobQueue.initialize(getApplicationContext());

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonQueue1) mCurrentQueue = QUEUE_1;
                else if (checkedId == R.id.radioButtonQueue2) mCurrentQueue = QUEUE_2;
                else mCurrentQueue = QUEUE_3;
            }
        });

        radioGroup.check(R.id.radioButtonQueue1);

        Button addJobBtn = findViewById(R.id.button_add_job);
        Button popBtn = findViewById(R.id.button_pop);
        Button peekBtn = findViewById(R.id.button_peek);
        Button sizeBtn = findViewById(R.id.button_size);
        Button cancelAllBtn = findViewById(R.id.button_cancel_all);

        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobQueue.add(mCurrentQueue, new JobTask("myJobName", "myJobData"));
            }
        });

        cancelAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobQueue.clear(mCurrentQueue);
            }
        });

        sizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Queue size = " + JobQueue.size(mCurrentQueue),
                        Toast.LENGTH_SHORT).show();
            }
        });

        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobTask jobTask = JobQueue.pop(mCurrentQueue);
                Toast.makeText(MainActivity.this,
                        jobTask != null ? jobTask.toString() : "Empty",
                        Toast.LENGTH_SHORT).show();
            }
        });

        peekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobTask jobTask = JobQueue.peek(mCurrentQueue);
                Toast.makeText(MainActivity.this,
                        jobTask != null ? jobTask.toString() : "Empty",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
