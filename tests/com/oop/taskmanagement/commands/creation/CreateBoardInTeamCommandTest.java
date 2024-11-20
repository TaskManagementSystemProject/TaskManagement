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


public class CreateBoardInTeamCommandTest {

    private static final int ARGUMENT_COUNT = 2;
    private static final String VALID_TEAM_IN_REPO = "Otbor";
    private TaskManagementRepository repository;
    private Command createBoardInTeamCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        createBoardInTeamCommand = new CreateBoardInTeamCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferDown() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBoardInTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferUp() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBoardInTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_NameOfBoardAlreadyExistsInTeam() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createBoardInTeamCommand.execute(List.of("White", "Otbor")));
    }

    @Test
    public void execute_Should_ThrowException_When_NameToShort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> createBoardInTeamCommand.execute(List.of("xxxx", "Otbor")));
    }

    @Test
    public void execute_Should_ThrowException_When_NameToLong() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> createBoardInTeamCommand.execute(List.of("xxxxxxxxxxx", "Otbor")));
    }

    @Test
    public void execute_Should_CreateBoard_When_ValidArguments() {
        // Arrange, Act
        createBoardInTeamCommand.execute(List.of("New board2", VALID_TEAM_IN_REPO));

        // Assert
        Assertions.assertEquals(2, repository.findTeamByName(VALID_TEAM_IN_REPO).getBoards().size());
    }

    @Test
    public void execute_Should_ReturnProperMessage_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format("Board %s created successfully in team %s.", "New board", "Otbor");

        // Act
        String actualOutput = createBoardInTeamCommand.execute(List.of("New board", "Otbor"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
