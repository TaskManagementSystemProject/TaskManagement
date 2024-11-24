package com.oop.taskmanagement.models.contracts.tasks;

import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;


import java.util.List;

public interface Bug extends TaskBase {

    void changeSeverity(SeverityType severity);

    void changePriority(PriorityType priority);

    List<String> getStepsToReproduce();

    PriorityType getPriority();

    SeverityType getSeverity();



}
