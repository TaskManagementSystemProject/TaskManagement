package com.oop.taskmanagement.models.contracts.team;

import com.oop.taskmanagement.models.contracts.functionality.Nameable;

import java.util.List;

public interface Team extends Nameable {
    List<String> getTeamActivity();
    List<String> getBoardActivity();
    List<String> getMemberActivity();
    List<Member> getMembers();
    List<Board> getBoards();
    void addBoard(Board board);
    void addMember(Member member);
    void removeBoard(Board board);
    void removeMember(Member member);

}
