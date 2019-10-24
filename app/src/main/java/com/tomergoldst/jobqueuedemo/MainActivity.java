package com.tomergoldst.jobqueuedemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tomergoldst.jobqueue.JobQueue;
import com.tomergoldst.jobqueue.JobTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String QUEUE_1 = "queue1";
    private static final String QUEUE_2 = "queue2";
    private static final String QUEUE_3 = "queue3";

    private String mCurrentQueueUid;

    private AppCompatTextView mQueue1SizeTxv;
    private AppCompatTextView mQueue2SizeTxv;
    private AppCompatTextView mQueue3SizeTxv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonQueue1) mCurrentQueueUid = QUEUE_1;
                else if (checkedId == R.id.radioButtonQueue2) mCurrentQueueUid = QUEUE_2;
                else mCurrentQueueUid = QUEUE_3;
            }
        });

        radioGroup.check(R.id.radioButtonQueue1);

        mQueue1SizeTxv = findViewById(R.id.textQueue1Size);
        mQueue2SizeTxv = findViewById(R.id.textQueue2Size);
        mQueue3SizeTxv = findViewById(R.id.textQueue3Size);

        mQueue1SizeTxv.setText(String.valueOf(JobQueue.size(QUEUE_1)));
        mQueue2SizeTxv.setText(String.valueOf(JobQueue.size(QUEUE_2)));
        mQueue3SizeTxv.setText(String.valueOf(JobQueue.size(QUEUE_3)));

        Button addJobBtn = findViewById(R.id.button_add_job);
        Button popBtn = findViewById(R.id.button_pop);
        Button peekBtn = findViewById(R.id.button_peek);
        Button sizeBtn = findViewById(R.id.button_size);
        Button cancelAllBtn = findViewById(R.id.button_cancel_all);

        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobQueue.add(mCurrentQueueUid, new JobTask("myJobName", "myJobData"));
                updateQueueSize(mCurrentQueueUid);
            }
        });

        cancelAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobQueue.clearQueue(mCurrentQueueUid);
                updateQueueSize(mCurrentQueueUid);
            }
        });

        sizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Queue size = " + JobQueue.size(mCurrentQueueUid),
                        Toast.LENGTH_SHORT).show();
            }
        });

        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobTask jobTask = JobQueue.pop(mCurrentQueueUid);
                Toast.makeText(MainActivity.this,
                        jobTask != null ? jobTask.toString() : "Empty",
                        Toast.LENGTH_SHORT).show();
                updateQueueSize(mCurrentQueueUid);
            }
        });

        peekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobTask jobTask = JobQueue.peek(mCurrentQueueUid);
                Toast.makeText(MainActivity.this,
                        jobTask != null ? jobTask.toString() : "Empty",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateQueueSize(String currentQueueUid){
        switch (currentQueueUid) {
            case QUEUE_1:
                mQueue1SizeTxv.setText(String.valueOf(JobQueue.size(QUEUE_1)));
                break;
            case QUEUE_2:
                mQueue2SizeTxv.setText(String.valueOf(JobQueue.size(QUEUE_2)));
                break;
            default:
                mQueue3SizeTxv.setText(String.valueOf(JobQueue.size(QUEUE_3)));
                break;
        }
    }
}
