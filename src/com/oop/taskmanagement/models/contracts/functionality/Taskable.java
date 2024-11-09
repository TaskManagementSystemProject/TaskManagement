package com.oop.taskmanagement.models.contracts.functionality;

import com.oop.taskmanagement.models.contracts.tasks.TaskBase;

import java.util.List;

public interface Taskable {
    List<TaskBase> getTasks();
    void addTask(TaskBase task);
    void removeTask(TaskBase task);
}
