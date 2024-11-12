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

import static com.oop.taskmanagement.utils.Constants.*;
import static com.oop.taskmanagement.utils.Constants.VALID_BOARD_NAME;

public class CreateFeedbackInBoardCommandTest {

    private static final int ARGUMENT_COUNT = 5;

    private static final String VALID_SEVERITY = SeverityType.MINOR.toString();
    private TaskManagementRepository repository;
    private Command createFeedbackInBoardCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        createFeedbackInBoardCommand = new CreateFeedbackInBoardCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferDown() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferUp() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_CreateFeedbackInBoard_When_ArgumentsAreValid() {
        // Arrange, Act, Assert
        Assertions.assertDoesNotThrow(() -> createFeedbackInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        String.valueOf(VALID_RATING),
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidTitle() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand
                .execute(List.of("Invalid",
                        VALID_DESCRIPTION,
                        String.valueOf(VALID_RATING),
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidDescription() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand
                .execute(List.of(VALID_TITLE,
                        "Invalid",
                        String.valueOf(VALID_RATING),
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_RatingNegative() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        String.valueOf(-1),
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_RatingOver100() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        String.valueOf(101),
                        VALID_TEAM_NAME,
                        VALID_BOARD_NAME)));
    }


    @Test
    public void execute_Should_ThrowException_When_TeamNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        String.valueOf(VALID_RATING),
                        "Invalid",
                        VALID_BOARD_NAME)));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand
                .execute(List.of(VALID_TITLE,
                        VALID_DESCRIPTION,
                        String.valueOf(VALID_RATING),
                        VALID_TEAM_NAME,
                        "Invalid")));
    }

    @Test
    public void execute_Should_LogInBoard_When_ValidArguments() {
        // Arrange
        String expectedLog = String.format(CREATE_FEEDBACK_SUCCESS_MESSAGE_IN_BOARD, 4);
        Board currentBoard = repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME);

        // Act
        createFeedbackInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                String.valueOf(VALID_RATING),
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedLog, currentBoard.getActivityHistory().get(currentBoard.getActivityHistory().size() - 1));
    }

    @Test
    public void execute_Should_CreateFeedbackInBoard_When_ValidArguments() {
        // Arrange
        Board currentBoard = repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME);
        int expectedLength = currentBoard.getTasks().size() + 1;

        // Act
        createFeedbackInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                String.valueOf(VALID_RATING),
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedLength, currentBoard.getTasks().size());
    }

    @Test
    public void execute_Should_ReturnProperMessage_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format(CREATE_FEEDBACK_SUCCESS_MESSAGE, 4, VALID_BOARD_NAME, VALID_TEAM_NAME);

        // Act
        String actualOutput = createFeedbackInBoardCommand.execute(List.of(VALID_TITLE,
                VALID_DESCRIPTION,
                String.valueOf(VALID_RATING),
                VALID_TEAM_NAME,
                VALID_BOARD_NAME));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
