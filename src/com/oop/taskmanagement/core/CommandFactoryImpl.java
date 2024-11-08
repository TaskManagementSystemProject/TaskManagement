package com.oop.taskmanagement.core;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.CommandFactory;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

public class CommandFactoryImpl implements CommandFactory {
    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TaskManagementRepository taskManagementRepository) {
        throw new RuntimeException("Not implemented yet");
    }
}
