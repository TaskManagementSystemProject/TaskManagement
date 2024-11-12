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

public class ChangeBugStatusCommandTest {

    private final static String BUG_STATUS_CHANGED_SUCCESSFULLY
            = "Bug with ID %d status changed to %s successfully.";
    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command changeBugStatus;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        changeBugStatus = new ChangeBugStatusCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferDown() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferUp() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNotDigit() {
        // Arrange
        List<String> parameters = List.of("1a", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_StatusTypeInvalid() {
        // Arrange
        List<String> parameters = List.of("Invalid", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange
        List<String> parameters = List.of("1000", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugStatus.execute(parameters));
    }

    @Test
    public void execute_Should_ChangeStatus_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of("2", "DONE");
        String expectedOutput = String.format(BUG_STATUS_CHANGED_SUCCESSFULLY, 2, "Done");

        // Act
        String actualOutput = changeBugStatus.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowExc_When_StatusIsChangedToTheSameStatus() {
        // Arrange
        List<String> parameters = List.of("2", "ACTIVE");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugStatus.execute(parameters));
    }
}
