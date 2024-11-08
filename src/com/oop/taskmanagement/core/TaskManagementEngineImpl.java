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
    private static final char MAIN_SPLIT_SYMBOL = ' ';
    private static final char SENTENCE_SEPARATOR = '^';
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
        List<String> allArguments = extractAllArguments(inputLine);
        String commandName = extractCommandName(allArguments);
        List<String> parameters = extractCommandParameters(allArguments);
        Command command = commandFactory.createCommandFromCommandName(commandName, taskManagementRepository);
        String executionResult = command.execute(parameters);
        print(executionResult);
    }

    /**
     * Receives a list of all parameters and extracts the command to be executed from it.
     * For example, if the input list is ["FilterBy", "Assignee", "John"] the method will return "FilterBy".
     *
     * @param allParameters A list of all parameters
     * @return The name of the command to be executed
     */
    private String extractCommandName(List<String> allParameters) {
        return allParameters.get(0);
    }

    /**
     * Receives a full list of arguments and extracts the parameters that are needed for the command to execute.
     * For example, if the input list is ["FilterBy", "Assignee", "John"]
     * the method will return a list of ["Assignee", "John"].
     *
     * @param inputLine A list of all parameters
     * @return A list of the parameters needed to execute the command
     */
    private List<String> extractCommandParameters(List<String> inputLine) {
        List<String> parameters = new ArrayList<>();
        for (int i = 1; i < inputLine.size(); i++) {
            parameters.add(inputLine.get(i));
        }
        return parameters;
    }


    private void print(String result) {
        System.out.println(result);
        System.out.println(REPORT_SEPARATOR);
    }

    /**
     * Receives a full line and extracts the parameters that are needed for the command to execute.
     * For example, if the input line is "FilterBy Assignee ^John Jones^",
     * the method will return a list of ["FilterBy", "Assignee", "John Jones"].
     *
     * @param lineToBeSplit A complete input line
     * @return A list of the parameters needed to execute the command
     */
    private List<String> extractAllArguments(String lineToBeSplit) {
        StringBuilder currentWord = new StringBuilder();
        boolean insideBracket = false;
        ArrayList<String> result = new ArrayList<>();

        for (char symbol : lineToBeSplit.toCharArray()) {
            // if insideBracket // dependable on SENTENCE_SEPARATOR, does not count space as a special symbol. Adds the element inside the result array
            // once we see the closing separator
            if (insideBracket) {
                if (symbol != SENTENCE_SEPARATOR) {
                    currentWord.append(symbol);
                    continue;
                }
                if (!currentWord.isEmpty()) {
                    result.add(currentWord.toString());
                    currentWord.setLength(0);
                }
                insideBracket = false;
            } else {
                // if we are outside of the bracket ( text that has to be considered as whole ) we are dealing with either a single word parameter or text
                // that we handle by catching the different scenarios// if its SENTENCE_SEPARATOR it means we are going inside brackets
                // if its a space we are adding the word only if the currentWord is not empty -> handles the case where multiple spaces are used
                // or if there is a single word parameter after another single word parameter.
                switch (symbol) {
                    case SENTENCE_SEPARATOR:
                        insideBracket = true;
                        break;
                    case MAIN_SPLIT_SYMBOL:
                        if (!currentWord.isEmpty()) {
                            result.add(currentWord.toString());
                            currentWord.setLength(0);
                        }
                        break;
                    default:
                        currentWord.append(symbol);
                }
            }
        }
        // in case we end with a single word parameter, add to the list;
        if (!currentWord.isEmpty()) {
            result.add(currentWord.toString());
        }
        return result;
    }
}
