package com.oop.taskmanagement.utils;

import com.oop.taskmanagement.exceptions.InvalidUserInputException;

public class ParsingHelpers {
    private static final String NO_SUCH_ENUM = "There is no %s in %ss.";


    public static int tryParseInt(String valueToParse, String errorMessage) {
        try {
            return Integer.parseInt(valueToParse);
        } catch (NumberFormatException e) {
            throw new InvalidUserInputException(errorMessage);
        }
    }


    public static <E extends Enum<E>> E tryParseEnum(String valueToParse, Class<E> type) {
        try {
            return Enum.valueOf(type, valueToParse.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidUserInputException(String.format(NO_SUCH_ENUM, valueToParse, type.getSimpleName()));
        }
    }
}
