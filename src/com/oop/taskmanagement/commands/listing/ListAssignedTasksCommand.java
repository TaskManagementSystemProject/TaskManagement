package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;
import jdk.jshell.Snippet;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListAssignedTasksCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE = 2;
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE = 3;
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 1;

    private final TaskManagementRepository taskManagementRepository;

    public ListAssignedTasksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);


        return switch (listingType) {
            case FILTER -> {
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield filterTasks(parameters, filterType);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                yield sortTasks();
            }
        };

    }

    private String filterTasks(List<String> parameters, FilterType filterType) {

        return switch (filterType) {
            case STATUS -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE);
                StatusType statusType = ParsingHelpers.tryParseEnum(parameters.get(2), StatusType.class);
                yield filterStatus(statusType);
            }
            case ASSIGNEE -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE);
                yield filterAssignee(parameters.get(2));
            }
            case STATUSANDASSIGNEE -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE);
                StatusType statusType = ParsingHelpers.tryParseEnum(parameters.get(2), StatusType.class);
                yield filterStatusAndAssignee(statusType, parameters.get(3));
            }
        };
    }

    private String filterStatus(StatusType statusType) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .flatMap(member -> member.getTasks().stream())
                .filter(task -> task.getStatus() == statusType)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    private String filterAssignee(String assigneeName) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .filter(member -> member.getName().equalsIgnoreCase(assigneeName))
                .flatMap(member -> member.getTasks().stream())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }


    private String filterStatusAndAssignee(StatusType statusType, String assigneeName) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .filter(member -> member.getName().equalsIgnoreCase(assigneeName))
                .flatMap(member -> member.getTasks().stream())
                .filter(task -> task.getStatus() == statusType)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    private String sortTasks() {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .flatMap(member -> member.getTasks().stream())
                .sorted(Comparator.comparing(TaskBase::getTitle))// sorts by title
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
