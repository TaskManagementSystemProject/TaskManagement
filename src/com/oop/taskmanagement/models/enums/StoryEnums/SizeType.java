package com.oop.taskmanagement.models.enums.StoryEnums;

public enum SizeType {
    LARGE,
    MEDIUM,
    SMALL;

    @Override
    public String toString() {
        switch (this) {
            case LARGE:
                return "Large";
            case MEDIUM:
                return "Medium";
            case SMALL:
                return "Small";
            default:
                return "";
        }
    }
}
