package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackInBoardCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private static final String CREATE_FEEDBACK_SUCCESS_MESSAGE = "Feedback with ID %d created successfully in board %s in team %s";
    private static final String RATING_PARSING_ERROR = "Feedback rating must be an integer.";
    private final TaskManagementRepository taskManagementRepository;

    public CreateFeedbackInBoardCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String title = parameters.get(0);
        String description = parameters.get(1);
        int rating = ParsingHelpers.tryParseInt(parameters.get(2), RATING_PARSING_ERROR);
        String teamName = parameters.get(3);
        String boardName = parameters.get(4);
        Team team = taskManagementRepository.findTeamByName(teamName);
        Board board = taskManagementRepository.findBoardByTeamName(teamName, boardName);
        Feedback newFeedback = taskManagementRepository.createFeedbackInBoard(title, description, rating, team, board);

        board.logActivity(String.format("Feedback with ID %d was added", newFeedback.getId()));
        return String.format(CREATE_FEEDBACK_SUCCESS_MESSAGE, newFeedback.getId(), boardName, teamName);
    }
}
