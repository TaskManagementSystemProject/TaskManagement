package com.oop.taskmanagement.models.contracts.team;

import java.util.List;

public interface Team {
    String getName();
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
