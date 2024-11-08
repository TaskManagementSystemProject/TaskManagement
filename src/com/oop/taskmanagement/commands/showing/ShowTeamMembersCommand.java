package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.List;

public class ShowTeamMembersCommand implements Command {

    private final TaskManagementRepository taskManagementRepository;

    public ShowTeamMembersCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        // TODO
        return "";
    }
}
