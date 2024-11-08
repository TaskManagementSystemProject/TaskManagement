package com.oop.taskmanagement.models.enums.StoryEnums;

public enum StatusType {
    NOT_DONE,
    IN_PROGRESS,
    DONE;

    @Override
    public String toString() {
        switch (this) {
            case NOT_DONE:
                return "Not Done";
            case IN_PROGRESS:
                return "InProgress";
            case DONE:
                return "Done";
            default:
                return "";
        }
    }
}
