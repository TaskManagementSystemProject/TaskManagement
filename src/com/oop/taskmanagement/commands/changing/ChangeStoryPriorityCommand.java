package com.oop.taskmanagement.commands.changing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeStoryPriorityCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final static String STORY_PRIORITY_CHANGED_SUCCESSFULLY = "Story with ID %d priority changed to %s successfully.";
    private static final String TASK_ID_PARSING_ERROR = "Task ID must be a number!";

    private final TaskManagementRepository taskManagementRepository;

    public ChangeStoryPriorityCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int storyID = ParsingHelpers.tryParseInt(parameters.get(0), TASK_ID_PARSING_ERROR);
        PriorityType newPriorityType = ParsingHelpers.tryParseEnum(parameters.get(1), PriorityType.class);

        Story storyToChangePriority = taskManagementRepository.findStoryById(storyID);
        storyToChangePriority.changePriority(newPriorityType);

        return String.format(STORY_PRIORITY_CHANGED_SUCCESSFULLY,storyID,newPriorityType);

    }
}
