package com.oop.taskmanagement;

import com.oop.taskmanagement.core.TaskManagementEngineImpl;


public class Startup {
    public static void main(String[] args) {
        TaskManagementEngineImpl engine = new TaskManagementEngineImpl();
        engine.start();
    }
}
