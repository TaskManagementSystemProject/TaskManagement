package com.oop.taskmanagement.commands.assigning;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.TaskManagementRepositoryImpl;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnassignFromMemberCommandTest {

    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command unassignFromMemberCommand;


    @BeforeEach
    public void initialize() {
        repository = new TaskManagementRepositoryImpl();
        unassignFromMemberCommand = new UnassignFromMemberCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> unassignFromMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNotDigit() {
        // Arrange
        List<String> parameters = List.of("1a", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> unassignFromMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange
        List<String> parameters = List.of("1", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> unassignFromMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberDoesNotExist() {
        // Arrange
        List<String> parameters = List.of("1", "Gosho");
        repository = ValidInitialization.createDummyRepository();
        // all that was to fill

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> unassignFromMemberCommand.execute(parameters));
    }


    private List<String> getValidParameters() {
        return new ArrayList<>(Arrays.asList("1", "Gosho"));
    }
}
