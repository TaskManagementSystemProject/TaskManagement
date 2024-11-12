package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;

public class CreateTeamCommandTest {
    private static final int ARGUMENT_COUNT = 1;
    private TaskManagementRepository repository;
    private Command createTeamCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        createTeamCommand = new CreateTeamCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_NameAlreadyExists() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createTeamCommand.execute(List.of(VALID_TEAM_NAME)));
    }

    @Test
    public void execute_Should_CreateTeam_When_ValidArguments() {
        // Arrange, Act
        createTeamCommand.execute(List.of("New team"));

        // Assert
        Assertions.assertEquals(3, repository.getTeams().size());
    }

    @Test
    public void execute_Should_ReturnProperMessage_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format(CREATE_TEAM_SUCCESS_MESSAGE, "New team");

        // Act
        String actualOutput = createTeamCommand.execute(List.of("New team"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
