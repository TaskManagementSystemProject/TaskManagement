package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.enums.StatusType;

import java.util.List;

public class FeedbackImpl extends TaskBaseImpl implements Feedback {


    private int rating;

    protected FeedbackImpl(int id, String title, String description, int rating, List<Comment> comments) {
        super(id, title, description, comments);
        this.rating = rating;
        this.status = StatusType.NEW;
        addEvent("New feedback created successfully");
    }

    @Override
    public void changeStatus(StatusType status) {
        if (status.equals(StatusType.NEW) || status.equals(StatusType.UNSCHEDULED)
                || status.equals(StatusType.SCHEDULED) || status.equals(StatusType.DONE)) {
            addEvent(String.format(ADD_STATUS_CHANGED_TO_EVENTLOG, this.status, status));
            this.status = status;
        } else {
            throw new InvalidUserInputException("Invalid Status provided.");
        }
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

}
