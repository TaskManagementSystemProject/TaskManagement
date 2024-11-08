package com.oop.taskmanagement.models.enums;

public enum SeverityType {

    CRITICAL,
    MAJOR,
    MINOR;

    @Override
    public String toString() {
        return switch (this) {
            case CRITICAL -> "Critical";
            case MAJOR -> "Major";
            case MINOR -> "Minor";
        };
    }
}
