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


public class ChangeBugPriorityCommandTest {

    private final static String BUG_PRIORITY_CHANGED_SUCCESSFULLY
            = "Bug with ID %d priority changed to %s successfully.";
    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command changeBugPriority;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        changeBugPriority = new ChangeBugPriorityCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferDown() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugPriority.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferUp() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugPriority.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNotDigit() {
        // Arrange
        List<String> parameters = List.of("1a", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugPriority.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_PriorityTypeInvalid() {
        // Arrange
        List<String> parameters = List.of("Invalid", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugPriority.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange
        List<String> parameters = List.of("1000", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeBugPriority.execute(parameters));
    }

    @Test
    public void execute_Should_ChangePriority_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of("2", "MEDIUM");
        String expectedOutput = String.format(BUG_PRIORITY_CHANGED_SUCCESSFULLY, 2, "Medium");

        // Act
        String actualOutput = changeBugPriority.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void execute_Should_ThrowExc_When_PriorityIsChangedToTheSamePriority() {
        // Arrange
        List<String> parameters = List.of("2", "LOW");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,() ->changeBugPriority.execute(parameters));
    }

}
