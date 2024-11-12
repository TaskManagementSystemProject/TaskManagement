package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTeamBoardsCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String NO_BOARDS_MESSAGE_IN_TEAM = "There are no boards in team %s yet.";
    private static final String TEAM_BOARDS_PREFIX_MESSAGE = "BOARDS in team %s:%n%s";
    private final TaskManagementRepository taskManagementRepository;

    public ShowTeamBoardsCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String teamName = parameters.get(0);
        Team team = taskManagementRepository.findTeamByName(teamName);

        String toReturnMessage = team.getBoards()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return toReturnMessage.isEmpty() ?
                String.format(NO_BOARDS_MESSAGE_IN_TEAM, teamName) :
                String.format(TEAM_BOARDS_PREFIX_MESSAGE, teamName, toReturnMessage);
    }
}
