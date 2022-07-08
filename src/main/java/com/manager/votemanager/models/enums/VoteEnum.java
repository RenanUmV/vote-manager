package com.manager.votemanager.models.enums;

public enum VoteEnum {

    YES("YES"),
    NO("NO");

    private final String label;

    VoteEnum(String label){this.label = label;}

    public String getLabel() {
        return label;
    }
}
