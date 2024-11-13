package com.oop.taskmanagement.core;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.oop.taskmanagement.utils.Constants.*;

public class TaskManagementRepositoryTest {
    private TaskManagementRepository repository;

    @BeforeEach
    public void initialize() {
        repository = ValidInitialization.createDummyRepository();
    }

    @Test
    public void constructor_Should_InitializeAllCollections() {
        // Arrange, Act, Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(repository.getTeams()),
                () -> Assertions.assertNotNull(repository.getMembers()),
                () -> Assertions.assertNotNull(repository.getBugs()),
                () -> Assertions.assertNotNull(repository.getStories()),
                () -> Assertions.assertNotNull(repository.getFeedbacks())
        );
    }

    @Test
    public void getFeedbacks_Should_ReturnShallowCopyOfCollection() {
        // Arrange
        List<Feedback> categoriesReference = repository.getFeedbacks();

        // Act
        List<Feedback> sameReference = repository.getFeedbacks();

        // Assert
        Assertions.assertNotSame(categoriesReference, sameReference);
    }

    @Test
    public void getTasks_Should_ReturnShallowCopyOfListWithAllTasks() {
        // Arrange
        List<TaskBase> expectedList = new ArrayList<>(repository.getBugs());
        expectedList.addAll(repository.getFeedbacks());
        expectedList.addAll(repository.getStories());

        // Act
        List<TaskBase> actualList = repository.getAllTasks();

        // Assert
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    public void findMemberByName_Should_ReturnExpectedMember_When_MemberFound() {
        // Arrange
        String expectedMemberName = "Gosho";

        // Act
        String actualMemberName = repository.findMemberByName("Gosho").getName();

        // Assert
        Assertions.assertEquals(expectedMemberName, actualMemberName);
    }

    @Test
    public void findMemberByName_Should_ThrowException_When_MemberNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findMemberByName("NOTFOUND"));
    }

    @Test
    public void findTeamByName_Should_ReturnExpectedTeam_When_TeamFound() {
        // Arrange
        String expectedTeamName = "Otbor";

        // Act
        String actualTeamName = repository.findTeamByName("Otbor").getName();

        // Assert
        Assertions.assertEquals(expectedTeamName, actualTeamName);
    }

    @Test
    public void findTeamByName_Should_ThrowException_When_TeamNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findTeamByName("NOTFOUND"));
    }

    @Test
    public void findBoardByTeamName_Should_ReturnExpectedMember_When_BoardFound() {
        // Arrange
        String expectedBoardName = "White";

        // Act
        String actualBoardName = repository.findBoardByTeamName("White", "Otbor").getName();

        // Assert
        Assertions.assertEquals(expectedBoardName, actualBoardName);
    }

    @Test
    public void findBoardByTeamName_Should_ThrowException_When_TeamNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findBoardByTeamName("White", "NOTFOUND"));
    }

    @Test
    public void findBoardByTeamName_Should_ThrowException_When_BoardNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findBoardByTeamName("NOTFOUND", "Otbor"));
    }

    @Test
    public void findMemberByTask_Should_ReturnMember_When_TaskHasAssignee() {
        // Arrange
        String expectedMemberName = "Gosho";

        // Act
        String actualMemberName = repository.findMemberByTask(repository.findTaskById(1)).getName();

        // Assert
        Assertions.assertEquals(expectedMemberName, actualMemberName);
    }

    @Test
    public void findMemberByTask_Should_ReturnNull_When_TaskNotAssignee() {
        // Arrange, Act, Assert
        Assertions.assertNull(repository.findMemberByTask(repository.findTaskById(2)));
    }

    @Test
    public void findTeamByMemberName_Should_ReturnTeam_When_MemberFound() {
        // Arrange
        String expectedTeamName = "Otbor";

        // Act
        String actualTeamName = repository.findTeamByMemberName("Gosho").getName();

        // Assert
        Assertions.assertEquals(expectedTeamName, actualTeamName);
    }

    @Test
    public void findTeamByMemberName_Should_ReturnNull_When_MemberNotFound() {
        // Arrange, Act, Assert
        Assertions.assertNull(repository.findTeamByMemberName("NOTFOUND"));
    }

    @Test
    public void findBugById_Should_ReturnBug_When_BugWithIdFound() {
        // Arrange
        Bug expectedBug = repository.getBugs().get(0);

        // Act
        Bug actualBug = repository.findBugById(2);

        // Assert
        Assertions.assertEquals(expectedBug, actualBug);
    }

    @Test
    public void findBugById_Should_ThrowException_When_BugWithIdNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findBugById(1));
    }

    @Test
    public void findStoryById_Should_ReturnStory_When_StoryWithIdFound() {
        // Arrange
        Story expectedStory = repository.getStories().get(0);

        // Act
        Story actualStory = repository.findStoryById(3);

        // Assert
        Assertions.assertEquals(expectedStory, actualStory);
    }

    @Test
    public void findStoryById_Should_ThrowException_When_StoryWithIdNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findStoryById(1));
    }

    @Test
    public void findFeedbackById_Should_ReturnFeedback_When_FeedbackWithIdFound() {
        // Arrange
        Feedback expectedFeedback = repository.getFeedbacks().get(0);

        // Act
        Feedback actualFeedback = repository.findFeedbackById(1);

        // Assert
        Assertions.assertEquals(expectedFeedback, actualFeedback);
    }

    @Test
    public void findFeedbackById_Should_ThrowException_When_FeedbackWithIdNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findFeedbackById(2));
    }

    @Test
    public void findTaskById_Should_ReturnTask_When_TaskWithIdFound() {
        // Arrange
        TaskBase expectedTask = repository.getAllTasks().get(0);

        // Act
        TaskBase actualTask = repository.findTaskById(2);

        // Assert
        Assertions.assertEquals(expectedTask, actualTask);
    }

    @Test
    public void findTaskById_Should_ThrowException_When_TaskWithIdNotFound() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findTaskById(5));
    }


    @Test
    public void createTeam_Should_ThrowException_When_TeamAlreadyExists() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.createTeam("Otbor"));
    }

    @Test
    public void createTeam_ShouldAddTeamToListOfTeams_When_ArgumentsAreValid() {
        // Arrange, Act
        int sizeOfListBeforeTeam = repository.getTeams().size();
        repository.createTeam("New team");
        int sizeOfListAfterNewTeam = repository.getTeams().size();

        // Assert
        Assertions.assertNotEquals(sizeOfListBeforeTeam, sizeOfListAfterNewTeam);
    }

    @Test
    public void createBoardInTeam_Should_ThrowException_When_TeamNameInvalid() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> repository.createBoardInTeam(
                        "Valid name", repository.findTeamByName("Invalid name")));
    }

    @Test
    public void createBoardInTeam_Should_ThrowException_When_BoardNameInvalid() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> repository.createBoardInTeam("White", repository.findTeamByName("Otbor")));
    }

    @Test
    public void createMember_Should_ThrowException_When_MemberNameNotUnique() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.createMember("Gosho"));
    }

    @Test
    public void createMember_ShouldAddMemberToListOfMembers_When_ArgumentsAreValid() {
        // Arrange, Act
        int sizeOfListBeforeMember = repository.getMembers().size();
        repository.createMember("New Member");
        int sizeOfListAfterNewMember = repository.getMembers().size();

        // Assert
        Assertions.assertNotEquals(sizeOfListAfterNewMember, sizeOfListBeforeMember);
    }

    @Test
    public void createBug_Should_ThrowException_When_TeamNameInvalid() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> repository.createBugInBoard(
                        VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_LIST_TO_REPRODUCE,
                        PriorityType.HIGH,
                        SeverityType.MAJOR,
                        repository.findTeamByName("Invalid team name"),
                        repository.findBoardByTeamName("White", "Invalid team name")));
    }

    @Test
    public void createBug_Should_ThrowException_When_BoardNameInvalid() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> repository.createBugInBoard(
                        VALID_TITLE,
                        VALID_DESCRIPTION,
                        VALID_LIST_TO_REPRODUCE,
                        PriorityType.HIGH,
                        SeverityType.MAJOR,
                        repository.findTeamByName("Otbor"),
                        repository.findBoardByTeamName("Invalid board name", "Otbor")));

    }


    @Test
    public void createBug_ShouldHaveUniqueID_When_ArgumentsAreValid() {
        // Arrange, Act
        int lastIDBeforeNewBug = repository.getAllTasks().getLast().getId();
        Bug bug = repository.createBugInBoard(
                "New bug title",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findTeamByName("Otbor"),
                repository.findBoardByTeamName("White", "Otbor"));
        int lastIDAfterNewBug = bug.getId();

        // Assert
        Assertions.assertNotEquals(lastIDAfterNewBug, lastIDBeforeNewBug);
    }

    @Test
    public void createBug_ShouldAddBugToListOfBugs_When_ArgumentsAreValid() {
        // Arrange, Act
        int sizeOfListBeforeNewBug = repository.getBugs().size();
        repository.createBugInBoard(
                "New bug title",
                VALID_DESCRIPTION,
                VALID_LIST_TO_REPRODUCE,
                PriorityType.HIGH,
                SeverityType.MAJOR,
                repository.findTeamByName("Otbor"),
                repository.findBoardByTeamName("White", "Otbor"));
        int sizeOfListAfterNewBug = repository.getBugs().size();

        // Assert
        Assertions.assertNotEquals(sizeOfListAfterNewBug, sizeOfListBeforeNewBug);
    }

    @Test
    public void createStory_ShouldHaveUniqueID_When_ArgumentsAreValid() {
        // Arrange, Act
        int lastIDBeforeNewStory = repository.getAllTasks().getLast().getId();
        Story story = repository.createStoryInBoard(
                "New story title",
                VALID_DESCRIPTION,
                PriorityType.HIGH,
                SizeType.SMALL,
                repository.findTeamByName("Otbor"),
                repository.findBoardByTeamName("White", "Otbor"));
        int lastIDAfterNewStory = story.getId();

        // Assert
        Assertions.assertNotEquals(lastIDAfterNewStory, lastIDBeforeNewStory);
    }

    @Test
    public void createStory_ShouldAddStoryToListOfStories_When_ArgumentsAreValid() {
        // Arrange, Act
        int sizeOfListBeforeNewStory = repository.getStories().size();
        repository.createStoryInBoard(
                "New story title",
                VALID_DESCRIPTION,
                PriorityType.HIGH,
                SizeType.SMALL,
                repository.findTeamByName("Otbor"),
                repository.findBoardByTeamName("White", "Otbor"));
        int sizeOfListAfterNewStory = repository.getStories().size();

        // Assert
        Assertions.assertNotEquals(sizeOfListAfterNewStory, sizeOfListBeforeNewStory);
    }

    @Test
    public void createFeedback_ShouldHaveUniqueID_When_ArgumentsAreValid() {
        // Arrange, Act
        int lastIDBeforeNewFeedback = repository.getAllTasks().getLast().getId();
        Feedback feedback = repository.createFeedbackInBoard(
                "New story title",
                VALID_DESCRIPTION,
                VALID_RATING,
                repository.findTeamByName("Otbor"),
                repository.findBoardByTeamName("White", "Otbor"));
        int lastIDAfterNewFeedback = feedback.getId();

        // Assert
        Assertions.assertNotEquals(lastIDAfterNewFeedback, lastIDBeforeNewFeedback);
    }

    @Test
    public void createFeedback_AddFeedbackToListOfFeedbacks_When_ArgumentsAreValid() {
        // Arrange, Act
        int sizeOfListBeforeNewFeedback = repository.getFeedbacks().size();
        repository.createFeedbackInBoard(
                "New story title",
                VALID_DESCRIPTION,
                VALID_RATING,
                repository.findTeamByName("Otbor"),
                repository.findBoardByTeamName("White", "Otbor"));
        int sizeOfListAfterNewFeedback = repository.getFeedbacks().size();

        // Assert
        Assertions.assertNotEquals(sizeOfListBeforeNewFeedback, sizeOfListAfterNewFeedback);
    }
}