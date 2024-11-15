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
import java.util.stream.Stream;

public class ListTasksCommand implements Command {

    private static final String NO_RESULTS_FOUND_MESSAGE = "There are NO tasks matching the given parameters.";
    private static final String ALL_TASK_PREFIX_MESSAGE = "LIST MATCHING TASKS:%n%s";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 1;
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING = 2;

    private final TaskManagementRepository taskManagementRepository;

    public ListTasksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        String toReturnMessage;
        if (parameters.isEmpty()) {
            toReturnMessage =  FilteringAndSortingHelperMethods.getTasksGeneric(
                    taskManagementRepository.getAllTasks(), false);
            return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : String.format(ALL_TASK_PREFIX_MESSAGE, toReturnMessage);
        }

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);

        toReturnMessage = listingResultMessage(parameters, listingType);
        return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : String.format(ALL_TASK_PREFIX_MESSAGE, toReturnMessage);


    }

    private String filterTaskByTitle(String titlesToList, List<TaskBase> tasks) {
        return tasks.stream()
                .filter(task -> task.getTitle().equals(titlesToList))
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    private Stream<TaskBase> sortTasksByTitle() {
        return taskManagementRepository.getAllTasks()
                .stream()
                .sorted(Comparator.comparing(TaskBase::getTitle));// sorts by title;
    }

    private String transformStreamToString(Stream<TaskBase> str){
        return str
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));
    }

    private String listingResultMessage(List<String> parameters,ListingType listingType){
        return switch (listingType) {
            case FILTER -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING);
                String titlesToList = parameters.get(1);
                yield filterTaskByTitle(titlesToList, taskManagementRepository.getAllTasks());
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                yield transformStreamToString(sortTasksByTitle());
            }
            case FILTERSORT ->  {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING);
                String titlesToList = parameters.get(1);
                yield filterTaskByTitle(titlesToList, sortTasksByTitle().toList());
            }
        };
    }
}
