package com.oop.taskmanagement.commands.changing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.enums.StatusType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeStoryStatusCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final static String STORY_STATUS_CHANGED_SUCCESSFULLY = "Story status changed successfully.";
    private static final String TASK_ID_PARSING_ERROR = "Task ID must be a number!";

    private final TaskManagementRepository taskManagementRepository;

    public ChangeStoryStatusCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int storyID = ParsingHelpers.tryParseInt(parameters.get(0), TASK_ID_PARSING_ERROR);
        StatusType newStatusType = ParsingHelpers.tryParseEnum(parameters.get(1), StatusType.class);

        Story storyToChangeStatus = taskManagementRepository.findStoryById(storyID);
        storyToChangeStatus.changeStatus(newStatusType);

        return String.format(STORY_STATUS_CHANGED_SUCCESSFULLY);
    }
}
