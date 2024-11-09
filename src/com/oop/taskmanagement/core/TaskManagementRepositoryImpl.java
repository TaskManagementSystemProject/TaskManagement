package com.oop.taskmanagement.core;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.BoardImpl;
import com.oop.taskmanagement.models.MemberImpl;
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
import com.oop.taskmanagement.models.tasks.BugImpl;
import com.oop.taskmanagement.models.tasks.StoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {


    private final List<Team> teams;
    private final List<Member> members;
    private int nextId;

    public TaskManagementRepositoryImpl() {
        teams = new ArrayList<>();
        members = new ArrayList<>();
        nextId = 1;
    }

    @Override
    public Team createTeam(String name) {
        if (teams.stream()
                .noneMatch(team -> team.getName().equalsIgnoreCase(name))) {
            Team newTeam = new TeamImpl(name);
            teams.add(newTeam);
        }

        throw new InvalidUserInputException(String.format("Team %s already exists.", name));

    }

    @Override
    public Board createBoardInTeam(String name, Team team) {
        if (teams.stream()
                .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(team.getName()))) {
            throw new InvalidUserInputException(String.format("Team %s does not exist.", team.getName()));
        }
        Board newBoard = new BoardImpl(name);
        team.addBoard(newBoard);
        return newBoard;
    }

    @Override
    public Member createMember(String name) {
        if (members.stream()
                .noneMatch(member -> member.getName().equalsIgnoreCase(name))) {
            Member newMember = new MemberImpl(name);
            members.add(newMember);
        }

        throw new InvalidUserInputException(String.format("Member %s already exists.", name));
    }

    @Override
    public Bug createBugInBoard(int id, String title, String description, List<String> stepsToReproduce, PriorityType priority, SeverityType severity, Team team, Board board) {
        if (teams.stream()
                .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(team.getName()))) {
            throw new InvalidUserInputException(String.format("Creating a bug failed! Team %s does not exist.", team.getName()));
        }
        if (team.getBoards().stream()
                .noneMatch(currentBoard -> currentBoard.getName().equalsIgnoreCase(board.getName()))) {
            throw new InvalidUserInputException(String.format("Creating a bug failed! Board %s does not exist.", board.getName()));
        }
        Bug newBug = new BugImpl(nextId, title, stepsToReproduce, priority, severity);
        nextId++;
        board.addTask(newBug);
        return newBug;
    }

    @Override
    public Feedback createFeedbackInBoard(int id, String title, String description, int rating, Team team, Board board) {

        return null;
    }


    // gosho
    @Override
    public Story createStoryInBoard(int id, String title, String description, PriorityType priority, SizeType size, Team team, Board board) {

        if (teams.stream()
                .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(team.getName()))) {
            throw new InvalidUserInputException(String.format("Creating a Story failed! Team %s does not exist.", team.getName()));
        }
        if (team.getBoards().stream()
                .noneMatch(currentBoard -> currentBoard.getName().equalsIgnoreCase(board.getName()))) {
            throw new InvalidUserInputException(String.format("Creating a Story failed! Board %s does not exist.", board.getName()));
        }
        Story newStory = new StoryImpl(nextId, title, description, priority, size);
        nextId++;
        board.addTask(newStory);
        return newStory;
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public Member findMemberByName(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        throw new InvalidUserInputException(String.format("There is no member with name %s", name));
    }

    @Override
    public Team findTeamByName(String name) {

        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        throw new InvalidUserInputException(String.format("There is no team with name %s", name));
    }
}
