package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateBoardInTeamCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private static final String CREATE_BOARD_SUCCESS_MESSAGE = "Board %s created successfully in team %s.";
    private static final String LOG_ACTIVITY_IN_BOARD_MESSAGE = "Board %s added to team %s.";

    private final TaskManagementRepository taskManagementRepository;

    public CreateBoardInTeamCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);
        Team teamToAddBoardIn = taskManagementRepository.findTeamByName(teamName);
        Board newBoard = taskManagementRepository.createBoardInTeam(boardName, teamToAddBoardIn);
        newBoard.logActivity(String.format(LOG_ACTIVITY_IN_BOARD_MESSAGE, boardName, teamName));

        return String.format(CREATE_BOARD_SUCCESS_MESSAGE, boardName, teamName);
    }
}
