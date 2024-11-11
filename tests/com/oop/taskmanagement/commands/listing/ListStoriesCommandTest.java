package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
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
    public void execute_Should_ReturnAllStoriesAsString_When_NoParametersPassed(){
        // Arrange
        String expectedOutput = getExpectedStoriesToString();

        // Act
        String actualOutput = listStoriesCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidListingType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("InvalidListing")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidFilteringType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter","InvalidFilter")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatus() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter","status")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter","assignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilterStatusAndAssignee() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("filter","statusandassignee")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsSort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("sort")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgument() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("sort","INVALID")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidSortArgumentForStory() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listStoriesCommand.execute(List.of("sort","severity")));
    }

    @Test
    public void execute_Should_ReturnFilteredByStatus_When_ValidArgumentsForFilteringStatus() {
        // Arrange
        String expectedOutput = getExpectedStoriesToString();
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filter", "status", "not done"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByAssignee_When_ValidArgumentsForFilteringAssignee() {
        // Arrange
        String expectedOutput = "";
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filter", "assignee", "NOTFOUND"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnFilteredByStatusAndAssignee_When_ValidArgumentsForFilteringStatusAndAssignee() {
        // Arrange
        String expectedOutput = getExpectedStoriesToString();
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("filter", "statusandassignee","Not Done" ,"Pesho"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ReturnSortedByTitle_When_ValidArgumentsForSortingByTitle() {
        // Arrange
        repository.createStoryInBoard("mustbefirst",
                VALID_DESCRIPTION,
                PriorityType.MEDIUM,
                SizeType.MEDIUM,
                repository.findTeamByName(VALID_TEAM_NAME_TWO),
                repository.findBoardByTeamName(VALID_BOARD_NAME,VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s",getExpectedStoriesToString(), getExpectedStoriesToStringSecond());
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
                repository.findTeamByName(VALID_TEAM_NAME_TWO),
                repository.findBoardByTeamName(VALID_BOARD_NAME,VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s",getExpectedStoriesToString(), getExpectedStoriesToStringSecond());
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
                repository.findTeamByName(VALID_TEAM_NAME_TWO),
                repository.findBoardByTeamName(VALID_BOARD_NAME,VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s",getExpectedStoriesToString(), getExpectedStoriesToStringSecond());
        // Act
        String actualOutput = listStoriesCommand.execute(List.of("sort", "size"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }



    private String getExpectedStoriesToString(){
        return String.format(EXPECTED_STORY_TO_STRING_FORMAT,
                3,
                VALID_TITLE,
                StatusType.NOT_DONE,
                VALID_PRIORITY_TYPE_STORY,
                VALID_SIZE_TYPE_STORY,
                VALID_MEMBER_NAME_TWO);
    }

    private String getExpectedStoriesToStringSecond(){
        return String.format(EXPECTED_STORY_TO_STRING_FORMAT,
                4,
                "mustbefirst",
                StatusType.NOT_DONE,
                PriorityType.MEDIUM,
                SizeType.MEDIUM,
                null);
    }
}
