package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.commands.enums.SortType;
import com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;
import com.oop.taskmanagement.utils.enums.TaskTypes;

import java.util.Comparator;
import java.util.List;

import static com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods.*;

public class ListStoriesCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 2;
    private static final String NO_RESULTS_FOUND_MESSAGE = "There are NO stories matching the given parameters.";
    private static final String STORIES_PREFIX_MESSAGE = "LIST MATCHING STORIES:%n%s";
    private final TaskManagementRepository taskManagementRepository;

    public ListStoriesCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        String toReturnMessage;
        if (parameters.isEmpty()) {
            toReturnMessage = FilteringAndSortingHelperMethods.getTasksGeneric(taskManagementRepository.getStories(), false);
            return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : String.format(STORIES_PREFIX_MESSAGE, toReturnMessage);
        }


        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);

        toReturnMessage = listingResultMessage(parameters, listingType);
        return toReturnMessage.isEmpty() ? NO_RESULTS_FOUND_MESSAGE : String.format(STORIES_PREFIX_MESSAGE, toReturnMessage);
    }

    private Comparator<Story> getComparator(SortType sortType) {
        return switch (sortType) {
            case TITLE -> Comparator.comparing(Story::getTitle);
            case PRIORITY -> Comparator.comparing(Story::getPriority);
            case SIZE -> Comparator.comparing(Story::getSize);
            default ->
                    throw new InvalidUserInputException(String.format("Feedback cannot be sorted by field %s ", sortType));
        };
    }

    private String listingResultMessage(List<String> parameters,ListingType listingType) {
        return switch (listingType) {
            case FILTER -> {
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield FilteringAndSortingHelperMethods.filterTasks(
                        taskManagementRepository.getStories(), parameters, filterType,false, TaskTypes.STORY , false);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                SortType sortType = ParsingHelpers.tryParseEnum(parameters.get(1), SortType.class);
                yield sortTasksGenericString(taskManagementRepository.getStories(),getComparator(sortType),false);
            }
            case FILTERSORT -> {
                ValidationHelpers.validateArgumentsCountMultiple(parameters,
                        EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_SINGLE + 1,
                        EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING_MULTIPLE + 1);
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                SortType sortType = ParsingHelpers.tryParseEnum(parameters.get(parameters.size() - 1), SortType.class); // gets the last parameter -> must be sort parameter
                List<Story> sortedStories = FilteringAndSortingHelperMethods.sortTasksGeneric(taskManagementRepository.getStories(), getComparator(sortType), false).toList();
                yield FilteringAndSortingHelperMethods.filterTasks(sortedStories, parameters, filterType, false, TaskTypes.ALL, true);
            }
        };
    }
}
