package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;
import com.oop.taskmanagement.utils.enums.TaskTypes;

import java.util.Comparator;
import java.util.List;

import static com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods.EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE;
import static com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods.EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE;

public class ListAssignedTasksCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 1;

    private static final String NO_RESULTS_FOUND_MESSAGE = "There are NO assigned tasks matching the given parameters.";
    private static final String ASSIGNED_TASK_PREFIX_MESSAGE = "LIST MATCHING ASSIGNED TASKS:%n%s";
    private final TaskManagementRepository taskManagementRepository;

    public ListAssignedTasksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        String toReturnMessage;
        if (parameters.isEmpty()) {
            toReturnMessage = FilteringAndSortingHelperMethods.getTasksGeneric(taskManagementRepository.getAllTasks(), true);
            return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : String.format(ASSIGNED_TASK_PREFIX_MESSAGE, toReturnMessage);
        }

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);
        toReturnMessage = listingResultMessage(parameters, listingType);
        return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : String.format(ASSIGNED_TASK_PREFIX_MESSAGE, toReturnMessage);
    }

    private String listingResultMessage(List<String> parameters,ListingType listingType){
        return switch (listingType) {
            case FILTER -> {
                ValidationHelpers.validateArgumentsCountMultiple(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE);
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield FilteringAndSortingHelperMethods.filterTasks(taskManagementRepository.getAllTasks(), parameters, filterType, true, TaskTypes.ALL, false);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                yield FilteringAndSortingHelperMethods.sortTasksGeneric(taskManagementRepository.getAllTasks(), Comparator.comparing(TaskBase::getTitle) ,true);
            }
            case FILTERSORT -> {
                ValidationHelpers.validateArgumentsCountMultiple(parameters,
                        EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE + EXPECTED_NUMBER_OF_ARGUMENTS_SORTING ,
                        EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE + EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield FilteringAndSortingHelperMethods.filterTasks(taskManagementRepository.getAllTasks(), parameters, filterType, true, TaskTypes.ALL, true)
                        .concat(FilteringAndSortingHelperMethods.sortTasksGeneric(taskManagementRepository.getAllTasks(), Comparator.comparing(TaskBase::getTitle) ,true));
            }
        };
    }
}
