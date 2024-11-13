package com.oop.taskmanagement.models.enums;

public enum PriorityType {
    LOW,
    MEDIUM,
    HIGH;

    @Override
    public String toString() {
        return switch (this) {
            case HIGH -> "High";
            case MEDIUM -> "Medium";
            case LOW -> "Low";
        };
    }
}
