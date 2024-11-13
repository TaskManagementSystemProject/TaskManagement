package com.oop.taskmanagement.models.tasks;

import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.utils.ValidationHelpers;

public class CommentImpl implements Comment {

    private static final int AUTHOR_MIN_LENGTH = 5;
    private static final int AUTHOR_MAX_LENGTH = 15;
    private static final String AUTHOR_LENGTH_ERROR = "Comment name must be between 3 and 15 symbols!";
    private static final int MESSAGE_MIN_LENGTH = 5;
    private static final int MESSAGE_MAX_LENGTH = 500;
    private static final String MESSAGE_LENGTH_ERROR = "Comment message must be between 5 and 500 symbols.";
    private static final String TO_STRING_FORMAT = "Author: %s%nContent: %s%n";
    private String author;
    private String message;

    public CommentImpl(String author, String message) {
        setAuthor(author);
        setMessage(message);
    }

    @Override
    public String getAuthor() {
        return author;
    }

    private void setAuthor(String author) {
        ValidationHelpers.validateIntRange(author.length(), AUTHOR_MIN_LENGTH, AUTHOR_MAX_LENGTH, AUTHOR_LENGTH_ERROR);
        this.author = author;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        ValidationHelpers.validateIntRange(message.length(), MESSAGE_MIN_LENGTH, MESSAGE_MAX_LENGTH, MESSAGE_LENGTH_ERROR);
        this.message = message;
    }

    @Override
    public String toString(){
        return String.format(TO_STRING_FORMAT,getAuthor(),getMessage());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Comment comment = (Comment) obj;
        return comment.getAuthor().equals(this.author) && comment.getMessage().equals(this.message);
    }
}
