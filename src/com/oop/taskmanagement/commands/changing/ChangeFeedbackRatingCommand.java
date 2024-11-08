package com.oop.taskmanagement.commands.changing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.List;

public class ChangeFeedbackRatingCommand implements Command {

    private final TaskManagementRepository taskManagementRepository;

    public ChangeFeedbackRatingCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        // TODO
        return "";
    }
}
