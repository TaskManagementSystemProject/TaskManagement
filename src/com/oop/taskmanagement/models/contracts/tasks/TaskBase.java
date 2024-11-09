package com.oop.taskmanagement.models.contracts.tasks;

import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.models.enums.TaskType;

import java.util.List;

public interface TaskBase {

    abstract void changeStatus(StatusType status);

    void addComment(Comment comment);

    int getId();

    String getTitle();

    TaskType getTaskType();

    String getDescription();

    StatusType getStatus();

    List<Comment> getComments();

    List<String> getEventLog();

}
