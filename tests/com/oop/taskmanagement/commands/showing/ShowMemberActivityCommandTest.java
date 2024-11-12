package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShowMemberActivityCommandTest {

    private TaskManagementRepository repository;
    private Command showMemberActivityCommand;

    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        showMemberActivityCommand = new ShowMemberActivityCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showMemberActivityCommand.execute(List.of("to", "many arguments")));
    }

    @Test
    public void execute_Should_ReturnActivity_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format("ACTIVITY for member Gosho:%nTest Activity");
        repository.findMemberByName("Gosho").logActivity("Test Activity");

        // Act
        String actualOutput = showMemberActivityCommand.execute(List.of("Gosho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_PrintEmptyMemberActivityMessage_When_NoActivityYet() {
        // Arrange
        String expectedOutput = "There is no activity in member Gosho yet.";

        // Act
        String actualOutput = showMemberActivityCommand.execute(List.of("Gosho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
