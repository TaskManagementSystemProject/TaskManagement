package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShowTaskHistoryCommandTest{

    private TaskManagementRepository repository;
    private Command ShowTaskHistoryCommandTest;

    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        ShowTaskHistoryCommandTest = new ShowTaskHistoryCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> ShowTaskHistoryCommandTest.execute(List.of("to", "many arguments")));
    }

    @Test
    public void execute_Should_ThrowException_When_WhenIdNotNumber() {
        // Arrange
        List<String> parameters = List.of("INVALID");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> ShowTaskHistoryCommandTest.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WhenTaskIdNotFound() {
        // Arrange
        List<String> parameters = List.of("15");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> ShowTaskHistoryCommandTest.execute(parameters));
    }
    @Test
    public void execute_Should_ReturnActivity_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format("LOG HISTORY for task 1:%nStatus changed from New to Done.");
        repository.findTaskById(1).changeStatus(StatusType.DONE);

        // Act
        String actualOutput = ShowTaskHistoryCommandTest.execute(List.of("1"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_PrintEmptyMemberActivityMessage_When_NoActivityYet() {
        // Arrange
        String expectedOutput = "There is no activity in task 1 yet.";

        // Act
        String actualOutput = ShowTaskHistoryCommandTest.execute(List.of("1"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
