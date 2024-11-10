package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class CreateBugInBoardCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;

    private static final String CREATE_BUG_SUCCESS_MESSAGE = "Bug with ID %d created successfully in board %s in team %s";
    private static final String LOG_ACTIVITY_IN_BOARD_MESSAGE = "Bug with ID %d was added";
    private final TaskManagementRepository taskManagementRepository;

    public CreateBugInBoardCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String title = parameters.get(0);
        String description = parameters.get(1);
        List<String> stepsToReproduce = convertStringToList(parameters.get(2));
        PriorityType priorityType = ParsingHelpers.tryParseEnum(parameters.get(3), PriorityType.class);
        SeverityType severityType = ParsingHelpers.tryParseEnum(parameters.get(4), SeverityType.class);
        String teamName = parameters.get(5);
        String boardName = parameters.get(6);
        Team team = taskManagementRepository.findTeamByName(teamName);
        Board board = taskManagementRepository.findBoardByTeamName(teamName, boardName);
        Bug newBug = taskManagementRepository.createBugInBoard(title, description, stepsToReproduce, priorityType, severityType, team, board);

        board.logActivity(String.format(LOG_ACTIVITY_IN_BOARD_MESSAGE, newBug.getId()));
        return String.format(CREATE_BUG_SUCCESS_MESSAGE, newBug.getId(), boardName, teamName);
    }

    private List<String> convertStringToList(String stepsToReproduce) {
        List<String> result = new ArrayList<>();
        for (String element : stepsToReproduce.split(";")) {
            result.add(element.trim());
        }
        return result;
    }
}
