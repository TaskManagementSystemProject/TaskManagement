package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowBoardActivityCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private final TaskManagementRepository taskManagementRepository;

    public ShowBoardActivityCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);

        Board board = taskManagementRepository.findBoardByTeamName(boardName, teamName);

        return String.join(System.lineSeparator(), board.getActivityHistory());
    }
}
