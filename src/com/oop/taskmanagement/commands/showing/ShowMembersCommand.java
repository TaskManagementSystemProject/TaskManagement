package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowMembersCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    private static final String NO_MEMBERS_MESSAGE = "There are no members yet.";
    private static final String MEMBERS_PREFIX_MESSAGE = "MEMBERS:%n%s";
    private final TaskManagementRepository taskManagementRepository;

    public ShowMembersCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String toReturnMessage = taskManagementRepository.getMembers()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return toReturnMessage.isEmpty() ? NO_MEMBERS_MESSAGE : String.format(MEMBERS_PREFIX_MESSAGE, toReturnMessage);
    }
}
