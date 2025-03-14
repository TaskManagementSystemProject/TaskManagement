package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.TaskManagementRepositoryImpl;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;

public class ListStoriesCommandTest {
    private TaskManagementRepository repository;
    private Command listStoriesCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        listStoriesCommand = new ListStoriesCommand(repository);
    }

    @Test
    public void execute_Should_ReturnAllStoriesAsString_When_NoParametersPassed() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString());

        // Act
        String actualOutput = listStoriesCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidListingType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("InvalidListing")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidFilteringType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter", "InvalidFilter")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatus() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter", "status")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidStatusFeedbackFilterStatus() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter", "status", "unscheduled")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter", "assignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatusAndAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter", "statusandassignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsSort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("sort")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgument() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("sort", "INVALID")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidFilterSortArgument() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filtersort", "INVALID")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgumentForStory() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("sort", "severity")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidFilterSortArgumentForStory() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filtersort", "status", "not done", "rating")));
    }

    @Test
    public void execute_Should_ReturnFilteredByStatus_When_ValidArgumentsForFilteringStatus() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString());

        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filter", "status", "not done"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoStoriesFoundMatchingTheArguments() {
        // Arrange
        String expectedOutput = "There are NO stories matching the given parameters.";

        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filter", "assignee", "NOTFOUND"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoStoriesFoundMatchingValidAssigneeName() {
        // Arrange
        String expectedOutput = "There are NO stories matching the given parameters.";

        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filtersort", "assignee", "InvalidAssignee", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoStoriesFoundMatchingValidStatus() {
        // Arrange
        String expectedOutput = "There are NO stories matching the given parameters.";

        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filtersort", "status", "Done", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoStoriesFoundMatchingValidStatusAndAssignee() {
        // Arrange
        String expectedOutput = "There are NO stories matching the given parameters.";

        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filtersort", "statusAndAssignee", "Not Done", "InvalidName", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnInformativeMessage_When_NoParametersNothingFound() {
        // Arrange
        String expectedOutput = "There are NO stories matching the given parameters.";

        repository = new TaskManagementRepositoryImpl();
        listStoriesCommand = new ListStoriesCommand(repository);
        // Act
        String actualOutput = listStoriesCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredStories_When_ValidFilteringArguments() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString());
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filter", "assignee", "Pesho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatusAndAssignee_When_ValidArgumentsForFilteringStatusAndAssignee() {
        // Arrange
        String expectedOutput = String.format("%s%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString());
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filter", "statusandassignee", "Not Done", "Pesho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidStatusTypeForStories() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter", "statusandassignee", "Active", "Gosho")));
    }

    @Test
    public void execute_Should_ReturnSortedByTitle_When_ValidArgumentsForSortingByTitle() {
        // Arrange
        repository.createStoryInBoard("mustbefirst",
                VALID_DESCRIPTION,
                PriorityType.MEDIUM,
                SizeType.MEDIUM,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString(), getExpectedStoriesToStringSecond());
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("sort", "title"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnSortedByPriority_When_ValidArgumentsForSortingByPriority() {
        // Arrange
        repository.createStoryInBoard("mustbefirst",
                VALID_DESCRIPTION,
                PriorityType.MEDIUM,
                SizeType.MEDIUM,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString(), getExpectedStoriesToStringSecond());
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("sort", "priority"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnSortedBySize_When_ValidArgumentsForSortingBySize() {
        // Arrange
        repository.createStoryInBoard("mustbefirst",
                VALID_DESCRIPTION,
                PriorityType.MEDIUM,
                SizeType.MEDIUM,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString(), getExpectedStoriesToStringSecond());
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("sort", "size"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatusSortedBySize_When_ValidArguments() {
        // Arrange
        repository.createStoryInBoard("mustbefirst",
                VALID_DESCRIPTION,
                PriorityType.MEDIUM,
                SizeType.MEDIUM,
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%n%s", STORIES_PREFIX_MESSAGE, getExpectedStoriesToString(), getExpectedStoriesToStringSecond());
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filtersort", "status", "not done", "size"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    private String getExpectedStoriesToString() {
        return String.format(EXPECTED_STORY_TO_STRING_FORMAT,
                3,
                VALID_TITLE,
                StatusType.NOT_DONE,
                VALID_PRIORITY_TYPE_STORY,
                VALID_SIZE_TYPE_STORY,
                VALID_MEMBER_NAME_TWO);
    }

    private String getExpectedStoriesToStringSecond() {
        return String.format(EXPECTED_STORY_TO_STRING_FORMAT,
                4,
                "mustbefirst",
                StatusType.NOT_DONE,
                PriorityType.MEDIUM,
                SizeType.MEDIUM,
                "None");
    }
}
