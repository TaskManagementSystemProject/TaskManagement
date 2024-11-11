package com.oop.taskmanagement.models.enums;

public enum PriorityType {
    LOW,
    MEDIUM,
    HIGH;

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
