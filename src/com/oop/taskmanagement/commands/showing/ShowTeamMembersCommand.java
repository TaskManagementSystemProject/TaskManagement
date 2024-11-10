package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTeamMembersCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final TaskManagementRepository taskManagementRepository;

    public ShowTeamMembersCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        Team team = taskManagementRepository.findTeamByName(teamName);

        return team.getMembers()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
