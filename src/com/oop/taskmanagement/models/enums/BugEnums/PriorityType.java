package com.oop.taskmanagement.models.enums.BugEnums;

public enum PriorityType {
    HIGH,
    MEDIUM,
    LOW;

    @Override
    public String toString() {
        switch (this) {
            case HIGH:
                return "High";
            case MEDIUM:
                return "Medium";
            case LOW:
                return "Low";
            default:
                return "";
        }
    }
}
