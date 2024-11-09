package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.enums.StatusType;

import java.util.List;

public class BugImpl extends TaskBaseImpl {


    protected BugImpl(int id, String title, String description, StatusType status, List<Comment> comments) {
        super(id, title, description, status, comments);
    }

    @Override
    public void validateStatus(StatusType status) {
        if (status.equals(StatusType.ACTIVE) || status.equals(StatusType.DONE)) {
            super.status = status;
        }else {
            throw new InvalidUserInputException("Invalid Status provided.");
        }
    }


}
