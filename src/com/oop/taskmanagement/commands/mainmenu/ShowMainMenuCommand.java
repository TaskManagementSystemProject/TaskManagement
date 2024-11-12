package com.oop.taskmanagement.commands.mainmenu;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.enums.CommandType;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;

import static com.oop.taskmanagement.core.TaskManagementEngineImpl.REPORT_SEPARATOR;


public class ShowMainMenuCommand implements Command {

    private static final int MAX_COMMAND_LENGTH = Arrays.stream(CommandType.values())
            .map(Enum::toString)
            .mapToInt(String::length)
            .max()
            .orElse(0);

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters,0);
        return printMainMenu();

    }

    private String printMainMenu(){
        StringBuilder stringBuilder = new StringBuilder(REPORT_SEPARATOR).append(System.lineSeparator());
        stringBuilder.append(printCommandsMenuMessage());
        int index = 1;
        for (CommandType cmt : CommandType.values()) {
            stringBuilder.append(System.lineSeparator()).append(String.format("# %2d. %-" + MAX_COMMAND_LENGTH + "s #", index++, cmt));
        }
        return stringBuilder.toString();
    }

    private String printCommandsMenuMessage() {
        String avCommand = "Commands Menu";
        int width = REPORT_SEPARATOR.length() - 2; // this is for the # we are adding
        int leftPadding = (width - avCommand.length()) / 2;
        int rightPadding = width - avCommand.length() - leftPadding;
        String format = "#%" + (leftPadding + avCommand.length()) + "s%" + rightPadding + "s#";
        return String.format(format, avCommand, "");
    }

}
