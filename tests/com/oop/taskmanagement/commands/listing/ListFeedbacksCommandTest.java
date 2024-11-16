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

public class ListFeedbacksCommandTest {

    private TaskManagementRepository repository;
    private Command listFeedbacksCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        listFeedbacksCommand = new ListFeedbacksCommand(repository);
    }

    @Test
    public void execute_Should_ReturnAllFeedbacksAsString_When_NoParametersPassed() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToString());

        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidListingType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("InvalidListing")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidFilteringType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("filter", "InvalidFilter")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatus() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("filter", "status")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("filter", "assignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatusAndAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("filter", "statusandassignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsSort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("sort")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgument() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("sort", "INVALID")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgumentForFeedback() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("sort", "size")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidFilterSortArgumentForFeedback() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listFeedbacksCommand.execute(List.of(
                        "filtersort", "statusandassignee", "Scheduled", "Gosho", "rating", "extra param")));
    }

    @Test
    public void execute_Should_ReturnFilteredByStatus_When_ValidArgumentsForFilteringStatus() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToString());
        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of("filter", "status", "NEW"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_ValidFilterButNothingWasFound() {
        // Arrange
        String expectedOutput = "There are NO feedbacks matching the given parameters.";

        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of("filter", "assignee", "NOTFOUND"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoParametersNothingFound() {
        // Arrange
        String expectedOutput = "There are NO feedbacks matching the given parameters.";

        repository = new TaskManagementRepositoryImpl();
        listFeedbacksCommand = new ListFeedbacksCommand(repository);
        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByAssignee_When_ValidArgumentsForFilteringAssignee() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToString());

        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of("filter", "assignee", "Gosho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatusAndAssignee_When_ValidArgumentsForFilteringStatusAndAssignee() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToStringThree());
        repository.findFeedbackById(1).changeStatus(StatusType.SCHEDULED);
        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of("filter", "statusandassignee", "scheduled", "Gosho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void execute_Should_ReturnFilteredByStatusAndAssigneeSortedByRating_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToStringThree());
        repository.findFeedbackById(1).changeStatus(StatusType.SCHEDULED);
        // Act
        String actualOutput = listFeedbacksCommand.execute(
                List.of("filtersort", "statusandassignee", "scheduled", "Gosho", "rating"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByAssigneeSortedByRating_When_ValidArguments() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToStringThree());
        repository.findFeedbackById(1).changeStatus(StatusType.SCHEDULED);
        // Act
        String actualOutput = listFeedbacksCommand.execute(
                List.of("filtersort", "assignee", "Gosho", "rating"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_StatusChangeInvalid() {
        // Arrange
        repository.findFeedbackById(1).changeStatus(StatusType.UNSCHEDULED);
        // Act

        // Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findTaskById(1).changeStatus(StatusType.UNSCHEDULED));
    }

    @Test
    public void execute_Should_ReturnFilteredByStatusSortedByRating_When_ValidArguments() {
        // Arrange
        repository.createFeedbackInBoard("mustbefirst",
                VALID_DESCRIPTION,
                4,
                repository.findTeamByName(VALID_TEAM_NAME),
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME));
        String expectedOutput = String.format(FEEDBACKS_PREFIX_MESSAGE + "%n" +
                getExpectedFeedbackToStringSecond() +
                "%n%n" +
                "Feedback with id: 1%n" +
                "Title: 10symb tit%n" +
                "Status: New%n" +
                "Rating: 5%n" +
                "Assigned to: Gosho");

        // Act
        String actualOutput = listFeedbacksCommand.execute(
                List.of("filtersort", "status", "new", "rating"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidStatusTypeForFeedback() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listFeedbacksCommand.execute(List.of("filter", "statusandassignee", "Active", "Gosho")));
    }

    @Test
    public void execute_Should_ReturnSortedByTitle_When_ValidArgumentsForSortingByTitle() {
        // Arrange
        repository.createFeedbackInBoard("mustbefirst",
                VALID_DESCRIPTION,
                4,
                repository.findTeamByName(VALID_TEAM_NAME),
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME));

        String expectedOutput = String.format("%s%n%s%n%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToString(), getExpectedFeedbackToStringSecond());
        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of("sort", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnSortedByRating_When_ValidArgumentsForSortingByRating() {
        // Arrange
        repository.createFeedbackInBoard("mustbefirst",
                VALID_DESCRIPTION,
                4,
                repository.findTeamByName(VALID_TEAM_NAME),
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME));

        String expectedOutput = String.format("%s%n%s%n%n%s", FEEDBACKS_PREFIX_MESSAGE, getExpectedFeedbackToStringSecond(), getExpectedFeedbackToString());
        // Act
        String actualOutput = listFeedbacksCommand.execute(List.of("sort", "rating"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }


    private String getExpectedFeedbackToString() {
        return String.format(EXPECTED_FEEDBACK_TO_STRING_FORMAT,
                1,
                VALID_TITLE,
                StatusType.NEW,
                VALID_RATING,
                VALID_MEMBER_NAME_ONE);
    }

    private String getExpectedFeedbackToStringSecond() {
        return String.format(EXPECTED_FEEDBACK_TO_STRING_FORMAT,
                4,
                "mustbefirst",
                StatusType.NEW,
                4,
                "None");
    }

    private String getExpectedFeedbackToStringThree() {
        return String.format(EXPECTED_FEEDBACK_TO_STRING_FORMAT,
                1,
                VALID_TITLE,
                StatusType.SCHEDULED,
                VALID_RATING,
                VALID_MEMBER_NAME_ONE);
    }
}
