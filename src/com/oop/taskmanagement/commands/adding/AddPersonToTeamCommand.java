package com.oop.taskmanagement.commands.adding;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class AddPersonToTeamCommand implements Command {

    private final static String MEMBER_ADDED_SUCCESSFULLY = "Member %s added to team %s successfully.";

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final TaskManagementRepository taskManagementRepository;

    public AddPersonToTeamCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String memberName = parameters.get(0);
        String teamName = parameters.get(1);
        Member memberToAdd = taskManagementRepository.findMemberByName(memberName);
        Team teamForAdding = taskManagementRepository.findTeamByName(teamName);
        teamForAdding.addMember(memberToAdd);
        String outputLog = String.format(MEMBER_ADDED_SUCCESSFULLY, memberName, teamName);
        memberToAdd.logActivity(outputLog);

        return outputLog;
    }
}
