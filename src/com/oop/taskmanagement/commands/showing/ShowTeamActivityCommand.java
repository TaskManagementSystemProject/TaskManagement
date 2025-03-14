package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowTeamActivityCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String NO_ACTIVITY_MESSAGE = "There is no activity in team %s yet.";
    private static final String TEAM_ACTIVITY_PREFIX_MESSAGE = "ACTIVITY in team %s:%n%s";
    private final TaskManagementRepository taskManagementRepository;

    public ShowTeamActivityCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        Team team = taskManagementRepository.findTeamByName(teamName);

        String toReturnMessage =  Stream.concat(team.getMembers().stream(), team.getBoards().stream())
                .flatMap(teamAsset -> teamAsset.getActivityHistory().stream())
                .collect(Collectors.joining(System.lineSeparator()));

        return toReturnMessage.isEmpty() ?
                String.format(NO_ACTIVITY_MESSAGE, teamName) :
                String.format(TEAM_ACTIVITY_PREFIX_MESSAGE, teamName, toReturnMessage);

    }

}
