package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.commands.enums.SortType;
import com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;
import com.oop.taskmanagement.utils.enums.TaskTypes;

import java.util.Comparator;
import java.util.List;

public class ListFeedbacksCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 2;
    private static final String NO_RESULTS_FOUND_MESSAGE = "There are NO feedbacks matching the given parameters.";
    private final TaskManagementRepository taskManagementRepository;

    public ListFeedbacksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        String toReturnMessage;
        if (parameters.isEmpty()) {
            toReturnMessage = FilteringAndSortingHelperMethods.getTasksGeneric(
                    taskManagementRepository.getFeedbacks(), false);
            return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : toReturnMessage;
        }

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);

        toReturnMessage = listingResultMessage(parameters, listingType);
        return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : toReturnMessage;
    }

    private Comparator<Feedback> getComparator(SortType sortType) {
        return switch (sortType) {
            case TITLE -> Comparator.comparing(Feedback::getTitle);
            case RATING -> Comparator.comparing(Feedback::getRating);
            default ->
                    throw new InvalidUserInputException(String.format("Feedback cannot be sorted by field %s ", sortType));
        };
    }

    private String listingResultMessage(List<String> parameters,ListingType listingType) {
        return switch (listingType) {
            case FILTER -> {
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield FilteringAndSortingHelperMethods.filterTasks(
                        taskManagementRepository.getFeedbacks(), parameters, filterType, false, TaskTypes.FEEDBACK);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                SortType sortType = ParsingHelpers.tryParseEnum(parameters.get(1), SortType.class);
                yield FilteringAndSortingHelperMethods.sortTasksGeneric(
                        taskManagementRepository.getFeedbacks(), getComparator(sortType), false);
            }
        };
    }
}
