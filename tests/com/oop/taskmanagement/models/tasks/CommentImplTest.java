package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.oop.taskmanagement.utils.Constants.*;

public class CommentImplTest {


    @Test
    public void CommentImpl_Should_ImplementCommentInterface() {
        // Arrange, Act
        CommentImpl comment = ValidInitialization.initializeValidComment();

        // Assert
        Assertions.assertTrue(comment instanceof Comment);
    }


    @Test
    public void constructor_Should_ThrowException_When_AuthorNameToShort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new CommentImpl(INVALID_AUTHOR_SHORT_NAME, VALID_COMMENT_MESSAGE));
    }

    @Test
    public void constructor_Should_ThrowException_When_AuthorNameToLong() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new CommentImpl(INVALID_AUTHOR_LONG_NAME, VALID_COMMENT_MESSAGE));
    }


    @Test
    public void constructor_Should_ThrowException_When_MessageToShort() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new CommentImpl(VALID_COMMENT_AUTHOR, INVALID_COMMENT_SHORT_MESSAGE));
    }

    @Test
    public void constructor_Should_ThrowException_When_MessageToLong() {
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () ->
                new CommentImpl(VALID_COMMENT_AUTHOR, INVALID_COMMENT_LONG_MESSAGE));
    }


    @Test
    public void getAuthor_Should_returnAuthor_When_ArgumentsAreValid() {
        // Arrange
        Comment comment = new CommentImpl("JohnSmith", "This comment is valid");

        // Act, Assert
        Assertions.assertEquals("JohnSmith", comment.getAuthor());
    }

    @Test
    public void getMessage_Should_returnMessage_When_ArgumentsAreValid() {
        // Arrange
        Comment comment = new CommentImpl("JohnSmith", "This comment is valid");

        // Act, Assert
        Assertions.assertEquals("This comment is valid", comment.getMessage());
    }


    @Test
    public void toString_Should_returnComment_When_ArgumentsAreValid() {
        // Arrange
        Comment comment = new CommentImpl("JohnSmith", "This comment is valid");
        String output = String.format(
                "Author: JohnSmith%n" +
                        "Comment: This comment is valid%n");

        // Act, Assert
        Assertions.assertEquals(output, comment.toString());
    }

    @Test
    public void equals_Should_returnTrue_When_CommentsAreIdentical() {
        // Arrange
        Comment comment1 = ValidInitialization.initializeValidComment();
        Comment comment2 = ValidInitialization.initializeValidComment();

        // Act, Assert
        Assertions.assertTrue(comment1.equals(comment2));
    }
}
