package com.oop.taskmanagement.commands.adding;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Comment;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.tasks.CommentImpl;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class AddCommentToTaskCommand implements Command {
    private final static String COMMENT_ADDED_SUCCESSFULLY = "COMMENT%n%s>> was added to task %d successfully.";
    private static final String TASK_ID_PARSING_ERROR = "Task ID must be a number!";
    private static final String MEMBER_LOG_MESSAGE = "Added a comment about Task %d.";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private final TaskManagementRepository taskManagementRepository;

    public AddCommentToTaskCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int taskId = ParsingHelpers.tryParseInt(parameters.get(0), TASK_ID_PARSING_ERROR);
        String commentatorName = parameters.get(1);
        String commentContent = parameters.get(2);
        TaskBase task = taskManagementRepository.findTaskById(taskId);
        Member commentOwner = taskManagementRepository.findMemberByName(commentatorName);
        Comment comment = new CommentImpl(commentatorName, commentContent);
        task.addComment(comment);
        commentOwner.logActivity(String.format(MEMBER_LOG_MESSAGE, taskId));

        return String.format(COMMENT_ADDED_SUCCESSFULLY, comment, taskId);
    }
}
