package com.oop.taskmanagement.models.contracts.tasks;

public interface Feedback extends TaskBase {

    void changeRating(int rating);

    int getRating();

}
