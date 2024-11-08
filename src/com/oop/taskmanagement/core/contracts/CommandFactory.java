package com.oop.taskmanagement.core.contracts;

import com.oop.taskmanagement.commands.contracts.Command;

public interface CommandFactory {
    Command createCommandFromCommandName(String commandTypeAsString, TaskManagementRepository taskManagementRepository);
}
