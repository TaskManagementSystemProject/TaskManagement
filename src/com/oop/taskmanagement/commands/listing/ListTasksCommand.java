package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.commands.enums.SortType;
import com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;
import com.oop.taskmanagement.utils.enums.TaskTypes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListTasksCommand implements Command {

    private static final String SORT_FIELD_ERROR = "Task cannot be sorted by field %s";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 1;
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING = 2;

    private final TaskManagementRepository taskManagementRepository;

    public ListTasksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.isEmpty()) {
            return FilteringAndSortingHelperMethods.getTasksGeneric(
                    taskManagementRepository.getAllTasks(), false);
        }

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);

        return switch (listingType) {
            case FILTER -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING);
               String titlesToList = parameters.get(1);
                yield filterTaskByTitle(titlesToList);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                yield sortTasksByTitle();
            }
        };

    }

    private String filterTaskByTitle(String titlesToList) {
        return taskManagementRepository.getAllTasks()
                .stream()
                .filter(task -> task.getTitle().equals(titlesToList))
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String sortTasksByTitle() {
        return taskManagementRepository.getAllTasks()
                .stream()
                .sorted(Comparator.comparing(TaskBase::getTitle))// sorts by title
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
