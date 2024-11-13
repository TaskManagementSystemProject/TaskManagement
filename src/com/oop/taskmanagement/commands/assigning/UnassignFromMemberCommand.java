package com.oop.taskmanagement.commands.assigning;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.utils.ParsingHelpers;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class UnassignFromMemberCommand implements Command {

    private final static String TASK_UNASSIGNED_SUCCESSFULLY = "Task with ID %d unassigned from member %s successfully.";
    private final static String ACTIVITY_LOG_UNASSIGN_MESSAGE = "Unassigned task with ID %d.";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String TASK_ID_PARSING_ERROR = "Task ID must be a number!";
    public static final String SET_MEMBER_DOESNT_HAVE_SET_TASK_EXC_MSG = "Member: %s - doesn't have Task with id: > %d < assigned to him";

    private final TaskManagementRepository taskManagementRepository;

    public UnassignFromMemberCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        int taskId = ParsingHelpers.tryParseInt(parameters.get(0), TASK_ID_PARSING_ERROR);
        String memberName = parameters.get(1);


        // find the Task which has to be unassigned.  checks all boards + members in all teams
        TaskBase taskToUnassign = taskManagementRepository.findTaskById(taskId);
        Member memberToRemoveTaskFrom = taskManagementRepository.findMemberByName(memberName);


       /*   if the given name exists and the id is valid but the task
            is not assigned to the given member throw exception     */
        if (memberToRemoveTaskFrom.getTasks().contains(taskToUnassign)) {
                memberToRemoveTaskFrom.removeTask(taskToUnassign);
                taskToUnassign.setAssigneeName(null);
                memberToRemoveTaskFrom.logActivity(String.format(ACTIVITY_LOG_UNASSIGN_MESSAGE, taskId));
            } else {
                throw new InvalidUserInputException(String.format(SET_MEMBER_DOESNT_HAVE_SET_TASK_EXC_MSG,
                        memberToRemoveTaskFrom.getName(), taskToUnassign.getId()));
            }




        return String.format(TASK_UNASSIGNED_SUCCESSFULLY, taskId, memberName);
    }
}
