package com.oop.taskmanagement.models.contracts.tasks;

import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SizeType;

public interface Story extends TaskBase{

    void changePriority(PriorityType priority);

    void changeSize(SizeType size);

    PriorityType getPriority();

    SizeType getSize();


}
