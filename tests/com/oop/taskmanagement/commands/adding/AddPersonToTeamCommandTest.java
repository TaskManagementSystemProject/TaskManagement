package com.oop.taskmanagement.commands.adding;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddPersonToTeamCommandTest {

    private static final int ARGUMENT_COUNT = 2;
    private TaskManagementRepository repository;
    private Command addPersonToTeamCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        addPersonToTeamCommand = new AddPersonToTeamCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferDown() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addPersonToTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferUp() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addPersonToTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberNameIsInvalid() {
        // Arrange
        List<String> parameters = List.of("NonExistentMemberName", "Otbor");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addPersonToTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNameIsInvalid() {
        // Arrange
        List<String> parameters = List.of("Gosho", "InvalidTeamName");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addPersonToTeamCommand.execute(parameters));
    }


    @Test
    public void execute_Should_AddEventMessageToEventLog_When_ParametersAreValid() {
        // Arrange
        List<String> parameters = List.of("Gosho", "Otbor");
        addPersonToTeamCommand.execute(parameters);
        String history = String.valueOf(repository.findMemberByName("Gosho").getActivityHistory().get(0));

        // Act, Assert
        Assertions.assertEquals("Member Gosho added to team Otbor successfully.", history);
    }
}
