package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.Constants;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.oop.taskmanagement.utils.Constants.EXPECTED_FEEDBACK_TO_STRING_FORMAT;
import static com.oop.taskmanagement.utils.Constants.EXPECTED_STORY_TO_STRING_FORMAT;

public class FeedbackImplTest {
    @Test
    public void FeedbackImpl_Should_ImplementFeedbackInterface() {
        // Arrange, Act
        FeedbackImpl feedback = ValidInitialization.initializeValidFeedback();

        // Assert
        Assertions.assertTrue(feedback instanceof Feedback);
    }

    @Test
    public void FeedbackImpl_Should_ImplementTaskBaseInterface() {
        // Arrange, Act
        TaskBase feedback = ValidInitialization.initializeValidFeedback();

        // Assert
        Assertions.assertTrue(feedback instanceof TaskBase);
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidNameProvided() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new FeedbackImpl(Constants.currentId,
                        "Invalid",
                        Constants.VALID_DESCRIPTION,
                        Constants.VALID_RATING));
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidDescriptionProvided() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new FeedbackImpl(Constants.currentId,
                        Constants.VALID_TITLE,
                        "Invalid",
                        Constants.VALID_RATING));
    }

    @Test
    public void constructor_Should_createNewFeedback_When_ArgumentsAreValid() {
        // Arrange, Act
        Feedback feedback = ValidInitialization.initializeValidFeedback();

        // Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(Constants.currentId, feedback.getId()),
                () -> Assertions.assertEquals(Constants.VALID_TITLE, feedback.getTitle()),
                () -> Assertions.assertEquals(Constants.VALID_DESCRIPTION, feedback.getDescription()),
                () -> Assertions.assertEquals(Constants.VALID_RATING, feedback.getRating()),
                () -> Assertions.assertEquals(StatusType.NEW, feedback.getStatus())
        );
    }

    @Test
    public void setAssignee_Should_AssignName() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        String expectedOutput = "Gosho";
        // Act
        feedback.setAssigneeName("Gosho");

        // Assert
        Assertions.assertEquals(expectedOutput, feedback.getAssigneeName());
    }

    @Test
    public void changeStatus_Should_ThrowException_When_InvalidStatusProvided() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> feedback.changeStatus(StatusType.IN_PROGRESS));
    }

    @Test
    public void changeStatus_Should_ChangeStatus_When_ValidStatusProvided() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();

        // Act
        feedback.changeStatus(StatusType.DONE);

        // Assert
        Assertions.assertEquals(StatusType.DONE, feedback.getStatus());
    }

    @Test
    public void changeStatus_Should_ThrowException_When_TryingToChangeToSameStatus() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> feedback.changeStatus(StatusType.NEW));
    }

    @Test
    public void changeStatus_Should_LogChange_When_ValidStatusProvided() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        String expectedOutput = String.format("Status changed from %s to %s.", StatusType.NEW, StatusType.DONE);
        // Act
        feedback.changeStatus(StatusType.DONE);

        // Assert
        Assertions.assertEquals(expectedOutput, feedback.getEventLog().get(0));
    }

    @Test
    public void changeRating_Should_ChangeRating() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        int expectedRating = 6;
        // Act
        feedback.changeRating(expectedRating);

        // Assert
        Assertions.assertEquals(expectedRating, feedback.getRating());
    }

    @Test
    public void changeRating_Should_ThrowException_SameRatingProvided() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();


        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> feedback.changeRating(Constants.VALID_RATING));
    }

    @Test
    public void changeRating_Should_LogChange() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        int newRating = 6;
        String expectedOutput = String.format("Rating changed from %d to %d.", Constants.VALID_RATING, newRating);

        // Act
        feedback.changeRating(6);

        // Assert
        Assertions.assertEquals(expectedOutput, feedback.getEventLog().get(0));
    }

    @Test
    public void toString_Should_ConvertToStringWithProperFormat() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        String expectedResult = String.format(EXPECTED_FEEDBACK_TO_STRING_FORMAT,
                Constants.currentId,
                Constants.VALID_TITLE,
                StatusType.NEW,
                Constants.VALID_RATING,
                null
        );
        // Act
        String actualOutput = feedback.toString();

        // Assert
        Assertions.assertEquals(expectedResult, actualOutput);
    }

    @Test
    public void addComment_Should_AddCommentInList() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        Comment comment = new CommentImpl("Pesho", "Long message");

        // Act
        feedback.addComment(comment);

        // Assert
        Assertions.assertEquals(1, feedback.getComments().size());
    }

    @Test
    public void addComment_Should_LogAdding() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        Comment comment = new CommentImpl("Pesho", "Long message");
        String expectedLog = "Comment added successfully";
        // Act
        feedback.addComment(comment);

        // Assert
        Assertions.assertEquals(expectedLog, feedback.getEventLog().get(0));
    }

    @Test
    public void getEventLog_Should_ReturnListOfLogs() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        feedback.addComment(new CommentImpl("Gosho", "Some message"));
        int newRating = 6;
        feedback.changeRating(newRating);
        List<String> expectedOutput = new ArrayList<>(
                Arrays.asList("Comment added successfully",
                        String.format("Rating changed from %d to %d.", Constants.VALID_RATING, newRating)));
        // Act
        List<String> actualOutput = feedback.getEventLog();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getComments_Should_ReturnListOfComments() {
        // Arrange
        Feedback feedback = ValidInitialization.initializeValidFeedback();
        Comment firstComment = new CommentImpl("Gosho", "Some message");
        Comment secondComment = new CommentImpl("Pesho", "Other message");
        feedback.addComment(firstComment);
        feedback.addComment(secondComment);
        List<Comment> expectedOutput = new ArrayList<>(Arrays.asList(firstComment, secondComment));

        // Act
        List<Comment> actualOutput = feedback.getComments();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}

