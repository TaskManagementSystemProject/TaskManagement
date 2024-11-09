package com.oop.taskmanagement.models.contracts.tasks;

import com.oop.taskmanagement.models.enums.StatusType;

public interface TaskBase {

    abstract void validateStatus (StatusType status);


}
