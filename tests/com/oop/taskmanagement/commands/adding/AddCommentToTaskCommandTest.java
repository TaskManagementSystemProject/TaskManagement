package com.oop.taskmanagement.commands.adding;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.utils.TestUtilities;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;

public class AddCommentToTaskCommandTest {

    private static final int ARGUMENT_COUNT = 3;
    private TaskManagementRepository repository;
    private Command addCommentToTask;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        addCommentToTask = new AddCommentToTaskCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDiffer() {
        // Arrange
        List<String> parameters = TestUtilities.getList(ARGUMENT_COUNT - 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addCommentToTask.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WhenIdNotNumber() {
        // Arrange
        List<String> parameters = List.of("INVALID", VALID_MEMBER_NAME_TWO, VALID_COMMENT_MESSAGE);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addCommentToTask.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WhenTaskIdNotFound() {
        // Arrange
        List<String> parameters = List.of("15", VALID_MEMBER_NAME_TWO, VALID_COMMENT_MESSAGE);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addCommentToTask.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberNotFound() {
        // Arrange
        List<String> parameters = List.of(VALID_TASK_ID, "NOTFOUND", VALID_COMMENT_MESSAGE);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addCommentToTask.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_CommentContentOutOfBounds() {
        // Arrange
        List<String> parameters = List.of(VALID_TASK_ID, VALID_MEMBER_NAME_ONE, "a");

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addCommentToTask.execute(parameters));
    }

    @Test
    public void execute_Should_AddCommentToTask_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of(VALID_TASK_ID, VALID_MEMBER_NAME_ONE, VALID_COMMENT_MESSAGE);
        TaskBase taskBase = repository.findTaskById(1);
        String expectedMessage = String.format(EXPECTED_COMMENT_TO_STRING);

        // Act
        addCommentToTask.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedMessage, taskBase.getComments().get(0).toString());
    }

    @Test
    public void execute_Should_LogToTask_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of(VALID_TASK_ID, VALID_MEMBER_NAME_ONE, VALID_COMMENT_MESSAGE);
        TaskBase taskBase = repository.findTaskById(1);
        String expectedMessage = TASK_SUCCESSFUL_LOG;

        // Act
        addCommentToTask.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedMessage, taskBase.getEventLog().get(taskBase.getEventLog().size() - 1));
    }

    @Test
    public void execute_Should_LogToMember_When_ValidArguments() {
        // Arrange
        List<String> parameters = List.of(VALID_TASK_ID, VALID_MEMBER_NAME_ONE, VALID_COMMENT_MESSAGE);
        Member member = repository.findMemberByName(VALID_MEMBER_NAME_ONE);
        String expectedMessage = MEMBER_EXPECTED_LOG;

        // Act
        addCommentToTask.execute(parameters);

        // Assert
        Assertions.assertEquals(expectedMessage, member.getActivityHistory().get(member.getActivityHistory().size() - 1));
    }
}
