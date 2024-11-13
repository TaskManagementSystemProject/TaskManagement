package com.oop.taskmanagement.core;

import com.oop.taskmanagement.core.contracts.TaskManagementEngine;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TaskManagementEngineTest {
    private TaskManagementEngine taskManagementEngine;

    @BeforeEach
    public void initialize(){
        taskManagementEngine = new TaskManagementEngineImpl();
    }
    @Test
    public void start_Should_MatchFirstSampleOutput_When_FirstSampleInputProvided() throws IOException {
        // Arrange
        File inputFile = new File("resources/SampleCase1/firstSampleInput.txt");
        System.setIn(new FileInputStream(inputFile)); // redirects the System.in to read from file rather than the console

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // redirect System.out to outputStream
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        String expectedOutput = Files.readString(Paths.get("resources/SampleCase1/firstSampleOutput.txt")); // get the expected output from the file

        // Act
        taskManagementEngine.start();
        String actualOutput = outputStream.toString();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void start_Should_MatchSecondSampleOutput_When_SecondSampleInputProvided() throws IOException {
        // Arrange
        File inputFile = new File("resources/SampleCase2/secondSampleInput.txt");
        System.setIn(new FileInputStream(inputFile)); // redirects the System.in to read from file rather than the console

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // redirect System.out to outputStream
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        String expectedOutput = Files.readString(Paths.get("resources/SampleCase2/secondSampleOutput.txt")); // get the expected output from the file

        // Act
        taskManagementEngine.start();
        String actualOutput = outputStream.toString();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void start_Should_MatchThirdSampleOutput_When_ThirdSampleInputProvided() throws IOException {
        // Arrange
        File inputFile = new File("resources/SampleCase3/thirdSampleInput.txt");
        System.setIn(new FileInputStream(inputFile)); // redirects the System.in to read from file rather than the console

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // redirect System.out to outputStream
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        String expectedOutput = Files.readString(Paths.get("resources/SampleCase3/thirdSampleOutput.txt")); // get the expected output from the file

        // Act
        taskManagementEngine.start();
        String actualOutput = outputStream.toString();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void start_Should_NotThrowException_When_InvalidInput(){
        // Arrange
        String invalidInput = "asdfasjfgajsfkaskd";
        System.setIn(new ByteArrayInputStream(invalidInput.getBytes()));

        //  Act, Assert
        Assertions.assertDoesNotThrow(() -> taskManagementEngine.start());
    }

    @Test
    public void start_Should_NotThrowException_When_InvalidInputBlank(){
        // Arrange
        String invalidInput = " ";
        System.setIn(new ByteArrayInputStream(invalidInput.getBytes()));

        //  Act, Assert
        Assertions.assertDoesNotThrow(() -> taskManagementEngine.start());
    }
}
