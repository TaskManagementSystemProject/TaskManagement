package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.TaskManagementRepositoryImpl;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShowTeamsCommandTest {

    private TaskManagementRepository repository;
    private Command showTeams;

    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        showTeams = new ShowTeamsCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showTeams.execute(List.of("EXTRA ARGUMENTS")));
    }

    @Test
    public void execute_Should_ReturnTeamsAsString_When_ValidArguments(){
        // Arrange
        String expectedOutput = String.format("TEAMS:%nName: Otbor, type: team.%nName: Otbor1, type: team.%nName: TestTeam, type: team.");
        repository.createTeam("TestTeam");

        // Act
        String actualOutput = showTeams.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnNoTeamsMessage_When_ValidArgumentsButNoTeams(){
        // Arrange
        String expectedOutput = "There are no teams yet.";
        repository = new TaskManagementRepositoryImpl();
        showTeams = new ShowTeamsCommand(repository);

        // Act
        String actualOutput = showTeams.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);

    }
}
