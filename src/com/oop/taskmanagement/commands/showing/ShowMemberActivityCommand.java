package com.oop.taskmanagement.commands.showing;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowMemberActivityCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String NO_ACTIVITY_MESSAGE = "There is no activity in member %s yet.";
    private final TaskManagementRepository taskManagementRepository;

    public ShowMemberActivityCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String memberName = parameters.get(0);
        Member member = taskManagementRepository.findMemberByName(memberName);

        String toReturnMessage = String.join(System.lineSeparator(), member.getActivityHistory());
        return toReturnMessage.isEmpty() ? String.format(NO_ACTIVITY_MESSAGE, memberName) : toReturnMessage;
    }
}
