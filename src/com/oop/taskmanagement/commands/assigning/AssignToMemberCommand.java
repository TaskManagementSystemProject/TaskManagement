package com.oop.taskmanagement.commands.assigning;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.contracts.team.TeamAsset;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class AssignToMemberCommand implements Command {
    private final static String TASK_ASSIGNED_SUCCESSFULLY = "Task with ID %d assigned to member %s successfully.";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String TASK_ID_PARSING_ERROR = "Task ID must be a number!";

    private final TaskManagementRepository taskManagementRepository;

    public AssignToMemberCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int taskId = ParsingHelpers.tryParseInt(parameters.get(0), TASK_ID_PARSING_ERROR);
        String memberName = parameters.get(1);
        // find the Task which has to be assigned from all the boards + members
        // find the owner of the task a.k.a board/member
        TaskBase taskToAssign = taskManagementRepository.findTaskById(taskId);
        TeamAsset assetToRemoveFrom = taskManagementRepository.findOwnerOfTask(taskToAssign);

        // remove task from current board/ member
        assetToRemoveFrom.removeTask(taskToAssign);
        Member memberForAssignment = taskManagementRepository.findMemberByName(memberName);

        // add task to new member
        memberForAssignment.addTask(taskToAssign);

        return String.format(TASK_ASSIGNED_SUCCESSFULLY, taskId, memberName);
    }
}
