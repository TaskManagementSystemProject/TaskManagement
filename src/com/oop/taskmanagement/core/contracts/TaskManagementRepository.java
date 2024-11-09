package com.oop.taskmanagement.core.contracts;

import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.contracts.team.TeamAsset;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.SizeType;

import java.util.List;

public interface TaskManagementRepository {

    // create
    Team createTeam(String name);
    Board createBoardInTeam(String name, Team team);
    Member createMember(String name);
    Bug createBugInBoard(int id, String title, String description, List<String> stepsToReproduce, PriorityType priority, SeverityType severity, Team team, Board board);
    Feedback createFeedbackInBoard(int id, String title, String description, int rating, Team team, Board board);
    Story createStoryInBoard(int id, String title, String description, PriorityType priority, SizeType size, Team team, Board board);

    // get
    List<Member> getMembers();
    List<Team> getTeams();

    // find
    Member findMemberByName(String name);
    Team findTeamByName(String name);
    TaskBase findTaskById(int id);
    TeamAsset findOwnerOfTask(TaskBase task);

}
