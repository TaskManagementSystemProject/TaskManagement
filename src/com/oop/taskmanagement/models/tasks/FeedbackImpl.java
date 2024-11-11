package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;

import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.enums.StatusType;

public class FeedbackImpl extends TaskBaseImpl implements Feedback {

    private static final String TO_STRING_FORMAT = "%sRating: %d%nAssigned to: %s"; // new
    private int rating;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        this.rating = rating;
        this.status = StatusType.NEW;
    }

    @Override
    public void changeStatus(StatusType status) {
        if (status.equals(StatusType.NEW) || status.equals(StatusType.UNSCHEDULED)
                || status.equals(StatusType.SCHEDULED) || status.equals(StatusType.DONE)) {
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
    public void changeRating(int rating) {
        if (rating == this.rating) {
            throw new InvalidUserInputException(String.format("Rating is already %d", rating));
        }
        addEvent(String.format(ADD_RATING_CHANGED_TO_EVENTLOG, this.rating, rating));
        this.rating = rating;
    }

    @Override
    public int getRating() {
        return rating;
    }


    @Override // new implementation
    public String toString() {
        return String.format(TO_STRING_FORMAT, super.toString(), getRating(), getAssigneeName());
    }

}
