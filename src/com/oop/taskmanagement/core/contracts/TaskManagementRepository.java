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
    void createTeam(String name);
    Board createBoardInTeam(String name, Team team);
    Member createMemberInTeam(String name, Team team);
    Member createMember(String name);
    Bug createBugInBoard(String title, String description, List<String> stepsToReproduce, PriorityType priority, SeverityType severity, Team team, Board board);
    Feedback createFeedbackInBoard( String title, String description, int rating, Team team, Board board);
    Story createStoryInBoard(String title, String description, PriorityType priority, SizeType size, Team team, Board board);

    // get
    List<Team> getTeams();
    List<Member> getMembers();
    List<Bug> getBugs();
    List<Story> getStories();
    List<Feedback> getFeedbacks();
    List<TaskBase> getAllTasks();


    // find
    Member findMemberByName(String name);
    Team findTeamByName(String name);
    Board findBoardByTeamName(String boardName, String teamName);
    TaskBase findTaskByIdWithStream(int id);
    TeamAsset findOwnerOfTaskWithStream(TaskBase task);
    TaskBase findTaskByIdLooping(int id);
    Member findMemberOfTaskLooping(TaskBase task);
    Team findTeamOfMemberLooping(String memberName);


    // new find logic
    Bug findBugById(int id);
    Story findStoryById(int id);
    Feedback findFeedbackById(int id);
    TaskBase findTaskById(int id);
}
