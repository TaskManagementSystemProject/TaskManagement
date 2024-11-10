package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.FilterType;
import com.oop.taskmanagement.commands.enums.ListingType;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListTasksCommand implements Command {

    // if we get just the command list all tasks arguments -> 0
    /*public static final int EXPECTED_NUMBER_OF_ARGUMENTS_NO_SORTING_OR_FILTERING = 0;*/
    public static final int MIN_EXPECTED_NUMBER_OF_ARGUMENTS_SORTING = 1;
    public static final int MAX_EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING = 2;
    private static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d; received: %d.";

    private final TaskManagementRepository taskManagementRepository;

    public ListTasksCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.isEmpty()){
            return getAllTasks();
        }
        validateParametersCount(parameters);

        ListingType listingType = ParsingHelpers.tryParseEnum(parameters.get(0), ListingType.class);

        return switch (listingType) {
            case FILTER -> {
                String titlesToList = parameters.get(1);
                yield filterTaskByTitle(titlesToList);
            }
            case SORT -> sortTasksByTitle();

        };

    }


    private String filterTaskByTitle(String titlesToList) {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .flatMap(member -> member.getTasks().stream())
                .filter(task -> task.getTitle().equals(titlesToList))
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    private String sortTasksByTitle() {
        return taskManagementRepository.getTeams()
                .stream()
                .flatMap(team -> team.getMembers().stream())
                .flatMap(member -> member.getTasks().stream())
                .sorted(Comparator.comparing(TaskBase::getTitle))// sorts by title
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    private String getAllTasks() {
        return taskManagementRepository.getAllTasks().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    private static void validateParametersCount(List<String> parameters) {
        ValidationHelpers.validateIntRange(
                parameters.size(),
                MIN_EXPECTED_NUMBER_OF_ARGUMENTS_SORTING,
                MAX_EXPECTED_NUMBER_OF_ARGUMENTS_FILTERING,
                INVALID_NUMBER_OF_ARGUMENTS);
    }
}
