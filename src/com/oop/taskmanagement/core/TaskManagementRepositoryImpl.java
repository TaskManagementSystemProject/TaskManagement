package com.oop.taskmanagement.core;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.team.BoardImpl;
import com.oop.taskmanagement.models.team.MemberImpl;
import com.oop.taskmanagement.models.team.TeamImpl;
import com.oop.taskmanagement.models.contracts.tasks.Bug;
import com.oop.taskmanagement.models.contracts.tasks.Feedback;
import com.oop.taskmanagement.models.contracts.tasks.Story;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;
import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.models.contracts.team.Team;
import com.oop.taskmanagement.models.enums.PriorityType;
import com.oop.taskmanagement.models.enums.SeverityType;
import com.oop.taskmanagement.models.enums.SizeType;
import com.oop.taskmanagement.models.tasks.BugImpl;
import com.oop.taskmanagement.models.tasks.FeedbackImpl;
import com.oop.taskmanagement.models.tasks.StoryImpl;
import com.oop.taskmanagement.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {


    private static final String NAME_ALREADY_IN_USE = "The name %s is already in use.";
    private static final String BOARD_ALREADY_EXISTS_MESSAGE = "Board %s already exists.";
    private static final String MEMBER_NOT_FOUND_MESSAGE = "There is no member with name %s.";
    private static final String TEAM_NOT_FOUND_MESSAGE = "There is no team with name %s.";
    private static final String BOARD_NOT_FOUND_MESSAGE = "There is no board %s in team %s.";
    private static final String BUG_NOT_FOUND_MESSAGE = "Bug with ID %d not found.";
    private static final String STORY_NOT_FOUND_MESSAGE = "Story with ID %d not found.";
    private static final String FEEDBACK_NOT_FOUND_MESSAGE = "Feedback with ID %d not found.";
    private static final String TASK_NOT_FOUND_MESSAGE = "Task with ID %d not found.";

    private final List<Team> teams;
    private final List<Member> members;
    private final List<Bug> bugs;
    private final List<Feedback> feedbacks;
    private final List<Story> stories;
    private int nextId;

    public TaskManagementRepositoryImpl(){
        teams = new ArrayList<>();
        members = new ArrayList<>();
        bugs = new ArrayList<>();
        feedbacks = new ArrayList<>();
        stories = new ArrayList<>();
        nextId = 1;
    }

    @Override
    public void createTeam(String name) {
        ValidationHelpers.validateNameUniqueness(teams, members, name, String.format(NAME_ALREADY_IN_USE, name));
        Team newTeam = new TeamImpl(name);
        teams.add(newTeam);
    }

    @Override
    public Board createBoardInTeam(String name, Team team) {

        if(team.getBoards().stream()
                .anyMatch(board -> board.getName().equalsIgnoreCase(name))){
            throw new InvalidUserInputException(String.format(BOARD_ALREADY_EXISTS_MESSAGE, name));
        }
        Board newBoard = new BoardImpl(name);
        team.addBoard(newBoard);
        return newBoard;
    }

    @Override
    public void createMember(String name) {
        ValidationHelpers.validateNameUniqueness(members, teams, name, String.format(NAME_ALREADY_IN_USE, name));
        Member newMember = new MemberImpl(name);
        members.add(newMember);
    }

    @Override
    public Bug createBugInBoard(String title, String description, List<String> stepsToReproduce, PriorityType priority, SeverityType severity, Team team, Board board) {

        Bug newBug = new BugImpl(nextId, title, description, stepsToReproduce, priority, severity);
        nextId++;
        board.addTask(newBug);
        bugs.add(newBug);
        return newBug;
    }

    @Override
    public Feedback createFeedbackInBoard(String title, String description, int rating, Team team, Board board) {
        Feedback newFeedback = new FeedbackImpl(nextId, title, description, rating);
        nextId++;
        board.addTask(newFeedback);
        feedbacks.add(newFeedback);
        return newFeedback;
    }


    @Override
    public Story createStoryInBoard(String title, String description, PriorityType priority, SizeType size, Team team, Board board) {

        Story newStory = new StoryImpl(nextId, title, description, priority, size);
        nextId++;
        board.addTask(newStory);
        stories.add(newStory);
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
    public List<Bug> getBugs(){
        return new ArrayList<>(bugs);
    }

    @Override
    public List<Story> getStories(){
        return new ArrayList<>(stories);
    }

    @Override
    public List<Feedback> getFeedbacks(){
        return new ArrayList<>(feedbacks);
    }

    public List<TaskBase> getAllTasks() {
        List<TaskBase> result = new ArrayList<>(bugs);
        result.addAll(feedbacks);
        result.addAll(stories);
        return result;
    }

    @Override
    public Member findMemberByName(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        throw new InvalidUserInputException(String.format(MEMBER_NOT_FOUND_MESSAGE, name));
    }

    @Override
    public Team findTeamByName(String name) {

        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        throw new InvalidUserInputException(String.format(TEAM_NOT_FOUND_MESSAGE, name));
    }

    @Override
    public Board findBoardByTeamName(String boardName, String teamName) {
        Team toSearchIn = findTeamByName(teamName);
        for(Board board : toSearchIn.getBoards()){
            if(board.getName().equalsIgnoreCase(boardName)){
                return board;
            }
        }
        throw new InvalidUserInputException(String.format(BOARD_NOT_FOUND_MESSAGE, boardName, teamName));
    }

    @Override
    public Member findMemberByTask(TaskBase task) {
        String memberName = task.getAssigneeName();
        return memberName == null ? null : findMemberByName(memberName);
    }

    @Override
    public Team findTeamByMemberName(String memberName) {
        for(Team team: teams){
            for(Member member : team.getMembers()){
                if(member.getName().equalsIgnoreCase(memberName)){
                    return team;
                }
            }
        }
        return null;
    }

    @Override
    public Bug findBugById(int id) {
        Bug result = findTaskByIdHelper(bugs, id);
        ifNullThrow(result, String.format(BUG_NOT_FOUND_MESSAGE, id));
        return result;
    }

    @Override
    public Story findStoryById(int id) {
        Story result = findTaskByIdHelper(stories, id);
        ifNullThrow(result, String.format(STORY_NOT_FOUND_MESSAGE, id));
        return result;
    }

    @Override
    public Feedback findFeedbackById(int id) {
        Feedback result = findTaskByIdHelper(feedbacks, id);
        ifNullThrow(result, String.format(FEEDBACK_NOT_FOUND_MESSAGE, id));
        return result;
    }

    @Override
    public TaskBase findTaskById(int id) {
        return Stream.concat(bugs.stream(), Stream.concat(stories.stream(), feedbacks.stream())) // creating a stream of TaskBase
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidUserInputException(String.format(TASK_NOT_FOUND_MESSAGE, id)));
    }

    private <T extends TaskBase> T findTaskByIdHelper(List<T> elements, int id){
        return elements.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private <T>  void ifNullThrow(T element, String message){
        if(element == null){
            throw new InvalidUserInputException(message);
        }
    }
}
