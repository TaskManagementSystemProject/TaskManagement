package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.List;

public class ShowTeamBoardsCommand implements Command {

    private final TaskManagementRepository taskManagementRepository;

    public ShowTeamBoardsCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }
    @Override
    public String execute(List<String> parameters) {
        // TODO
        return "";
    }
}
