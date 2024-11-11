package com.oop.taskmanagement.utils;

import com.oop.taskmanagement.commands.assigning.AssignToMemberCommand;
import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.core.TaskManagementRepositoryImpl;
import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.tasks.BugImpl;
import com.oop.taskmanagement.models.tasks.FeedbackImpl;
import com.oop.taskmanagement.models.tasks.StoryImpl;
import com.oop.taskmanagement.models.team.BoardImpl;
import com.oop.taskmanagement.models.team.MemberImpl;
import com.oop.taskmanagement.models.team.TeamImpl;

import static com.oop.taskmanagement.utils.Constants.*;

public class ValidInitialization {

    public static TeamImpl initializeValidTeam() {
        return new TeamImpl(VALID_TEAM_NAME);
    }

    public static BoardImpl initializeValidBoard() {
        return new BoardImpl(VALID_BOARD_NAME);
    }

    public static MemberImpl initializeValidMember() {
        return new MemberImpl(VALID_MEMBER_NAME_ONE);
    }

    public static BugImpl initializeValidBug() {
        return new BugImpl(currentId, VALID_TITLE, VALID_DESCRIPTION, VALID_LIST_TO_REPRODUCE, PriorityType.LOW, SeverityType.MINOR);
    }

    public static FeedbackImpl initializeValidFeedback() {
        return new FeedbackImpl(currentId, VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);
    }

    public static StoryImpl initializeValidStory() {
        return new StoryImpl(currentId, VALID_TITLE, VALID_DESCRIPTION, PriorityType.LOW, SizeType.SMALL);
    }

    /**
     * Creates a repository with two teams "Otbor" and "Otbor1" each of them having a board with name "White"
     * Having two members with name "Gosho" in "Otbor" and "Pesho" in "Otbor1"
     * Feedback in "Otbor" with Title "10symb tit", Description: "10symb des" Rating: 5 -> in board "White"
     * Bug in "Otbor" with Title "10symb tit", Description: "10symb des", ListToReproduce: ("Step1", "Step2"), Priority: LOW, Severity: MINOR -> in board "White"
     * Story in "Otbor1" with Title "10symb tit", Description: "10symb des", Priority: LOW, Size: SMALL -> in board "White", team "Otbor1"
     *
     * @return repository that was described
     */
    public static TaskManagementRepository createDummyRepository() {
        TaskManagementRepository repository = new TaskManagementRepositoryImpl();
        Command assignCommand = new AssignToMemberCommand(repository);
        repository.createTeam(VALID_TEAM_NAME);
        repository.createTeam(VALID_TEAM_NAME_TWO);
        Team team = repository.findTeamByName(VALID_TEAM_NAME);
        Team team2 = repository.findTeamByName(VALID_TEAM_NAME_TWO);
        repository.createBoardInTeam(VALID_BOARD_NAME, team);
        repository.createBoardInTeam(VALID_BOARD_NAME, team2);
        repository.createMember(VALID_MEMBER_NAME_ONE);
        repository.createMember(VALID_MEMBER_NAME_TWO);
        Board board = repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME);
        Board board2 = repository.findBoardByTeamName(VALID_BOARD_NAME, VALID_TEAM_NAME_TWO);
        Member member = repository.findMemberByName(VALID_MEMBER_NAME_ONE);
        Member member2 = repository.findMemberByName(VALID_MEMBER_NAME_TWO);
        repository.createFeedbackInBoard(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING, team, board);
        repository.createBugInBoard(VALID_TITLE, VALID_DESCRIPTION, VALID_LIST_TO_REPRODUCE, PriorityType.LOW, SeverityType.MINOR, team, board);
        repository.createStoryInBoard(VALID_TITLE, VALID_DESCRIPTION, PriorityType.LOW, SizeType.SMALL, team2, board2);
        team.addMember(member);
        team2.addMember(member2);
        return repository;
    }
}
