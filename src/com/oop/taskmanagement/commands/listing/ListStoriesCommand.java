package com.oop.taskmanagement.commands.listing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.List;

public class ListStoriesCommand implements Command {

    private final TaskManagementRepository taskManagementRepository;

    public ListStoriesCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        // TODO
        return "";
    }
}
