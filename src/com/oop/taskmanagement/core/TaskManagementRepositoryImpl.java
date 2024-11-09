package com.oop.taskmanagement.core;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.models.TeamImpl;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.SizeType;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {


    private final List<Team> teams;
    private final List<Member> members;
    private int nextId;

    public TaskManagementRepositoryImpl(){
        teams = new ArrayList<>();
        members = new ArrayList<>();
        nextId = 1;
    }

    @Override
    public Team createTeam(String name) {
        return null;
    }

    @Override
    public Board createBoardInTeam(String name, Team team) {
        return null;
    }

    @Override
    public Member createMember(String name) {
        return null;
    }

    @Override
    public Bug createBugInBoard(int id, String title, String description, List<String> stepsToReproduce, PriorityType priority, SeverityType severity, Team team, Board board) {
        return null;
    }
    // gosho
    @Override
    public Feedback createFeedbackInBoard(int id, String title, String description, int rating, Team team, Board board) {
        return null;
    }

    @Override
    public Story createStoryInBoard(int id, String title, String description, PriorityType priority, SizeType size, Team team, Board board) {
        return null;
    }

    @Override
    public List<Member> getMembers() {
        return List.of();
    }

    @Override
    public List<Team> getTeams() {
        return List.of();
    }

    @Override
    public Member findMemberByName(String name) {
        return null;
    }

    @Override
    public Team findTeamByName(String name) {
        return null;
    }
}
