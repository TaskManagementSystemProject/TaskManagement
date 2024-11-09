package com.oop.taskmanagement.models.contracts;

import com.oop.taskmanagement.models.contracts.tasks.TaskBase;

import java.util.List;

public interface TeamAsset {
    String getName();
    List<TaskBase> getTasks();
    List<String> getActivityHistory();
    void addTask(TaskBase task);
    void logActivity(String activityLog);
}
