package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ShowTeamsCommand implements Command {

    private final TaskManagementRepository taskManagementRepository;

    public ShowTeamsCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        return taskManagementRepository.getTeams()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
