package com.oop.taskmanagement.models.contracts.tasks;

import com.oop.taskmanagement.models.contracts.Member;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;

public interface Story extends TaskBase{

    PriorityType getPriority();

    SizeType getSize();

    Member getAssignee();
}
