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
    protected static final String ADD_SEVERITY_CHANGED_TO_EVENTLOG = "Severity changed from %s to %s.";
    protected static final String ADD_RATING_CHANGED_TO_EVENTLOG = "Rating changed from %d to %d.";
    private static final String NEW_COMMENT_ADDED = "Comment added successfully";
    private static final String TO_STRING_FORMAT = "Task with id: %d%nTitle: %s%nStatus: %s%n";  // new
    private static final String EXTRA_INFO_FORMAT = " with id: %d%nTitle: %s%nStatus: %s%n";  // new

    private final int id;
    private String title;
    private String description;
    protected StatusType status;
    private final List<Comment> comments;
    private final List<String> eventLog;
    private String assigneeName;
    protected TaskBaseImpl(int id, String title, String description) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        comments = new ArrayList<>();
        eventLog = new ArrayList<>();
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    @Override
    public abstract void changeStatus(StatusType status);

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
    public void addComment(Comment comment) {
        this.comments.add(comment);
        addEvent(NEW_COMMENT_ADDED);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public List<String> getEventLog() {
        return new ArrayList<>(eventLog);
    }

    protected String baseInfo(){
        return String.format(EXTRA_INFO_FORMAT, getId(),getTitle(),getStatus());

    }
    @Override // new implementation
    public String toString(){
        return String.format(TO_STRING_FORMAT, getId(),getTitle(),getStatus());
    }
}
