package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods;
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

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 1;

    private final TaskManagementRepository taskManagementRepository;

    public ListAssignedTasksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if(parameters.isEmpty()){
            return getAllAssignedTasks();
        }

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);
        return switch (listingType) {
            case FILTER -> {
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield FilteringAndSortingHelperMethods.filterTasks(taskManagementRepository,parameters, filterType);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                yield FilteringAndSortingHelperMethods.sortTasks(taskManagementRepository);
            }
        };
    }

    private String getAllAssignedTasks(){
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .flatMap(member -> member.getTasks().stream())
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

}
