package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShowBoardActivityCommandTest {

    private TaskManagementRepository repository;
    private Command showBoardActivityCommand;

    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        showBoardActivityCommand = new ShowBoardActivityCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showBoardActivityCommand.execute(List.of("only one argument")));
    }

    @Test
    public void execute_Should_ReturnTeamsAsString_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format("ACTIVITY for board White in team Otbor:%nTest Activity");
        repository.findBoardByTeamName("White", "Otbor").logActivity("Test Activity");

        // Act
        String actualOutput = showBoardActivityCommand.execute(List.of("White", "Otbor"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_PrintEmptyBoardActivityMessage_When_NoActivityYet() {
        // Arrange
        String expectedOutput = "There is no activity in board White yet.";

        // Act
        String actualOutput = showBoardActivityCommand.execute(List.of("White", "Otbor"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}