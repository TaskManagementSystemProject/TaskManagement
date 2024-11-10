package com.oop.taskmanagement.commands.enums;

public enum SortType {
    TITLE,
    PRIORITY,
    SEVERITY,
    SIZE,
    RATING;

    @Override
    public String toString() {
        switch (this) {
            case TITLE:
                return "Title";
            case PRIORITY:
                return "Priority";
            case SEVERITY:
                return "Severity";
            case SIZE:
                return "Size";
            case RATING:
                return "Rating";
            default:
                return "";
        }
    }
}
