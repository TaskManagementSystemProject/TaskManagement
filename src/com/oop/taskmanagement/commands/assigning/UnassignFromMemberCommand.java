package com.oop.taskmanagement.commands.assigning;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.List;

public class UnassignFromMemberCommand implements Command {

    private final TaskManagementRepository taskManagementRepository;

    public UnassignFromMemberCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        // TODO
        return "";
    }
}
