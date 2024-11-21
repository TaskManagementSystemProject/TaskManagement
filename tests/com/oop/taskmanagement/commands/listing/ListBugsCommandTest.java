package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.TaskManagementRepositoryImpl;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;

public class ListBugsCommandTest {
    private TaskManagementRepository repository;
    private Command listBugsCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        listBugsCommand = new ListBugsCommand(repository);
    }

    @Test
    public void execute_Should_ReturnAllStoriesAsString_When_NoParametersPassed() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToString());

        // Act
        String actualOutput = listBugsCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidListingType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("InvalidListing")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidFilteringType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("filter", "InvalidFilter")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatus() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("filter", "status")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidStatusInFilterArgumentForBug() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("filter", "status", "In progress")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("filter", "assignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatusAndAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("filter", "statusandassignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidStatusTypeForBug() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("filter", "status", "InProgress")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsSort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("sort")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgument() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("sort", "INVALID")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgumentForBug() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("sort", "rating")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortingTypeProvided() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> listBugsCommand.execute(List.of("filtersort", "assignee", "Pesho", "rating")));
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoStoriesFoundMatchingTheArguments() {
        // Arrange
        String expectedOutput = "There are NO bugs matching the given parameters.";
        // Act
        String actualOutput = listBugsCommand.execute(List.of("filter", "assignee", "NOTFOUND"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoParametersNothingFound() {
        // Arrange
        String expectedOutput = "There are NO bugs matching the given parameters.";

        repository = new TaskManagementRepositoryImpl();
        listBugsCommand = new ListBugsCommand(repository);
        // Act
        String actualOutput = listBugsCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatus_When_ValidArgumentsForFilteringStatus() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToString());
        // Act
        String actualOutput = listBugsCommand.execute(List.of("filter", "status", "Active"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByAssignee_When_ValidArgumentsForFilteringAssignee() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToStringWithAssignee());
        repository.findBugById(2).setAssigneeName("Pesho");
        // Act
        String actualOutput = listBugsCommand.execute(List.of("filter", "assignee", "Pesho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatusAndAssignee_When_ValidArgumentsForFilteringStatusAndAssignee() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToStringWithAssignee());
        repository.findBugById(2).setAssigneeName("Pesho");
        // Act
        String actualOutput = listBugsCommand.execute(List.of("filter", "statusandassignee", "Active", "Pesho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnSortedByTitle_When_ValidArgumentsForSortingByTitle() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToString(), getExpectedBugToStringSecond());
        // Act
        String actualOutput = listBugsCommand.execute(List.of("sort", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredStatusParamsSortedByTitle_When_ValidArguments() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToString(), getExpectedBugToStringSecond());
        // Act
        String actualOutput = listBugsCommand.execute(List.of("filtersort", "status", "active", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredAssigneeAndStatusParamsSortedByTitle_When_ValidArguments() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));
        repository.findTaskById(2).setAssigneeName("Pesho");
        repository.findTaskById(4).setAssigneeName("Pesho");

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE,
                getExpectedBugToStringWithAssignee(), getExpectedBugToStringWithAssigneeSecond());

        // Act
        String actualOutput = listBugsCommand.execute(List.of("filtersort", "statusandassignee", "active", "Pesho", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredAssigneeParamsSortedByTitle_When_ValidArguments() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));
        repository.findTaskById(2).setAssigneeName("Pesho");
        repository.findTaskById(4).setAssigneeName("Pesho");

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE,
                getExpectedBugToStringWithAssignee(), getExpectedBugToStringWithAssigneeSecond());

        // Act
        String actualOutput = listBugsCommand.execute(List.of("filtersort", "assignee", "Pesho", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredAssigneeParamsSortedByPriority_When_ValidArguments() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));
        repository.findTaskById(2).setAssigneeName("Pesho");
        repository.findTaskById(4).setAssigneeName("Pesho");

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE,
                getExpectedBugToStringWithAssignee(), getExpectedBugToStringWithAssigneeSecond());

        // Act
        String actualOutput = listBugsCommand.execute(List.of("filtersort", "assignee", "Pesho", "priority"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredAssigneeParamsSortedBySeverity_When_ValidArguments() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));
        repository.findTaskById(2).setAssigneeName("Pesho");
        repository.findTaskById(4).setAssigneeName("Pesho");

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE,
                getExpectedBugToStringWithAssignee(), getExpectedBugToStringWithAssigneeSecond());

        // Act
        String actualOutput = listBugsCommand.execute(
                List.of("filtersort", "assignee", "Pesho", "severity"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnSortedByPriority_When_ValidArgumentsForSortingByPriority() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToString(), getExpectedBugToStringSecond());
        // Act
        String actualOutput = listBugsCommand.execute(List.of("sort", "priority"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnSortedBySize_When_ValidArgumentsForSortingBySize() {
        // Arrange
        repository.createBugInBoard("must be last",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", BUGS_PREFIX_MESSAGE, getExpectedBugToString(), getExpectedBugToStringSecond());
        // Act
        String actualOutput = listBugsCommand.execute(List.of("sort", "severity"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }


    private String getExpectedBugToString() {
        return String.format(EXPECTED_BUG_TO_STRING_FORMAT,
                2,
                VALID_TITLE,
                StatusType.ACTIVE,
                PriorityType.LOW,
                SeverityType.MINOR,
                "None");
    }

    private String getExpectedBugToStringWithAssignee() {
        return String.format(EXPECTED_BUG_TO_STRING_FORMAT,
                2,
                VALID_TITLE,
                StatusType.ACTIVE,
                PriorityType.LOW,
                SeverityType.MINOR,
                "Pesho");
    }

    private String getExpectedBugToStringSecond() {
        return String.format(EXPECTED_BUG_TO_STRING_FORMAT,
                4,
                "must be last",
                StatusType.ACTIVE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                "None");
    }

    private String getExpectedBugToStringWithAssigneeSecond() {
        return String.format(EXPECTED_BUG_TO_STRING_FORMAT,
                4,
                "must be last",
                StatusType.ACTIVE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                "Pesho");
    }
}
