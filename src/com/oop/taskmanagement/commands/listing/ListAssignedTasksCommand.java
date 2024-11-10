package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class ListAssignedTasksCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 1;

    private final TaskManagementRepository taskManagementRepository;

    public ListAssignedTasksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.isEmpty()) {
            return FilteringAndSortingHelperMethods.getTasksGeneric(taskManagementRepository.getAllTasks(), true);
        }

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);
        return switch (listingType) {
            case FILTER -> {
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield FilteringAndSortingHelperMethods.filterTasks(taskManagementRepository.getAllTasks(), parameters, filterType, true);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                yield FilteringAndSortingHelperMethods.sortTasksGeneric(taskManagementRepository.getAllTasks(), Comparator.comparing(TaskBase::getTitle) ,true);
            }
        };
    }

}
