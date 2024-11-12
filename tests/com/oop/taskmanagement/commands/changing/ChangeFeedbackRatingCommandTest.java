package com.oop.taskmanagement.commands.changing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ChangeFeedbackRatingCommandTest {
    private final static String FEEDBACK_RATING_CHANGED_SUCCESSFULLY
            = "Feedback with ID %d rating changed to %d successfully.";
    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command changeFeedbackRating;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        changeFeedbackRating = new ChangeFeedbackRatingCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferDown() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackRating.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferUp() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackRating.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNotDigit() {
        // Arrange
        List<String> parameters = List.of("Invalid", "50");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackRating.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_RatingNotDigit() {
        // Arrange
        List<String> parameters = List.of("1", "Invalid");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackRating.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange
        List<String> parameters = List.of("1000", "50");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackRating.execute(parameters));
    }

    @Test
    public void execute_Should_ChangeRating_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of("1", "50");
        String expectedOutput = String.format(FEEDBACK_RATING_CHANGED_SUCCESSFULLY, 1, 50);

        // Act
        String actualOutput = changeFeedbackRating.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowExc_When_RatingIsChangedToTheSameRating() {
        // Arrange
        List<String> parameters = List.of("1", "5");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackRating.execute(parameters));
    }
}
