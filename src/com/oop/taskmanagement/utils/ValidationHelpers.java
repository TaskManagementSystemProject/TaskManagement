package com.oop.taskmanagement.utils;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.enums.TaskTypes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelpers {

    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";
    private static final String INVALID_STATUS_TYPE_MESSAGE = "%s cannot be filtered by status %s.";
    public static void validateIntRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new InvalidUserInputException(message);
        }
    }

    public static void validateDecimalRange(double value, double min, double max, String message) {
        if (value < min || value > max) {
            throw new InvalidUserInputException(message);
        }
    }

    public static void validateArgumentsCount(List<String> list, int expectedNumberOfParameters) {
        if (list.size() != expectedNumberOfParameters) {
            throw new InvalidUserInputException(
                    String.format(INVALID_NUMBER_OF_ARGUMENTS, expectedNumberOfParameters, list.size())
            );
        }
    }

    public static void validatePattern(String value, String pattern, String message) {
        Pattern patternToMatch = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patternToMatch.matcher(value);
        if (!matcher.matches()) {
            throw new InvalidUserInputException(message);
        }
    }
    public static void validateStatusType(StatusType statusType, TaskTypes taskType) {
        switch (taskType) {
            case BUG:
                if (statusType != StatusType.ACTIVE && statusType != StatusType.DONE) {
                    throw new InvalidUserInputException(String.format(INVALID_STATUS_TYPE_MESSAGE,taskType, statusType));
                }
                break;
            case STORY:
                if (statusType != StatusType.NOT_DONE && statusType != StatusType.IN_PROGRESS && statusType != StatusType.DONE) {
                    throw new InvalidUserInputException(String.format(INVALID_STATUS_TYPE_MESSAGE,taskType, statusType));
                }
                break;
            case FEEDBACK:
                if (statusType != StatusType.NEW && statusType != StatusType.UNSCHEDULED && statusType != StatusType.SCHEDULED && statusType != StatusType.DONE) {
                    throw new InvalidUserInputException(String.format(INVALID_STATUS_TYPE_MESSAGE,taskType, statusType));
                }
        }
    }
    public static void validateMemberInProperTeamForAssignment(TaskManagementRepository taskManagementRepository, TaskBase taskBase, Team teamOfMember) {
        for (Team currentTeam : taskManagementRepository.getTeams()) {
            for (Board board : currentTeam.getBoards()) {
                for (TaskBase task : board.getTasks()) {
                    if (task.getId() == taskBase.getId()) {
                        if (!currentTeam.getName().equalsIgnoreCase(teamOfMember.getName())) {
                            throw new InvalidUserInputException(String.format("Cannot assign task with ID %d from team %s to team %s",
                                    taskBase.getId(),
                                    currentTeam.getName(),
                                    teamOfMember.getName()));
                        }
                    }
                }
            }
        }
    }
}
