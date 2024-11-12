package com.oop.taskmanagement.commands.assigning;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.TASK_UNASSIGNED_SUCCESSFULLY;

public class UnassignFromMemberCommandTest {

    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command unassignFromMemberCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
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
        List<String> parameters = List.of("15", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> unassignFromMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberDoesNotExist() {
        // Arrange
        List<String> parameters = List.of("1", "DoesNot");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> unassignFromMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberIsNotTheOwnerOfTheTask() {
        // Arrange
        List<String> parameters = List.of("1", "Pesho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> unassignFromMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_UnassignFromMember_When_ValidParameters() {
        // Arrange
        List<String> parameters = List.of("1", "Gosho");

        // Act
        unassignFromMemberCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(0, repository.findMemberByName("Gosho").getTasks().size());
    }

    @Test
    public void execute_Should_SetTaskAssigneeToNull_When_ValidParameters() {
        // Arrange
        List<String> parameters = List.of("1", "Gosho");

        // Act
        unassignFromMemberCommand.execute(parameters);

        // Assert
        Assertions.assertNull(repository.findTaskById(1).getAssigneeName());
    }

    @Test
    public void execute_Should_ReturnProperMessage_When_ValidParameters() {
        // Arrange
        List<String> parameters = List.of("1", "Gosho");
        String expectedOutput = String.format(TASK_UNASSIGNED_SUCCESSFULLY, 1, "Gosho");

        // Act
        String actualOutput = unassignFromMemberCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
