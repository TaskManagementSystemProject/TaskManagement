package com.oop.taskmanagement.models.team;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.oop.taskmanagement.utils.Constants.INVALID_MEMBER_LONG_NAME;
import static com.oop.taskmanagement.utils.Constants.INVALID_MEMBER_SHORT_NAME;
import static com.oop.taskmanagement.utils.ValidInitialization.initializeValidBug;
import static com.oop.taskmanagement.utils.ValidInitialization.initializeValidMember;


public class MemberImplTest {

    @Test
    public void MemberImpl_Should_ImplementMemberInterface() {
        // Arrange, Act
        MemberImpl member = initializeValidMember();
        // Assert
        Assertions.assertTrue(member instanceof Member);
    }

    @Test
    public void constructor_Should_ThrowException_When_NameOutOfLowerBounds() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new MemberImpl(INVALID_MEMBER_SHORT_NAME)
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_NameOutOfUpperBounds() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new MemberImpl(INVALID_MEMBER_LONG_NAME)
        );
    }

    @Test
    public void constructor_Should_InitializeTasksList_When_ValidParameters() {
        // Arrange
        Member member = initializeValidMember();

        // Act, Assert
        Assertions.assertNotNull(member.getTasks());
    }

    @Test
    public void constructor_Should_InitializeActivityList_When_ValidParameters() {
        // Arrange
        Member member = initializeValidMember();

        // Act, Assert
        Assertions.assertNotNull(member.getActivityHistory());
    }

    @Test
    public void toString_Should_ConvertToStringWithProperFormat() {
        // Arrange
        Member member = new MemberImpl("ValidName");
        String expectedResult = "Name: ValidName";

        // Act
        String actualOutput = member.toString();

        // Assert
        Assertions.assertEquals(expectedResult, actualOutput);
    }

    @Test
    public void addTask_Should_AddTaskToTasksList() {
        // Arrange
        TaskBase validBug = initializeValidBug();
        Member member = initializeValidMember();

        // Act
        member.addTask(validBug);

        // Assert
        Assertions.assertEquals(1, member.getTasks().size());
    }


    @Test
    public void removeTask_Should_RemoveTaskFromTasksList_WhenTaskExists() {
        // Arrange
        Member member = initializeValidMember();
        TaskBase validBug = initializeValidBug();
        member.addTask(validBug);

        // Act
        member.removeTask(validBug);

        // Assert
        Assertions.assertEquals(0, member.getTasks().size());
    }

    @Test
    public void removeTask_Should_DoNothing_When_TaskIsNotInTasksList() {
        // Arrange
        TaskBase validBugToAdd = initializeValidBug();
        TaskBase differentValidBugToRemove = initializeValidBug();
        Member member = initializeValidMember();
        member.addTask(validBugToAdd);

        // Act
        member.removeTask(differentValidBugToRemove);

        // Assert
        Assertions.assertEquals(1, member.getTasks().size());
    }

    @Test
    public void removeTask_Should_RemoveTaskFromTasksList_TaskIsInTasksList() {
        // Arrange
        Member member = initializeValidMember();
        TaskBase bug = initializeValidBug();
        member.addTask(bug);

        // Act
        member.removeTask(bug);

        // Assert
        Assertions.assertEquals(0, member.getTasks().size());
    }
}
