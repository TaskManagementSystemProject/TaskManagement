package com.oop.taskmanagement.commands.changing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedbackRatingCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String FEEDBACK_ID_PARSING_ERROR = "Bug ID must be a number!";
    private static final String FEEDBACK_RATING_PARSING_ERROR = "Rating must be a number!";
    private static final String FEEDBACK_RATING_CHANGE_SUCCESS_MESSAGE = "Feedback with ID %d rating changed to %d successfully.";

    private final TaskManagementRepository taskManagementRepository;

    public ChangeFeedbackRatingCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int feedbackId = ParsingHelpers.tryParseInt(parameters.get(0), FEEDBACK_ID_PARSING_ERROR);
        int newRating = ParsingHelpers.tryParseInt(parameters.get(1), FEEDBACK_RATING_PARSING_ERROR);

        Feedback toModify = taskManagementRepository.findFeedbackById(feedbackId);
        toModify.changeRating(newRating);

        return String.format(FEEDBACK_RATING_CHANGE_SUCCESS_MESSAGE, feedbackId, newRating);
    }
}
