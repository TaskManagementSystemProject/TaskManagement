package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.enums.StatusType;

public class StoryImpl extends TaskBaseImpl implements Story {

    private static final String TO_STRING_FORMAT = "%sPriority: %s%nSize: %s%nAssigned to: %s"; // new
    private PriorityType priority;
    private SizeType size;

    public StoryImpl(int id, String title, String description,
                     PriorityType priority, SizeType size) {
        super(id, title, description);
        this.priority = priority;
        this.size = size;
        this.status = StatusType.NOT_DONE;
    }

    //  SETTERS
    @Override
    public void changeStatus(StatusType status) {
        if (status.equals(StatusType.NOT_DONE) || status.equals(StatusType.IN_PROGRESS) || status.equals(StatusType.DONE)) {
            if (status.equals(this.status)) {
                throw new InvalidUserInputException(String.format("Status is already at %s", status));
            }
            addEvent(String.format(ADD_STATUS_CHANGED_TO_EVENTLOG, this.status, status));
            this.status = status;
        } else {
            throw new InvalidUserInputException("Invalid Status provided.");
        }
    }

    @Override
    public void changePriority(PriorityType priority) {
        if (priority.equals(this.priority)) {
            throw new InvalidUserInputException(String.format("Priority is already at %s", priority));
        }
        addEvent(String.format(ADD_PRIORITY_CHANGED_TO_EVENTLOG, this.priority, priority));
        this.priority = priority;
    }

    @Override
    public void changeSize(SizeType size) {
        if (size.equals(this.size)) {
            throw new InvalidUserInputException(String.format("Size is already at %s", size));
        }
        addEvent(String.format(ADD_SIZE_CHANGED_TO_EVENTLOG, this.size, size));
        this.size = size;
    }

    @Override                                               //  GETTERS
    public PriorityType getPriority() {
        return priority;
    }

    @Override
    public SizeType getSize() {
        return size;
    }

    @Override // new implementation
    public String toString() {
        return String.format("Story" + TO_STRING_FORMAT, baseInfo(), getPriority(), getSize(),
                getAssigneeName() == null ? "None" : getAssigneeName());
    }
}
