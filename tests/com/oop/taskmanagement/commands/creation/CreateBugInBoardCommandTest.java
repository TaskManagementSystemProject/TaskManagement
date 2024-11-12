package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
/*
import static com.oop.taskmanagement.utils.Constants.*;
import static com.oop.taskmanagement.utils.Constants.VALID_BOARD_NAME;

public class CreateBugInBoardCommandTest {

    private static final int ARGUMENT_COUNT = 7;

    private static final String VALID_SEVERITY = SeverityType.MINOR.toString();
    private TaskManagementRepository repository;
    private Command createBugInBoardCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        createBugInBoardCommand = new CreateBugInBoardCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferDown() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferUp() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_CreateBugInBoard_When_ArgumentsAreValid() {
        // Arrange, Act, Assert
        Assertions.assertDoesNotThrow(() -> createBugInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_STEPS_TO_REPRODUCE,
                        VALID_PRIORITY,
                        VALID_SEVERITY,
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidTitle() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand
                .execute(List.of("invalid",
                        VALID_DESCRIPTION,
                        VALID_STEPS_TO_REPRODUCE,
                        VALID_PRIORITY,
                        VALID_SEVERITY,
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidDescription() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand
                .execute(List.of(VALID_TITLE,
                        "invalid",
                        VALID_STEPS_TO_REPRODUCE,
                        VALID_PRIORITY,
                        VALID_SEVERITY,
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidPriorityType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_STEPS_TO_REPRODUCE,
                        "Invalid",
                        VALID_SEVERITY,
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSeverityType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_STEPS_TO_REPRODUCE,
                        VALID_PRIORITY,
                        "Invalid",
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_STEPS_TO_REPRODUCE,
                        VALID_PRIORITY,
                        VALID_SEVERITY,
                        "invalid",
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_STEPS_TO_REPRODUCE,
                        VALID_PRIORITY,
                        VALID_SEVERITY,
                        VALID_TEAM_NAME,
                        "Invalid")));
    }

    @Test
    public void execute_Should_LogInBoard_When_ValidArguments() {
        // Arrange
        String expectedLog = String.format(CREATE_BUG_SUCCESS_MESSAGE_IN_BOARD, 4);
        Board currentBoard = repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME);

        // Act
        createBugInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_STEPS_TO_REPRODUCE,
                VALID_PRIORITY,
                VALID_SEVERITY,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedLog, currentBoard.getActivityHistory().get(currentBoard.getActivityHistory().size() - 1));
    }

    @Test
    public void execute_Should_CreateStoryInBoard_When_ValidArguments() {
        // Arrange
        Board currentBoard = repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME);
        int expectedLengthOfBugs = currentBoard.getTasks().size() + 1;

        // Act
        createBugInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_STEPS_TO_REPRODUCE,
                VALID_PRIORITY,
                VALID_SEVERITY,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedLengthOfBugs, currentBoard.getTasks().size());
    }

    @Test
    public void execute_Should_ReturnProperMessage_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format(CREATE_BUG_SUCCESS_MESSAGE, 4, VALID_BOARD_NAME, VALID_TEAM_NAME);

        // Act
        String actualOutput = createBugInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_STEPS_TO_REPRODUCE,
                VALID_PRIORITY,
                VALID_SEVERITY,
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
*/