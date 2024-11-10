package com.oop.taskmanagement.core;

import com.oop.taskmanagement.core.contracts.TaskManagementRepository;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import com.oop.taskmanagement.models.BoardImpl;
import com.oop.taskmanagement.models.MemberImpl;
import com.oop.taskmanagement.models.TeamImpl;
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
import com.oop.taskmanagement.models.tasks.BugImpl;
import com.oop.taskmanagement.models.tasks.FeedbackImpl;
import com.oop.taskmanagement.models.tasks.StoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {


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
        if(teams.stream()
                .noneMatch(team -> team.getName().equalsIgnoreCase(name))){
            Team newTeam = new TeamImpl(name);
            teams.add(newTeam);
        }

        throw new InvalidUserInputException(String.format("Team %s already exists.", name));

    }

    @Override
    public Board createBoardInTeam(String name, Team team) {
        if(teams.stream()
                .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(team.getName()))){
            throw new InvalidUserInputException(String.format("Team %s does not exist.", team.getName()));
        }
        Board newBoard = new BoardImpl(name);
        team.addBoard(newBoard);
        return newBoard;
    }

    @Override
    public Member createMemberInTeam(String name, Team team) {
        if(teams.stream()
                .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(team.getName()))){
            throw new InvalidUserInputException(String.format("Team %s does not exist.", team.getName()));
        }
        Member newMember = new MemberImpl(name);
        team.addMember(newMember);
        return newMember;
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
    public Bug createBugInBoard(String title, String description, List<String> stepsToReproduce, PriorityType priority, SeverityType severity, Team team, Board board) {
        if(teams.stream()
                        .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(team.getName()))){
            throw new InvalidUserInputException(String.format("Creating a bug failed! Team %s does not exist.", team.getName()));
        }
        if(team.getBoards().stream()
                .noneMatch(currentBoard -> currentBoard.getName().equalsIgnoreCase(board.getName()))){
            throw new InvalidUserInputException(String.format("Creating a bug failed! Board %s does not exist.", board.getName()));
        }
        Bug newBug = new BugImpl(nextId, title, description, stepsToReproduce, priority, severity);
        nextId++;
        board.addTask(newBug);
        bugs.add(newBug);
        return newBug;
    }

    @Override
    public Feedback createFeedbackInBoard(String title, String description, int rating, Team team, Board board) {
        if (teams.stream()
                .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(team.getName()))) {
            throw new InvalidUserInputException(String.format("Creating feedback failed! Team %s does not exist.", team.getName()));
        }
        if (team.getBoards().stream()
                .noneMatch(currentBoard -> currentBoard.getName().equalsIgnoreCase(board.getName()))) {
            throw new InvalidUserInputException(String.format("Creating feedback failed! Board %s does not exist.", board.getName()));
        }
        Feedback newFeedback = new FeedbackImpl(nextId, title, description, rating);
        nextId++;
        board.addTask(newFeedback);
        feedbacks.add(newFeedback);
        return newFeedback;
    }


    // gosho
    @Override
    public Story createStoryInBoard(String title, String description, PriorityType priority, SizeType size, Team team, Board board) {

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
        stories.add(newStory);
        return newStory;
    }

    /*
    private <T extends Nameable>  void checkExistence(List<T> elements, String name, String message){
        if (elements.stream()
                .noneMatch(currentTeam -> currentTeam.getName().equalsIgnoreCase(name))) {
            throw new InvalidUserInputException(message);
        }
    }
     */

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
        result.addAll(stories);
        result.addAll(feedbacks);
        return result;
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

    @Override
    public Board findBoardByTeamName(String boardName, String teamName) {
        Team toSearchIn = findTeamByName(teamName);
        for(Board board : toSearchIn.getBoards()){
            if(board.getName().equalsIgnoreCase(boardName)){
                return board;
            }
        }
        throw new InvalidUserInputException(String.format("There is no board %s in team %s", boardName, teamName));
    }


    @Override
    public TaskBase findTaskByIdWithStream(int id) {
        return teams.stream()
                .flatMap(team -> Stream.concat(
                        team.getMembers().stream()
                                .flatMap(member -> member.getTasks().stream()),
                        team.getBoards().stream()
                                .flatMap(board -> board.getTasks().stream())
                ))
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidUserInputException(String.format("Task with %d id does not exist.", id)));

        // create a stream of tasks, by flattening each team to a concatenation of its members stream of tasks and boards stream of tasks.
        // then filter to see only the tasks whose id match
        // knowing they are unique for sure there will be only one match, or 0 if it doesn't exist;
    }

    @Override
    public TeamAsset findOwnerOfTaskWithStream(TaskBase task) {
        return teams.stream()
                .flatMap(team -> Stream.concat(
                        team.getMembers().stream(),
                        team.getBoards().stream()
                )).filter(teamAsset -> teamAsset.getTasks().contains(task))
                .findFirst()
                .orElseThrow(() -> new InvalidUserInputException("Not found"));

        // to be tested.
    }

    private TaskBase getTaskById(List<TaskBase> tasks, int id){
        for(TaskBase task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }
    @Override
    public TaskBase findTaskByIdLooping(int id) {
        TaskBase toReturn = null;
        for(Team team: teams){
            for(Member member : team.getMembers()){
                toReturn = getTaskById(member.getTasks(),id);
            }
            for(Board board : team.getBoards()){
                toReturn = getTaskById(board.getTasks(), id);
            }
        }
        if(toReturn == null){
            throw new InvalidUserInputException(String.format("Task with ID %d was not found.", id));
        }
        return toReturn;
    }

    @Override
    public Member findMemberOfTaskLooping(TaskBase task) {
        for (Team team : teams) {
            for (Member member : team.getMembers()) {
                for (TaskBase currentTask : member.getTasks()) {
                    if (currentTask.getId() == task.getId()) {
                        return member;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Team findTeamOfMemberLooping(String memberName) {
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
        ifNullThrow(result, String.format("Bug with ID %d not found.", id));
        return result;
    }

    @Override
    public Story findStoryById(int id) {
        Story result = findTaskByIdHelper(stories, id);
        ifNullThrow(result, String.format("Story with ID %d not found.", id));
        return result;
    }

    @Override
    public Feedback findFeedbackById(int id) {
        Feedback result = findTaskByIdHelper(feedbacks, id);
        ifNullThrow(result, String.format("Feedback with ID %d not found.", id));
        return result;
    }

    @Override
    public TaskBase findTaskById(int id) {
        return Stream.concat(bugs.stream(), Stream.concat(stories.stream(), feedbacks.stream())) // creating a stream of TaskBase
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidUserInputException(String.format("Task with ID %d not found.", id)));
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
