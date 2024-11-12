package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateStoryInBoardCommand implements Command {

    private static final String LOG_ACTIVITY_IN_BOARD_MESSAGE = "Story with ID %d was added.";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 6;
    private static final String CREATE_STORY_SUCCESS_MESSAGE = "Story with ID %d created successfully in board %s in team %s.";
    private final TaskManagementRepository taskManagementRepository;

    public CreateStoryInBoardCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String title = parameters.get(0);
        String description = parameters.get(1);
        PriorityType priorityType = ParsingHelpers.tryParseEnum(parameters.get(2), PriorityType.class);
        SizeType sizeType = ParsingHelpers.tryParseEnum(parameters.get(3), SizeType.class);
        String teamName = parameters.get(4);
        String boardName = parameters.get(5);

        Team team = taskManagementRepository.findTeamByName(teamName);
        Board board = taskManagementRepository.findBoardByTeamName(boardName, teamName);
        Story newStory = taskManagementRepository.createStoryInBoard(title, description, priorityType, sizeType, team, board);

        board.logActivity(String.format(LOG_ACTIVITY_IN_BOARD_MESSAGE, newStory.getId()));
        return String.format(CREATE_STORY_SUCCESS_MESSAGE, newStory.getId(), boardName, teamName);
    }
}
