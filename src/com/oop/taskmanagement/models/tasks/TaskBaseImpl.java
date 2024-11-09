package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.models.contracts.tasks.Comment;

import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.TaskValidation;


import java.util.ArrayList;
import java.util.List;

public abstract class TaskBaseImpl implements TaskBase {
    protected static final String ADD_STATUS_CHANGED_TO_EVENTLOG = "Status changed from %s to %s.";

    private final int id;
    private String title;
    private String description;
    protected StatusType status;
    private List<Comment> comments;
    private List<String> eventLog;

    protected TaskBaseImpl(int id, String title, String description, List<Comment> comments) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        this.comments = comments;
        eventLog = new ArrayList<>();
    }

    public abstract void changeStatus(StatusType status);

    protected void addEvent(String event){
        this.eventLog.add(event);
    }

    private void setTitle(String title) {
        TaskValidation.validateTitle(title);
        this.title = title;
    }

    private void setDescription(String description) {
        TaskValidation.validateDescription(description);
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public StatusType getStatus() {
        return status;
    }

    public List<Comment> getComments() {
        return comments;                        //TODO
    }



    public List<String> getEventLog() {
        return eventLog;                        //TODO
    }



}
