# JobQueue
Persisted job queue for android.
Light and easy to use

## Instructions
Add a dependency to your app build.gradle
```groovy
dependencies {
    compile 'com.tomergoldst.android:jobqueue:1.1.1'
}
```

add a job to queue:
```java
JobTask jobTask = new JobTask("myJobName", "myJobData")
JobQueue.getInstance(this).add(jobTask);
```

get next job on queue::
```java
JobQueue.getInstance(this).getNext();
```

remove job from queue::
```java
JobQueue.getInstance(this).remove(jobTask);
```

remove all jobs from queue::
```java
JobQueue.getInstance(this).clear();
```

get all jobs on queue::
```java
List<JobTask> tasks = JobQueue.getInstance(this).getJobs();
```

get all jobs of the same task on queue::
```java
List<JobTask> jobs = JobQueue.getInstance(this).getJob("myJobName");
```

get queue size:
```java
long size = JobQueue.getInstance(this).size();
```

### License
```
Copyright 2016 Tomer Goldstein

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


