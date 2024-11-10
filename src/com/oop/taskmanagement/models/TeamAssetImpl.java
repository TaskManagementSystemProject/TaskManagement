package com.oop.taskmanagement.models;

import com.oop.taskmanagement.models.contracts.team.TeamAsset;
import com.oop.taskmanagement.models.contracts.tasks.TaskBase;

import java.util.ArrayList;
import java.util.List;

abstract public class TeamAssetImpl implements TeamAsset {

    private static final String TO_STRING_FORMAT = "Name: %s";
    private String name;
    private final List<TaskBase> tasks;
    private final List<String> activityHistory;

    public TeamAssetImpl(String name){
        setName(name);
        tasks = new ArrayList<>();
        activityHistory = new ArrayList<>();
    }

    abstract protected void validateName(String name);

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name){
        validateName(name);
        this.name = name;
    }
    @Override
    public List<TaskBase> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<String> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public void addTask(TaskBase task) {
        tasks.add(task);
    }

    @Override
    public void removeTask(TaskBase task) {tasks.remove(task);}

    @Override
    public void logActivity(String activityLog) {
        activityHistory.add(activityLog);
    }
    @Override
    public String toString(){
        return String.format(TO_STRING_FORMAT,getName());
    }
}
