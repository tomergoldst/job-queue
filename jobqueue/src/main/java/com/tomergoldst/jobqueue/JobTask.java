package com.tomergoldst.jobqueue;

import java.util.Date;

/**
 * Created by Tomer on 18/03/2017.
 *
 */

public class JobTask {

    private static final String TAG = JobTask.class.getSimpleName();

    private long id;
    private String name;
    private String params;
    private String data;
    private long createdAt;

    public JobTask(String name, String data) {
        this.name = name;
        this.data = data;
        this.createdAt = new Date().getTime();
    }

    public JobTask(String name, String params, String data) {
        this(name, data);
        this.params = params;
    }

    JobTask(long id, String name, String params, String data, long createdAt) {
        this.id = id;
        this.name = name;
        this.params = params;
        this.data = data;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "JobTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", params='" + params + '\'' +
                ", data='" + data + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
