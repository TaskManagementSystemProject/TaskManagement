package com.oop.taskmanagement.commands.listing.utility;

import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;
import com.oop.taskmanagement.utils.enums.TaskTypes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilteringAndSortingHelperMethods {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE = 3;
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE = 4;

    public static <T extends TaskBase> String filterTasks(List<T> tasks, List<String> parameters, FilterType filterType, boolean mustBeAssigned, TaskTypes taskType, boolean filterSort) {

        return switch (filterType) {
            case STATUS -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE + (filterSort ? 1 : 0));
                StatusType statusType = ParsingHelpers.tryParseEnum(parameters.get(2), StatusType.class);
                ValidationHelpers.validateStatusType(statusType, taskType);
                yield filterStatusGeneric(tasks, statusType, mustBeAssigned);
            }
            case ASSIGNEE -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE + (filterSort ? 1 : 0));
                yield filterAssigneeGeneric(tasks, parameters.get(2));
            }
            case STATUSANDASSIGNEE -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE + (filterSort ? 1 : 0));
                StatusType statusType = ParsingHelpers.tryParseEnum(parameters.get(2), StatusType.class);
                ValidationHelpers.validateStatusType(statusType, taskType);
                yield filterStatusAndAssigneeGeneric(tasks, statusType, parameters.get(3));
            }
        };
    }

    private static <T extends TaskBase> String filterStatusGeneric(List<T> tasks, StatusType statusType, boolean mustBeAssigned) {
        return tasks.stream()
                .filter(task -> task.getStatus() == statusType && (!mustBeAssigned || task.getAssigneeName() != null))
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    private static <T extends TaskBase> String filterAssigneeGeneric(List<T> tasks, String assigneeName) {
        return tasks.stream()
                .filter(task -> assigneeName.equals(task.getAssigneeName()))
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    private static <T extends TaskBase> String filterStatusAndAssigneeGeneric(List<T> tasks, StatusType statusType, String assigneeName) {
        return tasks.stream()
                .filter(task ->task.getStatus() == statusType && assigneeName.equals(task.getAssigneeName())) // single filter -> faster
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    public static <T extends TaskBase> String sortTasksGeneric(List<T> tasks, Comparator<T> comparator, boolean mustBeAssigned) {
        return tasks.stream()
                .filter(task -> !mustBeAssigned || task.getAssigneeName() != null) // makes the check only if it has to be assigned
                .sorted(comparator)// sorts by title
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    public static <T extends TaskBase> String getTasksGeneric(List<T> tasks, boolean mustBeAssigned) {
        return tasks.stream()
                .filter(task -> !mustBeAssigned || task.getAssigneeName() != null)
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

}
