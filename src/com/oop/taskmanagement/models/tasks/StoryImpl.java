package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.enums.StatusType;



public class StoryImpl extends TaskBaseImpl implements Story {


    private PriorityType priority;
    private SizeType size;

    protected StoryImpl(int id, String title, String description,
                        PriorityType priority, SizeType size) {
        super(id, title, description);
        this.priority = priority;
        this.size = size;
        this.status = StatusType.NOT_DONE;
        addEvent("New Story created successfully");


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

    public void changePriority(PriorityType priority) {
        addEvent(String.format(ADD_PRIORITY_CHANGED_TO_EVENTLOG, this.priority, priority));
        this.priority = priority;
    }

    public void changeSize(SizeType size) {
        addEvent(String.format(ADD_SIZE_CHANGED_TO_EVENTLOG, this.size, size));
        this.size = size;
    }


                                                                    //  GETTERS
    public PriorityType getPriority() {
        return priority;
    }

    public SizeType getSize() {
        return size;
    }


}
