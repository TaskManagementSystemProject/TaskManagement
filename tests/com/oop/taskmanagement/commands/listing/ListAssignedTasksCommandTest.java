package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.TaskManagementRepositoryImpl;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;

public class ListAssignedTasksCommandTest {
    private TaskManagementRepository repository;
    private Command listAssignedTasksCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        listAssignedTasksCommand = new ListAssignedTasksCommand(repository);
    }


    @Test
    public void execute_Should_ReturnAllTasksAsString_When_NoParametersPassed() {
        // Arrange
        String expectedOutput = String.format("%s%n%s%n%n%s",
                ASSIGNED_TASK_PREFIX_MESSAGE,
                FEEDBACK_TO_STRING_DUMMY_REPO,
                STORY_TO_STRING_DUMMY_REPO);

        // Act
        String actualOutput = listAssignedTasksCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidListingType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listAssignedTasksCommand.execute(List.of("InvalidListing")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsSort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listAssignedTasksCommand.execute(List.of("sort", "invalid")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilter() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listAssignedTasksCommand.execute(List.of("filter")));
    }

    @Test
    public void execute_Should_ReturnTasksSortedByTitle_When_ParameterSortProvided() {
        // Arrange
        repository.createFeedbackInBoard("0000000000",
                "Random description",
                10,
                repository.findTeamByName(VALID_TEAM_NAME_TWO),
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        repository.findTaskById(4).setAssigneeName("Pesho");

        String expectedOutput = String.format("%s%n%s%n%n%s%n%n%s",
                ASSIGNED_TASK_PREFIX_MESSAGE,
                getDummy(),
                FEEDBACK_TO_STRING_DUMMY_REPO,
                STORY_TO_STRING_DUMMY_REPO);

        // Act
        String actualOutput = listAssignedTasksCommand.execute(List.of("sort"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);

    }


    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoTaskFoundMatchingTheArguments() {
        // Arrange
        String expectedOutput = "There are NO assigned tasks matching the given parameters.";
        // Act
        String actualOutput = listAssignedTasksCommand.execute(List.of("filter", "assignee", "NOTFOUND"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoParametersNothingFound() {
        // Arrange
        String expectedOutput = "There are NO assigned tasks matching the given parameters.";

        repository = new TaskManagementRepositoryImpl();
        listAssignedTasksCommand = new ListAssignedTasksCommand(repository);
        // Act
        String actualOutput = listAssignedTasksCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatus_When_ValidArgumentsForFilteringStatus() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", ASSIGNED_TASK_PREFIX_MESSAGE, getDummyFeedback());
        // Act
        String actualOutput = listAssignedTasksCommand.execute(List.of("filter", "status", "nEw"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByAssignee_When_ValidArgumentsForFilteringAssignee() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", ASSIGNED_TASK_PREFIX_MESSAGE, getDummyFeedback());
        // Act
        String actualOutput = listAssignedTasksCommand.execute(List.of("filter", "assignee", "Gosho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatusAndAssignee_When_ValidArgumentsForFilteringStatusAndAssignee() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", ASSIGNED_TASK_PREFIX_MESSAGE, getDummyFeedback());

        // Act
        String actualOutput = listAssignedTasksCommand.execute(List.of("filter", "statusandassignee", "New", "Gosho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    private String getDummy() {
        return String.format(EXPECTED_FEEDBACK_TO_STRING_FORMAT,
                4,
                "0000000000",
                StatusType.NEW,
                10,
                "Pesho");
    }

    private String getDummyFeedback() {
        return String.format(EXPECTED_FEEDBACK_TO_STRING_FORMAT,
                1,
                VALID_TITLE,
                StatusType.NEW,
                VALID_RATING, //5
                "Gosho");
    }
}
