package com.oop.taskmanagement.models.team;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static com.oop.taskmanagement.utils.Constants.INVALID_BOARD_LONG_NAME;
import static com.oop.taskmanagement.utils.Constants.INVALID_BOARD_SHORT_NAME;
import static com.oop.taskmanagement.utils.ValidInitialization.initializeValidBoard;
import static com.oop.taskmanagement.utils.ValidInitialization.initializeValidBug;

public class BoardImplTest {


    @Test
    public void BoardImpl_Should_ImplementBoardInterface() {
        // Arrange, Act
        BoardImpl board = initializeValidBoard();
        // Assert
        Assertions.assertTrue(board instanceof Board);
    }

    @Test
    public void constructor_Should_ThrowException_When_NameOutOfLowerBounds() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new BoardImpl(INVALID_BOARD_SHORT_NAME)
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_NameOutOfUpperBounds() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new BoardImpl(INVALID_BOARD_LONG_NAME)
        );
    }

    @Test
    public void constructor_Should_InitializeTasksList_When_ValidParameters() {
        // Arrange
        Board board = initializeValidBoard();

        // Act, Assert
        Assertions.assertNotNull(board.getTasks());
    }

    @Test
    public void constructor_Should_InitializeActivityList_When_ValidParameters() {
        // Arrange
        Board board = initializeValidBoard();

        // Act, Assert
        Assertions.assertNotNull(board.getActivityHistory());
    }

    @Test
    public void toString_Should_ConvertToStringWithProperFormat() {
        // Arrange
        Board board = new BoardImpl("ValidName");
        String expectedResult = "Name: ValidName.";

        // Act
        String actualOutput = board.toString();

        // Assert
        Assertions.assertEquals(expectedResult, actualOutput);
    }

    @Test
    public void addTask_Should_AddTaskToTasksList() {
        // Arrange
        TaskBase validBug = initializeValidBug();
        Board board = initializeValidBoard();

        // Act
        board.addTask(validBug);

        // Assert
        Assertions.assertEquals(1, board.getTasks().size());
    }


    @Test
    public void removeTask_Should_RemoveTaskFromTasksList_WhenTaskExists() {
        // Arrange
        Board board = initializeValidBoard();
        TaskBase validBug = initializeValidBug();
        board.addTask(validBug);

        // Act
        board.removeTask(validBug);

        // Assert
        Assertions.assertEquals(0, board.getTasks().size());
    }

    @Test
    public void removeTask_Should_DoNothing_When_TaskIsNotInTasksList() {
        // Arrange
        TaskBase validBugToAdd = initializeValidBug();
        TaskBase differentValidBugToRemove = initializeValidBug();
        Board board = initializeValidBoard();
        board.addTask(validBugToAdd);

        // Act
        board.removeTask(differentValidBugToRemove);

        // Assert
        Assertions.assertEquals(1, board.getTasks().size());
    }

    @Test
    public void removeTask_Should_RemoveTaskFromTasksList_TaskIsInTasksList() {
        // Arrange
        Board board = initializeValidBoard();
        TaskBase bug = initializeValidBug();
        board.addTask(bug);

        // Act
        board.removeTask(bug);

        // Assert
        Assertions.assertEquals(0, board.getTasks().size());
    }
}
