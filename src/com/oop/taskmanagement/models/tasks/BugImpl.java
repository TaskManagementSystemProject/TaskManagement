package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.StatusType;

import java.util.List;

public class BugImpl extends TaskBaseImpl implements Bug {

    private final List<String> stepsToReproduce;
    private PriorityType priority;
    private SeverityType severity;


    protected BugImpl(int id, String title, String description,
                      List<String> stepsToReproduce, PriorityType priority,
                      SeverityType severity) {
        super(id, title, description);

        this.stepsToReproduce = stepsToReproduce;
        this.priority = priority;

        changeSeverity(severity);
        this.status = StatusType.ACTIVE;
        addEvent("New Bug created successfully");

    }

    @Override
    public void changeStatus(StatusType status) {
        if (status.equals(StatusType.ACTIVE) || status.equals(StatusType.DONE)) {
            addEvent(String.format(ADD_STATUS_CHANGED_TO_EVENTLOG, this.status, status));
            this.status = status;
        } else {
            throw new InvalidUserInputException("Invalid Status provided.");
        }
    }

    public void changeSeverity(SeverityType severity) {
        addEvent(String.format(ADD_SEVERITY_CHANGED_TO_EVENTLOG, this.severity, severity));
        this.severity = severity;
    }



    public void changePriority(PriorityType priority) {
        addEvent(String.format(ADD_PRIORITY_CHANGED_TO_EVENTLOG, this.priority, priority));
        this.priority = priority;
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





}
