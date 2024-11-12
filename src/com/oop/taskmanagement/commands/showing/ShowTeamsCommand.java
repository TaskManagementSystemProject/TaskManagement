package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTeamsCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    private static final String NO_TEAMS_MESSAGE = "There are no teams yet.";
    private final TaskManagementRepository taskManagementRepository;

    public ShowTeamsCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String toReturnMessage =  taskManagementRepository.getTeams()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + System.lineSeparator()));

        return toReturnMessage.isEmpty() ? NO_TEAMS_MESSAGE : toReturnMessage;

    }
}
