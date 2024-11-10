package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends TaskBaseImpl implements Bug {

    private static final String TO_STRING_FORMAT ="%sPriority: %s%nSeverity: %s%nAssigned to: %s"; // new
    private final List<String> stepsToReproduce;
    private PriorityType priority;
    private SeverityType severity;


    public BugImpl(int id, String title, String description,
                   List<String> stepsToReproduce, PriorityType priority,
                   SeverityType severity) {
        super(id, title, description);

        this.stepsToReproduce = stepsToReproduce;
        this.priority = priority;

        // changeSeverity(severity);  removed
        this.severity = severity; // new
        this.status = StatusType.ACTIVE;
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

    @Override
    public void changeSeverity(SeverityType severity) {
        addEvent(String.format(ADD_SEVERITY_CHANGED_TO_EVENTLOG, this.severity, severity));
        this.severity = severity;
    }

    @Override
    public void changePriority(PriorityType priority) {
        addEvent(String.format(ADD_PRIORITY_CHANGED_TO_EVENTLOG, this.priority, priority));
        this.priority = priority;
    }


    @Override
    public List<String> getStepsToReproduce() {
        return new ArrayList<>(stepsToReproduce);    // TODO
    }

    @Override
    public PriorityType getPriority() {
        return priority;
    }

    @Override
    public SeverityType getSeverity() {
        return severity;
    }


    /*
    @Override
    public String toString() {
        StringBuilder stringFromTaskBase = new StringBuilder(super.toString());
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Priority: %s", priority)).append(System.lineSeparator());
        sb.append(String.format("Severity: %s", severity)).append(System.lineSeparator());
        stringFromTaskBase.append(String.format(stringFromTaskBase.toString(), sb));
        stringFromTaskBase.append(String.format("Assigned to: %s", getAssigneeName())).append(System.lineSeparator());

        return stringFromTaskBase.toString();
    }
    older version
     */
    @Override // new implementation
    public String toString() {
        return String.format(TO_STRING_FORMAT, super.toString(), getPriority(), getSeverity(), getAssigneeName());
    }
}
