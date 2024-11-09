package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.models.contracts.tasks.Comment;

import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.TaskValidation;


import java.util.ArrayList;
import java.util.List;

public abstract class TaskBaseImpl implements TaskBase {

    private int id;
    private String title;
    private String description;
    protected StatusType status;
    private List<Comment> comments;
    private ArrayList<String> eventLog;

    protected TaskBaseImpl(int id, String title, String description, StatusType status, List<Comment> comments) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        this.comments = comments;
        eventLog = new ArrayList<>();
        validateStatus(status);

    }

    public abstract void validateStatus(StatusType status);




    private void setTitle(String title) {
        TaskValidation.validateTitle(title);
        this.title = title;
    }

    private void setDescription(String description) {
        TaskValidation.validateDescription(description);
        this.description = description;
    }


}
