package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShowTeamMembersCommandTest {
    private TaskManagementRepository repository;
    private Command showTeamMembers;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        showTeamMembers = new ShowTeamMembersCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showTeamMembers.execute(List.of()));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNotFound(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showTeamMembers.execute(List.of("INVALID")));
    }

    @Test
    public void execute_Should_ReturnMembersOfTeamAsString_When_ValidArguments(){
        // Arrange
        String expectedOutput = String.format("Name: Pesho, type: member.%n%nName: TestMember, type: member.");
        repository.createMember("TestMember");
        Team team = repository.findTeamByName("Otbor1");
        team.addMember(repository.findMemberByName("TestMember"));


        // Act
        String actualOutput = showTeamMembers.execute(List.of("Otbor1"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnProperStringWhenNoBoardsOfTeam_When_ValidArguments(){
        // Arrange
        String expectedOutput = "There are no members in team Otbor2 yet.";
        repository.createTeam("Otbor2");

        // Act
        String actualOutput = showTeamMembers.execute(List.of("Otbor2"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
