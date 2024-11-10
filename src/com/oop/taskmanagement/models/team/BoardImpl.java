package com.oop.taskmanagement.models.team;

import com.oop.taskmanagement.models.contracts.team.Board;
import com.oop.taskmanagement.utils.ValidationHelpers;


public class BoardImpl extends TeamAssetImpl implements Board {

    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 10;
    private static final String NAME_LENGTH_ERROR = "Board name should be between 5 and 10 symbols.";
    private static final String TYPE_MESSAGE = "%s, type: board.";

    public BoardImpl(String name){
        super(name);
    }

    @Override
    protected void validateName(String name) {
        ValidationHelpers.validateIntRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_LENGTH_ERROR);
    }

    @Override
    public String toString() {
        return String.format(TYPE_MESSAGE, super.toString());
    }
}
