package com.oop.taskmanagement.core;

import com.oop.taskmanagement.commands.adding.AddCommentToTaskCommand;
import com.oop.taskmanagement.commands.adding.AddPersonToTeamCommand;
import com.oop.taskmanagement.commands.assigning.AssignToMemberCommand;
import com.oop.taskmanagement.commands.assigning.UnassignFromMemberCommand;
import com.oop.taskmanagement.commands.changing.*;
import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.commands.creation.*;
import com.oop.taskmanagement.commands.enums.CommandType;
import com.oop.taskmanagement.commands.listing.*;
import com.oop.taskmanagement.commands.mainmenu.ShowMainMenuCommand;
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
            case CREATEMEMBER -> new CreateMemberCommand(taskManagementRepository);
            case SHOWMEMBERS -> new ShowMembersCommand(taskManagementRepository);
            case SHOWMEMBERACTIVITY -> new ShowMemberActivityCommand(taskManagementRepository);
            case CREATETEAM -> new CreateTeamCommand(taskManagementRepository);
            case SHOWTEAMS -> new ShowTeamsCommand(taskManagementRepository);
            case SHOWTEAMACTIVITY -> new ShowTeamActivityCommand(taskManagementRepository);
            case ADDPERSONTOTEAM -> new AddPersonToTeamCommand(taskManagementRepository);
            case ADDCOMMENTTOTASK -> new AddCommentToTaskCommand(taskManagementRepository);
            case SHOWTEAMMEMBERS -> new ShowTeamMembersCommand(taskManagementRepository);
            case CREATEBOARDINTEAM -> new CreateBoardInTeamCommand(taskManagementRepository);
            case SHOWTEAMBOARDS -> new ShowTeamBoardsCommand(taskManagementRepository);
            case SHOWBOARDACTIVITY -> new ShowBoardActivityCommand(taskManagementRepository);
            case CREATEBUGINBOARD -> new CreateBugInBoardCommand(taskManagementRepository);
            case CREATESTORYINBOARD -> new CreateStoryInBoardCommand(taskManagementRepository);
            case CREATEFEEDBACKINBOARD -> new CreateFeedbackInBoardCommand(taskManagementRepository);
            case CHANGEBUGPRIORITY -> new ChangeBugPriorityCommand(taskManagementRepository);
            case CHANGEBUGSEVERITY -> new ChangeBugSeverityCommand(taskManagementRepository);
            case CHANGEBUGSTATUS -> new ChangeBugStatusCommand(taskManagementRepository);
            case CHANGESTORYPRIORITY -> new ChangeStoryPriorityCommand(taskManagementRepository);
            case CHANGESTORYSIZE -> new ChangeStorySizeCommand(taskManagementRepository);
            case CHANGESTORYSTATUS -> new ChangeStoryStatusCommand(taskManagementRepository);
            case CHANGEFEEDBACKRATING -> new ChangeFeedbackRatingCommand(taskManagementRepository);
            case CHANGEFEEDBACKSTATUS -> new ChangeFeedbackStatusCommand(taskManagementRepository);
            case ASSIGNTOMEMBER -> new AssignToMemberCommand(taskManagementRepository);
            case UNASSIGNFROMMEMBER -> new UnassignFromMemberCommand(taskManagementRepository);
            case LISTTASKS -> new ListTasksCommand(taskManagementRepository);
            case LISTASSIGNEDTASKS -> new ListAssignedTasksCommand(taskManagementRepository);
            case LISTBUGS -> new ListBugsCommand(taskManagementRepository);
            case LISTFEEDBACKS -> new ListFeedbacksCommand(taskManagementRepository);
            case LISTSTORIES -> new ListStoriesCommand(taskManagementRepository);
            case SHOWMAINMENU -> new ShowMainMenuCommand();
        };
    }
}
