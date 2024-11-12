package com.oop.taskmanagement.commands.assigning;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class AssignToMemberCommandTest {

    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command assignToMemberCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        assignToMemberCommand = new AssignToMemberCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> assignToMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNotDigit() {
        // Arrange
        List<String> parameters = List.of("1a", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> assignToMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange
        List<String> parameters = List.of("10000", "Gosho");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> assignToMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberDoesNotExist() {
        // Arrange
        List<String> parameters = List.of("1", "InvalidMemberName");
        repository = ValidInitialization.createDummyRepository();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> assignToMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_SetTaskAssigneeToNull_When_ValidParameters() {
        // Arrange
        List<String> parameters = List.of("1", "Damyan");
        repository.createMember("Damyan");
        repository.findTeamByName("Otbor").addMember(repository.findMemberByName("Damyan"));
        Member originalAssigneeOfTask = repository.findMemberByTask(repository.findTaskById(1));

        // Act
        assignToMemberCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(0, originalAssigneeOfTask.getTasks().size());
    }

    @Test
    public void execute_Should_NotUnassign_When_TaskWasNotAssigned() {
        // Arrange
        List<String> parameters = List.of("2", "Damyan");
        repository.createMember("Damyan");
        repository.findTeamByName("Otbor").addMember(repository.findMemberByName("Damyan"));
        Member originalAssigneeOfTask = repository.findMemberByTask(repository.findTaskById(2));

        // Act
        assignToMemberCommand.execute(parameters);

        // Assert
        Assertions.assertNull(originalAssigneeOfTask);
    }


}
