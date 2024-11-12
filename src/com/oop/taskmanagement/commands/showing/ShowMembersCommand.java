package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ShowMembersCommand implements Command {

    private final TaskManagementRepository taskManagementRepository;

    public ShowMembersCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        return taskManagementRepository.getMembers()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
