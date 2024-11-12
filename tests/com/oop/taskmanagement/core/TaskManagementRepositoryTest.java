package com.oop.taskmanagement.core;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class TaskManagementRepositoryTest {
    TaskManagementRepository repository;

    @BeforeEach
    public void beforeEach() {
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

    // TODO Gosho add tests here


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
    public void getTasks_Should_ReturnShallowCopyOfListWithAllTasks(){
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
    public void findMemberByName_Should_ReturnExpectedMember_When_MemberFound(){
        // Arrange
        String expectedMemberName = "Gosho";

        // Act
        String actualMemberName = repository.findMemberByName("Gosho").getName();

        // Assert
        Assertions.assertEquals(expectedMemberName, actualMemberName);
    }

    @Test
    public void findMemberByName_Should_ThrowException_When_MemberNotFound(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findMemberByName("NOTFOUND"));
    }

    @Test
    public void findTeamByName_Should_ReturnExpectedTeam_When_TeamFound(){
        // Arrange
        String expectedTeamName = "Otbor";

        // Act
        String actualTeamName = repository.findTeamByName("Otbor").getName();

        // Assert
        Assertions.assertEquals(expectedTeamName, actualTeamName);
    }

    @Test
    public void findTeamByName_Should_ThrowException_When_TeamNotFound(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findTeamByName("NOTFOUND"));
    }

    @Test
    public void findBoardByTeamName_Should_ReturnExpectedMember_When_BoardFound(){
        // Arrange
        String expectedBoardName = "White";

        // Act
        String actualBoardName = repository.findBoardByTeamName("White", "Otbor").getName();

        // Assert
        Assertions.assertEquals(expectedBoardName, actualBoardName);
    }
    @Test
    public void findBoardByTeamName_Should_ThrowException_When_TeamNotFound(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> repository.findBoardByTeamName("White", "NOTFOUND"));
    }

    @Test
    public void findBoardByTeamName_Should_ThrowException_When_BoardNotFound(){
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



}
