package com.oop.taskmanagement.models;

import com.oop.taskmanagement.utils.ValidationHelpers;

public class MemberImpl extends TeamAssetImpl{
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private static final String NAME_LENGTH_ERROR = "Board name should be between 5 and 15 symbols.";

    public MemberImpl(String name) {
        super(name);
    }

    @Override
    protected void validateName(String name) {
        ValidationHelpers.validateIntRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_LENGTH_ERROR);
    }

}
