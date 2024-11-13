package com.oop.taskmanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class StartupTest {
    // this is purely troll test class, just to have 100% line/class coverage : D

    @Test
    void main_Should_NotThrow(){
        // Arrange
        String invalidInput = "\nGosho";
        System.setIn(new ByteArrayInputStream(invalidInput.getBytes()));
        // Act, Assert
        Assertions.assertDoesNotThrow(() -> Startup.main(new String[0]));
    }
}
