package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.Member;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.enums.StatusType;

import java.util.List;

public class StoryImpl extends TaskBaseImpl implements Story {


    private PriorityType priority;
    private SizeType size;
    private Member assignee;

    protected StoryImpl(int id, String title, String description,
                        PriorityType priority, SizeType size, Member assignee, List<Comment> comments) {
        super(id, title, description, comments);
        this.priority = priority;
        this.size = size;
        this.status = StatusType.NOT_DONE;
        this.assignee = assignee;
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

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public void setSize(SizeType size) {
        this.size = size;
    }

    public void setAssignee(Member assignee) {
        this.assignee = assignee;
    }

                                                                    //  GETTERS
    public PriorityType getPriority() {
        return priority;
    }

    public SizeType getSize() {
        return size;
    }

    public Member getAssignee() {
        return assignee;
    }
}
