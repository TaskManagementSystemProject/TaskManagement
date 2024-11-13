package com.oop.taskmanagement.utils;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.functionality.Nameable;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.enums.TaskTypes;

import java.util.List;
import java.util.stream.Stream;

public class ValidationHelpers {

    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";
    private static final String INVALID_NUMBER_OF_ARGUMENTS_MULTIPLE = "Invalid number of arguments. Expected: %d or %d; received: %d.";
    private static final String INVALID_STATUS_TYPE_MESSAGE = "%s cannot be filtered by status %s.";
    private static final String RATING_TO_BIG_MESSAGE = "Rating can't be more than %d!";
    private static final int RATING_MAX_VALUE = 100;

    public static void validateIntRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new InvalidUserInputException(message);
        }
    }

    public static void ValidateRating(int rating) {
        if (rating < 0) {
            throw new InvalidUserInputException("Rating can't be negative!");
        } else if (rating > RATING_MAX_VALUE) {
            throw new InvalidUserInputException(String.format(RATING_TO_BIG_MESSAGE, RATING_MAX_VALUE));
        }
    }

    public static void validateArgumentsCount(List<String> list, int expectedNumberOfParameters) {
        if (list.size() != expectedNumberOfParameters) {
            throw new InvalidUserInputException(
                    String.format(INVALID_NUMBER_OF_ARGUMENTS, expectedNumberOfParameters, list.size())
            );
        }
    }

    public static void validateArgumentsCountMultiple(List<String> list, int expectedNumberOfParametersOne, int expectedNumberOfParametersTwo) {
        if (list.size() != expectedNumberOfParametersOne && list.size() != expectedNumberOfParametersTwo) {
            throw new InvalidUserInputException(
                    String.format(INVALID_NUMBER_OF_ARGUMENTS_MULTIPLE, expectedNumberOfParametersOne, expectedNumberOfParametersTwo, list.size())
            );
        }
    }

    public static void validateNameUniqueness(List<? extends Nameable> firstList, List<? extends Nameable> secondList, String nameToCheck, String message) {
        if (Stream.concat(firstList.stream(), secondList.stream())
                .anyMatch(element -> element.getName().equalsIgnoreCase(nameToCheck))) {
            throw new InvalidUserInputException(message);
        }
    }

    public static void validateStatusType(StatusType statusType, TaskTypes taskType) {
        switch (taskType) {
            case BUG:
                if (statusType != StatusType.ACTIVE && statusType != StatusType.DONE) {
                    throw new InvalidUserInputException(String.format(INVALID_STATUS_TYPE_MESSAGE, taskType, statusType));
                }
                break;
            case STORY:
                if (statusType != StatusType.NOT_DONE && statusType != StatusType.IN_PROGRESS && statusType != StatusType.DONE) {
                    throw new InvalidUserInputException(String.format(INVALID_STATUS_TYPE_MESSAGE, taskType, statusType));
                }
                break;
            case FEEDBACK:
                if (statusType != StatusType.NEW && statusType != StatusType.UNSCHEDULED && statusType != StatusType.SCHEDULED && statusType != StatusType.DONE) {
                    throw new InvalidUserInputException(String.format(INVALID_STATUS_TYPE_MESSAGE, taskType, statusType));
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
