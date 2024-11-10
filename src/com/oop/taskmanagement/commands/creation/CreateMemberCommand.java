package com.oop.taskmanagement.commands.creation;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateMemberCommand implements Command {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private static final String CREATE_MEMBER_SUCCESS_MESSAGE = "Member %s created successfully in team %s";

    private final TaskManagementRepository taskManagementRepository;

    public CreateMemberCommand(TaskManagementRepository taskManagementRepository) {
        this.taskManagementRepository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String memberName = parameters.get(0);
        String teamName = parameters.get(1);
        Team teamToAddMemberIn = taskManagementRepository.findTeamByName(teamName);
        Member newMember = taskManagementRepository.createMemberInTeam(memberName, teamToAddMemberIn);
        newMember.logActivity(String.format("Member %s added to team %s", memberName, teamName));

        return String.format(CREATE_MEMBER_SUCCESS_MESSAGE, memberName, teamName);
    }
}
