package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.Member;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.StatusType;

import java.util.List;

public class BugImpl extends TaskBaseImpl implements Bug {

    private List<String> stepsToReproduce;
    private PriorityType priority;
    private SeverityType severity;
    private Member assignee;


    protected BugImpl(int id, String title, String description,
                      List<String> stepsToReproduce, PriorityType priority,
                      SeverityType severity, List<Comment> comments, Member assignee) {
        super(id, title, description, comments);

        this.stepsToReproduce = stepsToReproduce;
        this.priority = priority;

        changeSeverity(severity);
        this.status = StatusType.ACTIVE;
        this.assignee = assignee;
        addEvent("New Bug created successfully");

    }

    @Override
    public void changeStatus(StatusType status) {
        if (status.equals(StatusType.ACTIVE) || status.equals(StatusType.DONE)) {
            addEvent(String.format(ADD_STATUS_CHANGED_TO_EVENTLOG, this.status, status));
            super.status = status;
        } else {
            throw new InvalidUserInputException("Invalid Status provided.");
        }
    }

    public void changeSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public void setAssignee(Member assignee) {
        this.assignee = assignee;
    }


    public void setStepsToReproduce(List<String> stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public List<String> getStepsToReproduce() {
        return stepsToReproduce;                 // TODO
    }

    public PriorityType getPriority() {
        return priority;
    }


    public SeverityType getSeverity() {
        return severity;
    }

    public Member getAssignee() {
        return assignee;
    }




}
