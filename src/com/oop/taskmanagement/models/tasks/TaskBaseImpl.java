package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.contracts.tasks.EventLog;


import java.util.List;

public class TaskBaseImpl {

    private int id;
    private String title;
    private String description;
    private List<Comment> comments;
    private EventLog eventlog;

    protected TaskBaseImpl(int id, String title, String description, List<Comment> comments) {
        this.id = id;
        setTitle(title);
        setDescription(description);

    }

    public void setTitle(String title) {

        //validate
        this.title = title;
    }

    public void setDescription(String description) {
        //validate
        this.description = description;
    }


}
