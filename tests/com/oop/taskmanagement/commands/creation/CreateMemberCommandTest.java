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

import static com.oop.taskmanagement.utils.Constants.CREATE_MEMBER_SUCCESS_MESSAGE;
import static com.oop.taskmanagement.utils.Constants.VALID_MEMBER_NAME_ONE;

public class CreateMemberCommandTest {
    private static final int ARGUMENT_COUNT = 1;
    private TaskManagementRepository repository;
    private Command createMemberCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        createMemberCommand = new CreateMemberCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createMemberCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_NameAlreadyExists() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createMemberCommand.execute(List.of(VALID_MEMBER_NAME_ONE)));
    }

    @Test
    public void execute_Should_CreateMember_When_ValidArguments() {
        // Arrange, Act
        createMemberCommand.execute(List.of("New member"));

        // Assert
        Assertions.assertEquals(3, repository.getMembers().size());
    }

    @Test
    public void execute_Should_ReturnProperMessage_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format(CREATE_MEMBER_SUCCESS_MESSAGE, "New member");

        // Act
        String actualOutput = createMemberCommand.execute(List.of("New member"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
