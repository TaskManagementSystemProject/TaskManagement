package com.oop.taskmanagement.core;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.CommandFactory;
import com.oop.taskmanagement.core.contracts.TaskManagementEngine;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManagementEngineImpl implements TaskManagementEngine {
    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND_ERROR = "Command cannot be empty.";
    private static final String MAIN_SPLIT_SYMBOL = " ";
    private static final String TEXT_OPEN_SYMBOL = "<<";
    private static final String TEXT_CLOSE_SYMBOL = ">>";
    private static final String REPORT_SEPARATOR = "####################";

    private final CommandFactory commandFactory;
    private final TaskManagementRepository taskManagementRepository;

    public TaskManagementEngineImpl() {
        this.commandFactory = new CommandFactoryImpl();
        this.taskManagementRepository = new TaskManagementRepositoryImpl();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    print(EMPTY_COMMAND_ERROR);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
            } catch (Exception ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    print(ex.getMessage());
                } else {
                    print(ex.toString());
                }
            }
        }
    }

    private void processCommand(String inputLine) {
        String commandName = extractCommandName(inputLine);
        List<String> parameters = extractCommandParameters(inputLine);
        Command command = commandFactory.createCommandFromCommandName(commandName, taskManagementRepository);
        String executionResult = command.execute(parameters);
        print(executionResult);
    }

    /**
     * Receives a full line and extracts the command to be executed from it.
     * For example, if the input line is "FilterBy Assignee John", the method will return "FilterBy".
     *
     * @param inputLine A complete input line
     * @return The name of the command to be executed
     */
    private String extractCommandName(String inputLine) {
        return inputLine.split(" ")[0];
    }

    /**
     * Receives a full line and extracts the parameters that are needed for the command to execute.
     * For example, if the input line is "FilterBy Assignee John",
     * the method will return a list of ["Assignee", "John"].
     *
     * @param inputLine A complete input line
     * @return A list of the parameters needed to execute the command
     */
    private List<String> extractCommandParameters(String inputLine) {
        if (inputLine.contains(TEXT_OPEN_SYMBOL)) {
            return extractTextParameters(inputLine);
        }
        String[] commandParts = inputLine.split(" ");
        List<String> parameters = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            parameters.add(commandParts[i]);
        }
        return parameters;
    }

    /**
     * Receives a full command in which there are fields that contain multiple words (a.k.a sentence) and rework those specific
     * parameters to a single String which is afterward passed as parameters for the command to execute.
     * For example, if the input like is CreateTask {{This is a specific task}} {{When I click there, that happens}} ..
     * the method will return a list of ["This is a specific task", "When I click there, that happens", ..]
     * @param fullCommand A complete command
     * @return A list of parameters needed to execute the command
     */
    public List<String> extractTextParameters(String fullCommand) {
        int indexOfFirstSeparator = fullCommand.indexOf(MAIN_SPLIT_SYMBOL);
        int indexOfOpenText = fullCommand.indexOf(TEXT_OPEN_SYMBOL);
        int indexOfCloseText = fullCommand.indexOf(TEXT_CLOSE_SYMBOL);;
        List<String> parameters = new ArrayList<>();

        // add the Title input text as a parameter // or in case of addingComment it takes just the comment
        fullCommand = getString(fullCommand, parameters, indexOfOpenText, indexOfCloseText);

        indexOfOpenText = fullCommand.indexOf(TEXT_OPEN_SYMBOL);
        indexOfCloseText = fullCommand.indexOf(TEXT_CLOSE_SYMBOL);;
        if (indexOfOpenText != - 1) {
            // if there are two times {{}} {{}} in the fullCommand -> gets both of the strings included
            // if there are more than 1 strings included, the case is when creating a task -> has title and description, we are taking the description
            // as well as a parameter
            fullCommand = getString(fullCommand, parameters, indexOfOpenText, indexOfCloseText);
        }

        List<String> result = new ArrayList<>(Arrays.asList(fullCommand.substring(indexOfFirstSeparator + 1).split(MAIN_SPLIT_SYMBOL)));
        parameters.addAll(result);
        return parameters;
    }

    // adds the parameter in the OPEN/CLOSE symbol as a single String and removes from the fullCommand String.
    private String getString(String fullCommand, List<String> parameters, int indexOfOpenText, int indexOfCloseText) {
        parameters.add(fullCommand.substring(indexOfOpenText + TEXT_OPEN_SYMBOL.length(), indexOfCloseText));
        fullCommand = fullCommand.replaceFirst("<<.+?>> ", "");
        return fullCommand;
    }

    private void print(String result) {
        System.out.println(result);
        System.out.println(REPORT_SEPARATOR);
    }

}
