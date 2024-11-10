package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.enums.StatusType;


public class StoryImpl extends TaskBaseImpl implements Story {


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
            addEvent(String.format(ADD_STATUS_CHANGED_TO_EVENTLOG, this.status, status));
            this.status = status;
        } else {
            throw new InvalidUserInputException("Invalid Status provided.");
        }
    }

    @Override
    public void changePriority(PriorityType priority) {
        addEvent(String.format(ADD_PRIORITY_CHANGED_TO_EVENTLOG, this.priority, priority));
        this.priority = priority;
    }

    @Override
    public void changeSize(SizeType size) {
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


    @Override
    public String toString() {
        StringBuilder stringFromTaskBase = new StringBuilder(super.toString());
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Priority: %s", priority)).append(System.lineSeparator());
        sb.append(String.format("Size: %s", size)).append(System.lineSeparator());
        stringFromTaskBase.append(String.format(stringFromTaskBase.toString(), sb));
        stringFromTaskBase.append(String.format("Assigned to: %s", getAssigneeName())).append(System.lineSeparator());

        return stringFromTaskBase.toString();
    }

}
