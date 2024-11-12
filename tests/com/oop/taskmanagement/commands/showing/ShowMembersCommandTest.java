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

public class ShowMembersCommandTest {

    private TaskManagementRepository repository;
    private Command showMembersCommand;

    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        showMembersCommand = new ShowMembersCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showMembersCommand.execute(List.of("EXTRA ARGUMENTS")));
    }

    @Test
    public void execute_Should_ReturnTeamsAsString_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format("MEMBERS:%nName: Gosho, type: member.%nName: Pesho, type: member.%nName: TestMember, type: member.");
        repository.createMember("TestMember");

        // Act
        String actualOutput = showMembersCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_EmptyMembersMessage_When_RepoIsEmpty() {
        // Arrange
        TaskManagementRepository repository1 = new TaskManagementRepositoryImpl();
        Command showMembersCommand1 = new ShowMembersCommand(repository1);
        String expectedOutput = "There are no members yet.";

        // Act
        String actualOutput = showMembersCommand1.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
