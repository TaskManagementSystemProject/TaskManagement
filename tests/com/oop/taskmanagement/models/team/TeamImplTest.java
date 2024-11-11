package com.oop.taskmanagement.models.team;

import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.utils.Constants;
import com.oop.taskmanagement.utils.ValidInitialization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamImplTest {

    @Test
    public void TeamImpl_Should_ImplementTeamInterface(){
        // Arrange, Act
        TeamImpl team = ValidInitialization.initializeValidTeam();
        // Assert
        Assertions.assertTrue(team instanceof Team);
    }

    @Test
    public void constructor_Should_ThrowException_When_NameOutOfLowerBounds() {
        // Arrange, Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new TeamImpl(Constants.INVALID_TEAM_SHORT_NAME)
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_NameOutOfUpperBounds() {
        // Arrange, Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new TeamImpl(Constants.INVALID_TEAM_LONG_NAME)
        );
    }

    @Test
    public void constructor_Should_InitializeMemberList_When_ValidTeamName(){
        // Arrange
        Team team = ValidInitialization.initializeValidTeam();

        // Act, Assert
        Assertions.assertNotNull(team.getMembers());
    }

    @Test
    public void constructor_Should_InitializeBoardList_When_ValidTeamName(){
        // Arrange
        Team team = ValidInitialization.initializeValidTeam();

        // Act, Assert
        Assertions.assertNotNull(team.getBoards());
    }

    @Test
    public void toString_Should_ConvertToStringWithProperFormat(){
        // Arrange
        Team team = ValidInitialization.initializeValidTeam();
        String expectedResult = String.format("Name: %s, type: team.", Constants.VALID_TEAM_NAME);

        // Act
        String actualOutput = team.toString();

        // Assert
        Assertions.assertEquals(expectedResult,actualOutput);
    }

    @Test
    public void addMember_Should_AddMemberToMemberList(){
        // Arrange
        Member member = new MemberImpl("Gosho");
        Team team = ValidInitialization.initializeValidTeam();

        // Act
        team.addMember(member);

        // Assert
        Assertions.assertEquals(1, team.getMembers().size());
    }

    @Test
    public void addBoard_Should_AddBoardToBoardList(){
        // Arrange
        Board board = new BoardImpl("White");
        Team team = ValidInitialization.initializeValidTeam();

        // Act
        team.addBoard(board);

        // Assert
        Assertions.assertEquals(1, team.getBoards().size());
    }

    @Test
    public void removeMember_Should_RemoveMemberFromMemberList_WhenMemberExists(){
        // Arrange
        Member member = new MemberImpl("Gosho");
        Team team = ValidInitialization.initializeValidTeam();
        team.addMember(member);

        // Act
        team.removeMember(member);

        // Assert
        Assertions.assertEquals(0, team.getMembers().size());
    }

    @Test
    public void removeMember_Should_DoNothing_WhenMemberDoesNotExists(){
        // Arrange
        Member member = new MemberImpl("Gosho");
        Member notMember = new MemberImpl("Pesho");
        Team team = ValidInitialization.initializeValidTeam();
        team.addMember(member);

        // Act
        team.removeMember(notMember);

        // Assert
        Assertions.assertEquals(1, team.getMembers().size());
    }

    @Test
    public void removeBoard_Should_RemoveBoardFromBoardList_WhenBoardExists(){
        // Arrange
        Board board = new BoardImpl("White");
        Team team = ValidInitialization.initializeValidTeam();
        team.addBoard(board);

        // Act
        team.removeBoard(board);

        // Assert
        Assertions.assertEquals(0, team.getBoards().size());
    }

    @Test
    public void removeBoard_Should_DoNothing_WhenBoardDoesNotExists(){
        // Arrange
        Board board = new BoardImpl("White");
        Board notBoard = new BoardImpl("Black");
        Team team = ValidInitialization.initializeValidTeam();
        team.addBoard(board);

        // Act
        team.removeBoard(notBoard);

        // Assert
        Assertions.assertEquals(1, team.getBoards().size());
    }

    @Test
    public void getTeamActivity_Should_ReturnMergedAllMembersAndBoardsActivities() {
        // Arrange
        Board board = new BoardImpl("White");
        Member member = new MemberImpl("Gosho");
        Member secondMember = new MemberImpl("Pesho");
        Team team = ValidInitialization.initializeValidTeam();
        team.addBoard(board);
        team.addMember(member);
        team.addMember(secondMember);
        board.logActivity("One");
        board.logActivity("Two");
        member.logActivity("Three");
        member.logActivity("Four");
        secondMember.logActivity("Five");
        List<String> expectedList = new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));

        // Act
        List<String> resultList = team.getTeamActivity();

        // Assert
        Assertions.assertEquals(expectedList, resultList);
    }

    @Test
    public void getMemberActivity_Should_ReturnMergedAllMembersActivities() {
        // Arrange
        Board board = new BoardImpl("White");
        Member member = new MemberImpl("Gosho");
        Member secondMember = new MemberImpl("Pesho");
        Team team = ValidInitialization.initializeValidTeam();
        team.addBoard(board);
        team.addMember(member);
        team.addMember(secondMember);
        board.logActivity("One");
        board.logActivity("Two");
        member.logActivity("Three");
        member.logActivity("Four");
        secondMember.logActivity("Five");
        List<String> expectedList = new ArrayList<>(Arrays.asList("Three", "Four", "Five"));

        // Act
        List<String> resultList = team.getMemberActivity();

        // Assert
        Assertions.assertEquals(expectedList, resultList);
    }

    @Test
    public void getBoardActivity_Should_ReturnMergedAllBoardsActivities() {
        // Arrange
        Board board = new BoardImpl("White");
        Board secondBoard = new BoardImpl("Black");
        Member member = new MemberImpl("Gosho");
        Member secondMember = new MemberImpl("Pesho");
        Member thirdMember = new MemberImpl("Newbie");
        Team team = ValidInitialization.initializeValidTeam();
        team.addBoard(board);
        team.addBoard(secondBoard);
        team.addMember(thirdMember);
        team.addMember(member);
        team.addMember(secondMember);
        board.logActivity("One");
        board.logActivity("Two");
        member.logActivity("Three");
        member.logActivity("Four");
        secondMember.logActivity("Five");
        secondBoard.logActivity("Stop");
        List<String> expectedList = new ArrayList<>(Arrays.asList("One", "Two", "Stop"));

        // Act
        List<String> resultList = team.getBoardActivity();

        // Assert
        Assertions.assertEquals(expectedList, resultList);
    }

}
