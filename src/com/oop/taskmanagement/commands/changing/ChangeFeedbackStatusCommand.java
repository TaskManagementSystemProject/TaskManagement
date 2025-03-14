package com.oop.taskmanagement.commands.changing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedbackStatusCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final static String FEEDBACK_CHANGED_SUCCESSFULLY = "Feedback with ID %d status changed to %s successfully.";
    private static final String TASK_ID_PARSING_ERROR = "Task ID must be a number!";

    private final TaskManagementRepository taskManagementRepository;

    public ChangeFeedbackStatusCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int feedbackId = ParsingHelpers.tryParseInt(parameters.get(0), TASK_ID_PARSING_ERROR);
        StatusType newStatus = ParsingHelpers.tryParseEnum(parameters.get(1), StatusType.class);

        Feedback feedbackToChangeStatus = taskManagementRepository.findFeedbackById(feedbackId);
        feedbackToChangeStatus.changeStatus(newStatus);

        return String.format(FEEDBACK_CHANGED_SUCCESSFULLY,feedbackId,newStatus);
    }
}
