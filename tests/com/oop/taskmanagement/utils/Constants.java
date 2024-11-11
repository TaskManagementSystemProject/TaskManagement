package com.oop.taskmanagement.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Constants {

    // Tasks
    public static int currentId = 1;
    public static final String VALID_TITLE = "10symb tit";
    public static final String VALID_DESCRIPTION = "10symb des";
    public static final List<String> VALID_LIST_TO_REPRODUCE = new ArrayList<>(Arrays.asList("Step1","Step2"));
    public static final int VALID_RATING = 5;
    public static final String EXPECTED_FEEDBACK_TO_STRING_FORMAT = "Task with id: %d%nTitle: %s%nStatus: %s%nRating: %d%nAssigned to: %s";
    public static final String EXPECTED_STORY_TO_STRING_FORMAT = "Task with id: %d%nTitle: %s%nStatus: %s%nPriority: %s%nSize: %s%nAssigned to: %s";
    // Team
    public static final String VALID_TEAM_NAME = "Otbor";
    public static final String VALID_TEAM_NAME_TWO = "Otbor1";
    public static final String INVALID_TEAM_SHORT_NAME = "Geor";
    public static final String INVALID_TEAM_LONG_NAME = "GeorgiYordanOtbo";
    // Board
    public static final String VALID_BOARD_NAME = "White";
    public static final String INVALID_BOARD_SHORT_NAME = "Bob";
    public static final String INVALID_BOARD_LONG_NAME = "DaveTheMagicalCheeseWizard";
    // Member
    public static final String VALID_MEMBER_NAME_ONE = "Gosho";
    public static final String VALID_MEMBER_NAME_TWO = "Pesho";
    public static final String INVALID_MEMBER_SHORT_NAME = "Bob";
    public static final String INVALID_MEMBER_LONG_NAME = "DaveTheMagicalCheeseWizard";
    // Comments
    public static final String VALID_COMMENT_AUTHOR = "JohnSmith"; // 5 - 15
    public static final String VALID_COMMENT_MESSAGE = "Messages can be from 5 to 500 characters";
    public static final String INVALID_COMMENT_SHORT_MESSAGE = "XXXX";
    public static final String INVALID_COMMENT_LONG_MESSAGE = "X".repeat(501);
    public static final String INVALID_AUTHOR_SHORT_NAME = "Bob";  // 5 - 15
    public static final String INVALID_AUTHOR_LONG_NAME = "DaveTheMagicalCheeseWizard";

    // Commands
    public final static String TASK_UNASSIGNED_SUCCESSFULLY = "Task with ID %d unassigned from member %s successfully.";
    public final static String FEEDBACK_STATUS_CHANGED_SUCCESSFULLY = "Feedback with ID %d status changed to %s successfully.";
    public final static String STORY_ATTRIBUTE_CHANGED_SUCCESSFULLY = "Story with ID %d %s changed to %s successfully.";
    public static final String CREATE_STORY_SUCCESS_MESSAGE = "Story with ID %d created successfully in board %s in team %s.";
    public static final String CREATE_MEMBER_SUCCESS_MESSAGE = "Member %s created successfully.";
    public static final String CREATE_TEAM_SUCCESS_MESSAGE = "Team %s created successfully.";
    public static final String CREATE_BOARD_SUCCESS_MESSAGE = "Story with ID %d was added.";
    public static final String VALID_PRIORITY_TYPE_STORY = "Low";
    public static final String VALID_SIZE_TYPE_STORY = "Small";
}
