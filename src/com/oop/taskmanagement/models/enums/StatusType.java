package com.oop.taskmanagement.models.enums;

public enum StatusType {
    NEW,
    UNSCHEDULED,
    SCHEDULED,
    NOT_DONE,
    IN_PROGRESS,
    ACTIVE,
    DONE;

    @Override
    public String toString() {
        return switch (this) {
            case NEW -> "New";
            case UNSCHEDULED -> "Unscheduled";
            case SCHEDULED -> "Scheduled";
            case NOT_DONE -> "Not done";
            case IN_PROGRESS -> "In progress";
            case ACTIVE -> "Active";
            case DONE -> "Done";
        };
    }
}
