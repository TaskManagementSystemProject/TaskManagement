package com.oop.taskmanagement.models.enums.BugEnums;

public enum StatusType {
    ACTIVE,
    DONE;

    @Override
    public String toString() {
        switch (this) {
            case ACTIVE:
                return "Active";
            case DONE:
                return "Done";
            default:
                return "";
        }
    }
}
