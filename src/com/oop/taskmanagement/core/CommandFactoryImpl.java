package com.oop.taskmanagement.core;

import com.oop.taskmanagement.commands.adding.AddPersonToTeamCommand;
import com.oop.taskmanagement.commands.assigning.AssignToMemberCommand;
import com.oop.taskmanagement.commands.assigning.UnassignFromMemberCommand;
import com.oop.taskmanagement.commands.changing.*;
import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.creation.*;
import com.oop.taskmanagement.commands.enums.CommandType;
import com.oop.taskmanagement.commands.showing.*;
import com.oop.taskmanagement.core.contracts.CommandFactory;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {
    @Override
    public Command createCommandFromCommandName(String commandTypeAsString, TaskManagementRepository taskManagementRepository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandTypeAsString, CommandType.class);

        // TODO
        return switch (commandType) {
            case CREATEMEMBER -> new CreateMemberCommand();
            case SHOWMEMBERS -> new ShowMembersCommand();
            case SHOWMEMBERACTIVITY -> new ShowMemberActivityCommand();
            case CREATETEAM -> new CreateTeamCommand();
            case SHOWTEAMS -> new ShowTeamsCommand();
            case SHOWTEAMACTIVITY -> new ShowTeamActivityCommand();
            case ADDPERSONTOTEAM -> new AddPersonToTeamCommand();
            case SHOWTEAMMEMBERS -> new ShowTeamMembersCommand();
            case CREATEBOARDINTEAM -> new CreateBoardInTeamCommand();
            case SHOWTEAMBOARDS -> new ShowTeamBoardsCommand();
            case SHOWBOARDACTIVITY -> new ShowBoardActivityCommand();
            case CREATEBUGINBOARD -> new CreateBugInBoardCommand();
            case CREATESTORYINBOARD -> new CreateStoryInBoardCommand();
            case CREATEFEEDBACKINBOARD -> new CreateFeedbackInBoardCommand();
            case CHANGEBUGPRIORITY -> new ChangeBugPriorityCommand();
            case CHANGEBUGSEVERITY -> new ChangeBugSeverityCommand();
            case CHANGEBUGSTATUS -> new ChangeBugStatusCommand();
            case CHANGESTORYPRIORITY -> new ChangeStoryPriorityCommand();
            case CHANGESTORYSIZE -> new ChangeStorySizeCommand();
            case CHANGESTORYSTATUS -> new ChangeStoryStatusCommand();
            case CHANGEFEEDBACKRATING -> new ChangeFeedbackRatingCommand();
            case CHANGEFEEDBACKSTATUS -> new ChangeFeedbackStatusCommand();
            case ASSIGNTOMEMBER -> new AssignToMemberCommand();
            case UNASSINGFROMMEMBER -> new UnassignFromMemberCommand();
        };
    }
}
