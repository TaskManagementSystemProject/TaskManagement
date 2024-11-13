package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowTaskHistoryCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String NO_ACTIVITY_MESSAGE = "There is no activity in task %d yet.";
    private static final String TASK_ID_PARSING_ERROR = "Task ID must be a number!";
    private static final String TASK_ACTIVITY_PREFIX_MESSAGE = "LOG HISTORY for task %d:%n%s";
    private final TaskManagementRepository taskManagementRepository;

    public ShowTaskHistoryCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int taskId = ParsingHelpers.tryParseInt(parameters.get(0), TASK_ID_PARSING_ERROR);

        TaskBase taskBase = taskManagementRepository.findTaskById(taskId);

        String toReturnMessage = String.join(System.lineSeparator(), taskBase.getEventLog());
        return toReturnMessage.isEmpty() ?
                String.format(NO_ACTIVITY_MESSAGE, taskId) :
                String.format(TASK_ACTIVITY_PREFIX_MESSAGE,
                        taskId,
                        toReturnMessage);
    }
}
