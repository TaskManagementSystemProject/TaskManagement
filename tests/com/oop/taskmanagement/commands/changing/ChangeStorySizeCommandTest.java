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

import static com.oop.taskmanagement.utils.Constants.STORY_ATTRIBUTE_CHANGED_SUCCESSFULLY;

public class ChangeStorySizeCommandTest {
    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command changeStorySize;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        changeStorySize = new ChangeStorySizeCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeStorySize.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNotDigit() {
        // Arrange
        List<String> parameters = List.of("1a", "Large");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeStorySize.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_StatusTypeInvalid() {
        // Arrange
        List<String> parameters = List.of("15", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeStorySize.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange
        List<String> parameters = List.of("15", "Large");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeStorySize.execute(parameters));
    }

    @Test
    public void execute_Should_ChangeSize_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of("3", "Large");
        String expectedOutput = String.format(STORY_ATTRIBUTE_CHANGED_SUCCESSFULLY, 3, "size", "Large");

        // Act
        String actualOutput = changeStorySize.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
