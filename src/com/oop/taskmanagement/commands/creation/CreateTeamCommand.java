package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateTeamCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String CREATE_TEAM_SUCCESS_MESSAGE = "Team %s created successfully";

    private final TaskManagementRepository taskManagementRepository;

    public CreateTeamCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.getFirst();
        taskManagementRepository.createTeam(teamName);

        return String.format(CREATE_TEAM_SUCCESS_MESSAGE, teamName);
    }
}
