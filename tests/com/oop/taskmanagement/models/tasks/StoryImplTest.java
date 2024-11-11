package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.Constants;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.oop.taskmanagement.utils.Constants.VALID_DESCRIPTION;

public class StoryImplTest {

    @Test
    public void StoryImpl_Should_ImplementStoryInterface(){
        // Arrange, Act
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Assert
        Assertions.assertTrue(story instanceof Story);
    }

    @Test
    public void StoryImpl_Should_ImplementTaskBaseInterface(){
        // Arrange, Act
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Assert
        Assertions.assertTrue(story instanceof TaskBase);
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidNameProvided(){
        // Arrange, Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(Constants.currentId,
                        "Invalid",
                        VALID_DESCRIPTION,
                        PriorityType.LOW,
                        SizeType.SMALL));
    }

    @Test
    public void constructor_Should_ThrowException_When_InvalidDescriptionProvided(){
        // Arrange, Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(Constants.currentId,
                        Constants.VALID_TITLE,
                        "Invalid",
                        PriorityType.LOW,
                        SizeType.SMALL));
    }

    @Test
    public void constructor_Should_createNewStory_When_ArgumentsAreValid() {
        // Arrange, Act
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(Constants.currentId, story.getId()),
                () -> Assertions.assertEquals(Constants.VALID_TITLE, story.getTitle()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, story.getDescription()),
                () -> Assertions.assertEquals(PriorityType.LOW, story.getPriority()),
                () -> Assertions.assertEquals(SizeType.SMALL, story.getSize()),
                () -> Assertions.assertEquals(StatusType.NOT_DONE, story.getStatus())
        );
    }

    @Test
    public void setAssignee_Should_AssignName(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        String expectedOutput = "Gosho";
        // Act
        story.setAssigneeName("Gosho");

        // Assert
        Assertions.assertEquals(expectedOutput, story.getAssigneeName());
    }
    @Test
    public void changeStatus_Should_ThrowException_When_InvalidStatusProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changeStatus(StatusType.NEW));
    }

    @Test
    public void changeStatus_Should_ThrowException_When_SameStatusProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changeStatus(StatusType.NOT_DONE));
    }

    @Test
    public void changeStatus_Should_ChangeStatus_When_ValidStatusProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Act
        story.changeStatus(StatusType.DONE);

        // Assert
        Assertions.assertEquals(StatusType.DONE, story.getStatus());
    }

    @Test
    public void changeStatus_Should_LogChange_When_ValidStatusProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        String expectedOutput = String.format("Status changed from %s to %s.", StatusType.NOT_DONE, StatusType.DONE);
        // Act
        story.changeStatus(StatusType.DONE);

        // Assert
        Assertions.assertEquals(expectedOutput, story.getEventLog().get(0));
    }

    @Test
    public void changeSize_Should_ChangeSize(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Act
        story.changeSize(SizeType.LARGE);

        // Assert
        Assertions.assertEquals(SizeType.LARGE, story.getSize());
    }

    @Test
    public void changeSize_Should_ThrowException_When_SameSizeProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changeSize(SizeType.SMALL));
    }

    @Test
    public void changeSize_Should_LogChange_When_ValidSizeProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        String expectedOutput = String.format("Size changed from %s to %s.", SizeType.SMALL, SizeType.LARGE);
        // Act
        story.changeSize(SizeType.LARGE);

        // Assert
        Assertions.assertEquals(expectedOutput, story.getEventLog().get(0));
    }

    @Test
    public void changePriority_Should_ChangePriority(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Act
        story.changePriority(PriorityType.HIGH);

        // Assert
        Assertions.assertEquals(PriorityType.HIGH, story.getPriority());
    }

    @Test
    public void changePriority_Should_ThrowException_When_SamePriorityProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changePriority(PriorityType.LOW));
    }

    @Test
    public void changePriority_Should_LogChange_When_ValidPriorityProvided(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        String expectedOutput = String.format("Priority changed from %s to %s.", PriorityType.LOW, PriorityType.HIGH);
        // Act
        story.changePriority(PriorityType.HIGH);

        // Assert
        Assertions.assertEquals(expectedOutput, story.getEventLog().get(0));
    }

    @Test
    public void toString_Should_ConvertToStringWithProperFormat(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        String expectedResult = String.format("Task with id: %d%nTitle: %s%nStatus: %s%n" +
                        "Priority: %s%nSize: %s%nAssigned to: %s",
                Constants.currentId,
                Constants.VALID_TITLE,
                StatusType.NOT_DONE,
                PriorityType.LOW,
                SizeType.SMALL,
                null
        );
        // Act
        String actualOutput = story.toString();

        // Assert
        Assertions.assertEquals(expectedResult,actualOutput);
    }

    @Test
    public void addComment_Should_AddCommentInList(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        Comment comment = new CommentImpl("Pesho","Long message");

        // Act
        story.addComment(comment);

        // Assert
        Assertions.assertEquals(1,story.getComments().size());
    }

    @Test
    public void addComment_Should_LogAdding(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        Comment comment = new CommentImpl("Pesho","Long message");
        String expectedLog = "Comment added successfully";
        // Act
        story.addComment(comment);

        // Assert
        Assertions.assertEquals(expectedLog,story.getEventLog().get(0));
    }
    @Test
    public void getEventLog_Should_ReturnListOfLogs(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        story.addComment(new CommentImpl("Gosho","Some message"));
        story.changePriority(PriorityType.HIGH);
        List<String> expectedOutput = new ArrayList<>(
                Arrays.asList("Comment added successfully",
                        String.format("Priority changed from %s to %s.", PriorityType.LOW, PriorityType.HIGH)));
        // Act
        List<String> actualOutput = story.getEventLog();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getComments_Should_ReturnListOfComments(){
        // Arrange
        StoryImpl story = ValidInitialization.initializeValidStory();
        Comment firstComment = new CommentImpl("Gosho","Some message");
        Comment secondComment = new CommentImpl("Pesho", "Other message");
        story.addComment(firstComment);
        story.addComment(secondComment);
        List<Comment> expectedOutput = new ArrayList<>(Arrays.asList(firstComment,secondComment));

        // Act
        List<Comment> actualOutput = story.getComments();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }


}
