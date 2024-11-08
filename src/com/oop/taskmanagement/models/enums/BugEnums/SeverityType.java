package com.oop.taskmanagement.models.enums.BugEnums;

public enum SeverityType {

    CRITICAL,
    MAJOR,
    MINOR;

    @Override
    public String toString() {
        switch (this) {
            case CRITICAL:
                return "Critical";
            case MAJOR:
                return "Major";
            case MINOR:
                return "Minor";
            default:
                return "";
        }
    }
}
