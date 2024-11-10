package com.oop.taskmanagement.models.team;

import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamImpl implements Team {
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private static final String NAME_LENGTH_ERROR = "Team name should be between 5 and 15 characters.";
    private String name;
    private final List<Board> boards;
    private final List<Member> members;

    public TeamImpl(String name){
        setName(name);
        boards = new ArrayList<>();
        members = new ArrayList<>();
    }


    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateIntRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_LENGTH_ERROR);
        this.name = name;
    }

    @Override
    public List<String> getTeamActivity() {
        return Stream.concat(boards.stream(), members.stream())
                .flatMap(teamAsset -> teamAsset.getActivityHistory().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBoardActivity() {
        return boards.stream()
                .flatMap(teamAsset -> teamAsset.getActivityHistory().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getMemberActivity() {
        return members.stream()
                .flatMap(teamAsset -> teamAsset.getActivityHistory().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {
        boards.add(board);
    }

    @Override
    public void removeBoard(Board board) {
        boards.add(board);
    }

    @Override
    public void addMember(Member member) {
        members.add(member);
    }

    @Override
    public void removeMember(Member member) {
        members.remove(member);
    }

    @Override
    public String toString(){
        return String.format("Name: %s, type: team", getName());
    }
}
