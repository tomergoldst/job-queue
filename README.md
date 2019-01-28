# JobQueue
Persisted job queue for android.

Manage your jobs flow by working with a queue. You can create as many queues as you need

Light and easy to use

## Dependency
Add a dependency to your app build.gradle
```groovy
dependencies {
    compile 'com.tomergoldst.android:jobqueue:2.0.0'
}
```

## Instructions

Init JobQueue at your application class onCreate()
```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JobQueue.initialize(this);
    }
}
```

add a job to queue:
```java
final String NETWORK_QUEUE = "network_queue"
JobTask jobTask = new JobTask("myJobName", "myJobData")
JobQueue.add(NETWORK_QUEUE, jobTask);
```

pop next job:
```java
JobTask jobTask = JobQueue.pop(NETWORK_QUEUE);
```

peek on next job:
```java
JobTask jobTask = JobQueue.peek(NETWORK_QUEUE);
```

remove job from queue:
```java
JobQueue.remove(jobTask);
```

remove all jobs from queue:
```java
JobQueue.clearQueue(NETWORK_QUEUE);
```

get all jobs on queue:
```java
List<JobTask> jobs = JobQueue.getJobs(NETWORK_QUEUE);
```

get all jobs of the same task on queue:
```java
List<JobTask> jobs = JobQueue.getJob(NETWORK_QUEUE, "myJobName");
```

get queue size:
```java
long size = JobQueue.size(NETWORK_QUEUE);
```

### License
```
Copyright 2019 Tomer Goldstein

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


