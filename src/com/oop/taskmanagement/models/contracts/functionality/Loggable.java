package com.oop.taskmanagement.models.contracts.functionality;

import java.util.List;

public interface Loggable {
    List<String> getActivityHistory();
    void logActivity(String activityLog);
}
