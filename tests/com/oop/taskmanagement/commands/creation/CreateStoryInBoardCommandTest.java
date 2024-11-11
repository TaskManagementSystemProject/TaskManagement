package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;

public class CreateStoryInBoardCommandTest {
    private static final int ARGUMENT_COUNT = 6;
    private TaskManagementRepository repository;
    private Command createStoryInBoardCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        createStoryInBoardCommand = new CreateStoryInBoardCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createStoryInBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidPriorityType(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createStoryInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        "InvalidPriority",
                        VALID_SIZE_TYPE_STORY,
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSizeType(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createStoryInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_PRIORITY_TYPE_STORY,
                        "InvalidSize",
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNotFound(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createStoryInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_PRIORITY_TYPE_STORY,
                        VALID_SIZE_TYPE_STORY,
                        "NOT FOUND",
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardNotFound(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createStoryInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_PRIORITY_TYPE_STORY,
                        VALID_SIZE_TYPE_STORY,
                        VALID_TEAM_NAME,
                        "NOT FOUND")));
    }

    @Test
    public void execute_Should_LogInBoard_When_ValidArguments() {
        // Arrange
        String expectedLog = String.format(CREATE_BOARD_SUCCESS_MESSAGE, 4);
        Board currentBoard = repository.findBoardByTeamName(VALID_BOARD_NAME,VALID_TEAM_NAME);

        // Act
        createStoryInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_PRIORITY_TYPE_STORY,
                VALID_SIZE_TYPE_STORY,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedLog, currentBoard.getActivityHistory().get(currentBoard.getActivityHistory().size() - 1));
    }

    @Test
    public void execute_Should_CreateStoryInBoard_When_ValidArguments() {
        // Arrange
        Board currentBoard = repository.findBoardByTeamName(VALID_BOARD_NAME,VALID_TEAM_NAME);
        int expectedLengthOfStories = currentBoard.getTasks().size() + 1;

        // Act
        createStoryInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_PRIORITY_TYPE_STORY,
                VALID_SIZE_TYPE_STORY,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedLengthOfStories, currentBoard.getTasks().size());
    }

    @Test
    public void execute_Should_ReturnProperMessage_When_ValidArguments() {
        // Arrange
        Board currentBoard = repository.findBoardByTeamName(VALID_BOARD_NAME,VALID_TEAM_NAME);
        String expectedOutput = String.format(CREATE_STORY_SUCCESS_MESSAGE,4,VALID_BOARD_NAME,VALID_TEAM_NAME);

        // Act
        String actualOutput = createStoryInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_PRIORITY_TYPE_STORY,
                VALID_SIZE_TYPE_STORY,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
