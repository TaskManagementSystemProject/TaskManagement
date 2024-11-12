package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;


public class ListTasksCommandTest {
    private TaskManagementRepository repository;
    private Command listTasksCommand;


    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
        listTasksCommand = new ListTasksCommand(repository);
    }

    @Test
    public void execute_Should_ReturnAllTasksAsString_When_NoParametersPassed() {
        // Arrange
        String expectedOutput = String.format("%s%n%s%n%n%s%n%n%s",
                ALL_TASK_PREFIX_MESSAGE,
                BUG_TO_STRING_DUMMY_REPO,
                FEEDBACK_TO_STRING_DUMMY_REPO,
                STORY_TO_STRING_DUMMY_REPO);

        // Act
        String actualOutput = listTasksCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidListingType() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listTasksCommand.execute(List.of("InvalidListing")));
    }


    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsSort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listTasksCommand.execute(List.of("sort", "invalid")));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidCountOfArgumentsFilter() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> listTasksCommand.execute(List.of("filter")));
    }

    @Test
    public void execute_Should_ReturnTasksSortedByTitle_When_ParameterSortProvided() {
        // Arrange
        repository.createFeedbackInBoard("0000000000",
                "Random description",
                10,
                repository.findTeamByName(VALID_TEAM_NAME_TWO),
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s%n%s%n%s%n%s",
                ALL_TASK_PREFIX_MESSAGE,
                getDummyFeedback(),
                BUG_TO_STRING_DUMMY_REPO,
                FEEDBACK_TO_STRING_DUMMY_REPO,
                STORY_TO_STRING_DUMMY_REPO);

        // Act
        String actualOutput = listTasksCommand.execute(List.of("sort"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);

    }

    @Test
    public void execute_Should_ReturnTasksFilteredByTitle_When_ParameterFilterProvided() {
        // Arrange
        repository.createFeedbackInBoard("0000000000",
                "Random description",
                10,
                repository.findTeamByName(VALID_TEAM_NAME_TWO),
                repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO));

        String expectedOutput = String.format("%s%n%s", ALL_TASK_PREFIX_MESSAGE, getDummyFeedback());

        // Act
        String actualOutput = listTasksCommand.execute(List.of("filter", "0000000000"));

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);

    }

    private String getDummyFeedback() {
        return String.format(EXPECTED_FEEDBACK_TO_STRING_FORMAT,
                4,
                "0000000000",
                StatusType.NEW,
                10,
                null);
    }
}
