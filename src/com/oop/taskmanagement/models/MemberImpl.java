package com.oop.taskmanagement.models;

import com.oop.taskmanagement.models.contracts.team.Member;
import com.oop.taskmanagement.utils.ValidationHelpers;

public class MemberImpl extends TeamAssetImpl implements Member {
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private static final String NAME_LENGTH_ERROR = "Member name should be between 5 and 15 symbols.";
    private static final String TYPE_MESSAGE = "%s, type: member";

    public MemberImpl(String name) {
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
