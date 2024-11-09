package com.oop.taskmanagement.commands.changing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeBugPriorityCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String BUG_ID_PARSING_ERROR = "Bug ID must be a number!";
    private static final String BUG_PRIORITY_CHANGE_SUCCESS_MESSAGE = "Bug with ID %d priority changed to %s successfully.";

    private final TaskManagementRepository taskManagementRepository;

    public ChangeBugPriorityCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int bugId = ParsingHelpers.tryParseInt(parameters.get(0), BUG_ID_PARSING_ERROR);
        PriorityType newPriority = ParsingHelpers.tryParseEnum(parameters.get(1), PriorityType.class);

        Bug toModify = taskManagementRepository.findBugById(bugId);
        toModify.changePriority(newPriority);

        return String.format(BUG_PRIORITY_CHANGE_SUCCESS_MESSAGE, bugId, newPriority);
    }
}
