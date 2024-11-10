package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.commands.enums.SortType;
import com.oop.taskmanagement.commands.listing.utility.FilteringAndSortingHelperMethods;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListBugsCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 2;

    private final TaskManagementRepository taskManagementRepository;

    public ListBugsCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);

        return switch (listingType) {
            case FILTER -> {
                FilterType filterType = ParsingHelpers.tryParseEnum(parameters.get(1), FilterType.class);
                yield FilteringAndSortingHelperMethods.filterTasks(taskManagementRepository,parameters,filterType);
            }
            case SORT -> {
                ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS_SORTING);
                SortType sortType = ParsingHelpers.tryParseEnum(parameters.get(1), SortType.class);
                yield sortTasks(getComparator(sortType));
            }
        };
    }

    private Comparator<Bug> getComparator(SortType sortType) {
        return switch (sortType) {
            case TITLE -> Comparator.comparing(Bug::getTitle);
            case PRIORITY -> Comparator.comparing(Bug::getPriority);
            case SEVERITY -> Comparator.comparing(Bug::getSeverity);
            default ->
                    throw new InvalidUserInputException(String.format("Bug cannot be sorted by field %s ", sortType));
        };
    }

    private String sortTasks(Comparator<Bug> bugComparator) {
        return taskManagementRepository.getBugs()
                .stream()
                .sorted(bugComparator)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
