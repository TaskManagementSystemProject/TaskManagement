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

import static com.oop.taskmanagement.utils.Constants.FEEDBACK_STATUS_CHANGED_SUCCESSFULLY;

public class ChangeFeedbackStatusCommandTest {
    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command changeFeedbackStatus;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        changeFeedbackStatus = new ChangeFeedbackStatusCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNotDigit() {
        // Arrange
        List<String> parameters = List.of("1a", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_StatusTypeInvalid() {
        // Arrange
        List<String> parameters = List.of("15", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange
        List<String> parameters = List.of("15", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeFeedbackStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ChangeStatus_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of("1", "DONE");
        String expectedOutput = String.format(FEEDBACK_STATUS_CHANGED_SUCCESSFULLY, 1, "Done");
        // Act
        String actualOutput = changeFeedbackStatus.execute(parameters);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
