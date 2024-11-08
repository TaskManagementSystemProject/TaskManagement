package com.oop.taskmanagement.utils;

import static java.lang.String.format;

public class TaskValidation {


    public static final int TITLE_LENGTH_MAX = 100;
    public static final int TITLE_LENGTH_MIN = 10;
    private static final String TITLE_LEN_ERR = format(
            "Title must be between %d and %d characters long!", TITLE_LENGTH_MIN, TITLE_LENGTH_MAX);

    public static final int DESCRIPTION_LENGTH_MAX = 500;
    public static final int DESCRIPTION_LENGTH_MIN = 10;
    private static final String DESCRIPTION_LEN_ERR = format(
            "Description must be between %d and %d characters long!", DESCRIPTION_LENGTH_MIN, DESCRIPTION_LENGTH_MAX);



    public static void validateTitle(String title) {
        ValidationHelpers.validateIntRange(
                title.length(), TITLE_LENGTH_MIN, TITLE_LENGTH_MAX, TITLE_LEN_ERR);
    }

    public static void validateDescription(String title) {
        ValidationHelpers.validateIntRange(
                title.length(), DESCRIPTION_LENGTH_MIN, DESCRIPTION_LENGTH_MAX, DESCRIPTION_LEN_ERR);
    }
}
