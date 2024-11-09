package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.models.contracts.tasks.Comment;

import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.TaskValidation;


import java.util.ArrayList;
import java.util.List;

public abstract class TaskBaseImpl implements TaskBase {
    protected static final String ADD_STATUS_CHANGED_TO_EVENTLOG = "Status changed from %s to %s.";
    protected static final String ADD_PRIORITY_CHANGED_TO_EVENTLOG = "Priority changed from %s to %s.";
    protected static final String ADD_SIZE_CHANGED_TO_EVENTLOG = "Size changed from %s to %s.";
    protected static final String ADD_ASSIGNEE_CHANGED_TO_EVENTLOG = "Assignee changed from %s to %s.";
    protected static final String ADD_SEVERITY_CHANGED_TO_EVENTLOG = "Severity changed from %s to %s.";
    protected static final String ADD_RATING_CHANGED_TO_EVENTLOG = "Rating changed from %d to %d.";
    private static final String NEW_COMMENT_ADDED = "Comment added successfully";

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

    public void addComment(Comment comment) {
        this.comments.add(comment);
        addEvent(NEW_COMMENT_ADDED);
    }

    protected void addEvent(String event) {
        this.eventLog.add(event);
    }

    // no eventLog needed   --  can be changed to final later
    private void setTitle(String title) {
        TaskValidation.validateTitle(title);
        this.title = title;
    }

    // no eventLog needed   --  can be changed to final later
    private void setDescription(String description) {
        TaskValidation.validateDescription(description);
        this.description = description;
    }

    @Override
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
