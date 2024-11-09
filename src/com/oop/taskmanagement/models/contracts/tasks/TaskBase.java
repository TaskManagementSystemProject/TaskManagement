package com.oop.taskmanagement.models.contracts.tasks;

import com.oop.taskmanagement.models.enums.StatusType;

import java.util.List;

public interface TaskBase {

    abstract void changeStatus(StatusType status);

    void addComment(Comment comment);

    int getId();

    String getTitle();

    String getDescription();

    StatusType getStatus();

    List<Comment> getComments();

    List<String> getEventLog();

}
