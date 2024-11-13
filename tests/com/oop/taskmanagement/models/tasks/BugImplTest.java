package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.Constants;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.oop.taskmanagement.utils.Constants.EXPECTED_BUG_TO_STRING_FORMAT;
import static com.oop.taskmanagement.utils.Constants.EXPECTED_FEEDBACK_TO_STRING_FORMAT;

public class BugImplTest {


    @Test
    public void BugImpl_Should_ImplementBugInterface(){
        // Arrange, Act
        BugImpl bug = ValidInitialization.initializeValidBug();

        // Assert
        Assertions.assertTrue(bug instanceof Bug);
    }

    @Test
    public void BugImpl_Should_ImplementTaskBaseInterface(){
        // Arrange, Act
        TaskBase bug = ValidInitialization.initializeValidBug();

        // Assert
        Assertions.assertTrue(bug instanceof TaskBase);
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidNameProvided(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new BugImpl(Constants.currentId,
                        "Invalid",
                        Constants.VALID_DESCRIPTION,
                        Constants.VALID_LIST_TO_REPRODUCE,
                        PriorityType.LOW,
                        SeverityType.MINOR));
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidDescriptionProvided(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new BugImpl(Constants.currentId,
                        Constants.VALID_TITLE,
                        "Invalid",
                        Constants.VALID_LIST_TO_REPRODUCE,
                        PriorityType.LOW,
                        SeverityType.MINOR));
    }

    @Test
    public void constructor_Should_createNewBug_When_ArgumentsAreValid() {
        // Arrange, Act
        Bug bug = ValidInitialization.initializeValidBug();

        // Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(Constants.currentId, bug.getId()),
                () -> Assertions.assertEquals(Constants.VALID_TITLE, bug.getTitle()),
                () -> Assertions.assertEquals(Constants.VALID_DESCRIPTION, bug.getDescription()),
                () -> Assertions.assertEquals(Constants.VALID_LIST_TO_REPRODUCE, bug.getStepsToReproduce()),
                () -> Assertions.assertEquals(PriorityType.LOW, bug.getPriority()),
                () -> Assertions.assertEquals(SeverityType.MINOR, bug.getSeverity()),
                () -> Assertions.assertEquals(StatusType.ACTIVE, bug.getStatus())
        );
    }

    @Test
    public void setAssignee_Should_AssignName(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        String expectedOutput = "Gosho";
        // Act
        bug.setAssigneeName("Gosho");

        // Assert
        Assertions.assertEquals(expectedOutput, bug.getAssigneeName());
    }
    @Test
    public void changeStatus_Should_ThrowException_When_InvalidStatusProvided(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> bug.changeStatus(StatusType.IN_PROGRESS));
    }

    @Test
    public void changeStatus_Should_ChangeStatus_When_ValidStatusProvided(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();

        // Act
        bug.changeStatus(StatusType.DONE);

        // Assert
        Assertions.assertEquals(StatusType.DONE, bug.getStatus());
    }

    @Test
    public void changeStatus_Should_ThrowException_When_TryingToChangeToTheSameStatus(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> bug.changeStatus(StatusType.ACTIVE));
    }

    @Test
    public void changeStatus_Should_LogChange_When_ValidStatusProvided(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        String expectedOutput = String.format("Status changed from %s to %s.", StatusType.ACTIVE, StatusType.DONE);
        // Act
        bug.changeStatus(StatusType.DONE);

        // Assert
        Assertions.assertEquals(expectedOutput, bug.getEventLog().get(0));
    }

    @Test
    public void changeSeverity_Should_ChangeSeverity(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();

        // Act
        bug.changeSeverity(SeverityType.MAJOR);

        // Assert
        Assertions.assertEquals(SeverityType.MAJOR, bug.getSeverity());
    }

    @Test
    public void changeSeverity_Should_ThrowException_TryingToChangeToTheSameSeverity(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> bug.changeSeverity(SeverityType.MINOR));
    }

    @Test
    public void changeSeverity_Should_LogChange_When_ValidSeverityProvided(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        String expectedOutput = String.format("Severity changed from %s to %s.", SeverityType.MINOR, SeverityType.MAJOR);
        // Act
        bug.changeSeverity(SeverityType.MAJOR);

        // Assert
        Assertions.assertEquals(expectedOutput, bug.getEventLog().get(0));
    }

    @Test
    public void changePriority_Should_ChangePriority(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();

        // Act
        bug.changePriority(PriorityType.HIGH);

        // Assert
        Assertions.assertEquals(PriorityType.HIGH, bug.getPriority());
    }

    @Test
    public void changePriority_Should_ThrowException_TryingToChangeToTheSamePriority(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> bug.changePriority(PriorityType.LOW));
    }

    @Test
    public void changePriority_Should_LogChange_When_ValidPriorityProvided(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        String expectedOutput = String.format("Priority changed from %s to %s.", PriorityType.LOW, PriorityType.HIGH);
        // Act
        bug.changePriority(PriorityType.HIGH);

        // Assert
        Assertions.assertEquals(expectedOutput, bug.getEventLog().get(0));
    }

    @Test
    public void toString_Should_ConvertToStringWithProperFormat(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        String expectedResult = String.format(EXPECTED_BUG_TO_STRING_FORMAT,
                Constants.currentId,
                Constants.VALID_TITLE,
                StatusType.ACTIVE,
                PriorityType.LOW,
                SeverityType.MINOR,
                "None"
                );
        // Act
        String actualOutput = bug.toString();

        // Assert
        Assertions.assertEquals(expectedResult,actualOutput);
    }

    @Test
    public void addComment_Should_AddCommentInList(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        Comment comment = new CommentImpl("Pesho","Long message");

        // Act
        bug.addComment(comment);

        // Assert
        Assertions.assertEquals(1,bug.getComments().size());
    }

    @Test
    public void addComment_Should_LogAdding(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        Comment comment = new CommentImpl("Pesho","Long message");
        String expectedLog = "Comment added successfully.";
        // Act
        bug.addComment(comment);

        // Assert
        Assertions.assertEquals(expectedLog,bug.getEventLog().get(0));
    }
    @Test
    public void getEventLog_Should_ReturnListOfLogs(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        bug.addComment(new CommentImpl("Gosho","Some message"));
        bug.changePriority(PriorityType.HIGH);
        List<String> expectedOutput = new ArrayList<>(
                Arrays.asList("Comment added successfully.",
                        String.format("Priority changed from %s to %s.", PriorityType.LOW, PriorityType.HIGH)));
        // Act
        List<String> actualOutput = bug.getEventLog();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getComments_Should_ReturnListOfComments(){
        // Arrange
        Bug bug = ValidInitialization.initializeValidBug();
        Comment firstComment = new CommentImpl("Gosho","Some message");
        Comment secondComment = new CommentImpl("Pesho", "Other message");
        bug.addComment(firstComment);
        bug.addComment(secondComment);
        List<Comment> expectedOutput = new ArrayList<>(Arrays.asList(firstComment,secondComment));

        // Act
        List<Comment> actualOutput = bug.getComments();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

}
