package com.oop.taskmanagement.models.contracts.team;

import com.oop.taskmanagement.models.contracts.functionality.Loggable;
import com.oop.taskmanagement.models.contracts.functionality.Nameable;
import com.oop.taskmanagement.models.contracts.functionality.Taskable;

public interface TeamAsset extends Nameable, Loggable, Taskable {
    // to add extra methods if needed
}
