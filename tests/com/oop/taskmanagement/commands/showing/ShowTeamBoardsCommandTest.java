package com.oop.taskmanagement.commands.showing;


import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShowTeamBoardsCommandTest {

    private TaskManagementRepository repository;
    private Command showTeamBoards;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        showTeamBoards = new ShowTeamBoardsCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showTeamBoards.execute(List.of()));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNotFound(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showTeamBoards.execute(List.of("INVALID")));
    }

    @Test
    public void execute_Should_ReturnBoardsOfTeamAsString_When_ValidArguments(){
        // Arrange
        String expectedOutput = String.format("Name: White, type: board.%nName: Black, type: board.");
        repository.createBoardInTeam("Black", repository.findTeamByName("Otbor"));
        // TODO for all listings to put some counter in front, to 1. data then 2.data etc

        // Act
        String actualOutput = showTeamBoards.execute(List.of("Otbor"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnEmptyStringWhenNoBoardsOfTeam_When_ValidArguments(){
        // Arrange
        String expectedOutput = "";
        repository.createTeam("Otbor2");

        // Act
        String actualOutput = showTeamBoards.execute(List.of("Otbor2"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

}
