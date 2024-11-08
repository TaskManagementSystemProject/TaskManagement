package com.oop.taskmanagement.models.enums;

public enum SizeType {
    LARGE,
    MEDIUM,
    SMALL;

    @Override
    public String toString() {
        return switch (this) {
            case LARGE -> "Large";
            case MEDIUM -> "Medium";
            case SMALL -> "Small";
        };
    }
}
