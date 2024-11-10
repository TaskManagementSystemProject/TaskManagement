package com.oop.taskmanagement.commands.listing.utility;

import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.SortType;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.lang.reflect.Executable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilteringAndSortingHelperMethods {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE = 2;
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE = 3;

    public static String filterTasks(TaskManagementRepository taskManagementRepository, List<String> parameters, FilterType filterType) {

        return switch (filterType) {
            case STATUS -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE);
                StatusType statusType = ParsingHelpers.tryParseEnum(parameters.get(2), StatusType.class);
                yield filterStatus(taskManagementRepository,statusType);
            }
            case ASSIGNEE -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE);
                yield filterAssignee(taskManagementRepository, parameters.get(2));
            }
            case STATUSANDASSIGNEE -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE);
                StatusType statusType = ParsingHelpers.tryParseEnum(parameters.get(2), StatusType.class);
                yield filterStatusAndAssignee(taskManagementRepository, statusType, parameters.get(3));
            }
        };
    }

    private static String filterStatus(TaskManagementRepository taskManagementRepository, StatusType statusType) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .flatMap(member -> member.getTasks().stream())
                .filter(task -> task.getStatus() == statusType)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    private static String filterAssignee(TaskManagementRepository taskManagementRepository, String assigneeName) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .filter(member -> member.getName().equalsIgnoreCase(assigneeName))
                .flatMap(member -> member.getTasks().stream())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }


    private static String filterStatusAndAssignee(TaskManagementRepository taskManagementRepository, StatusType statusType, String assigneeName) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .filter(member -> member.getName().equalsIgnoreCase(assigneeName))
                .flatMap(member -> member.getTasks().stream())
                .filter(task -> task.getStatus() == statusType)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    public static String sortTasks(TaskManagementRepository taskManagementRepository) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .flatMap(member -> member.getTasks().stream())
                .sorted(Comparator.comparing(TaskBase::getTitle))// sorts by title
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
