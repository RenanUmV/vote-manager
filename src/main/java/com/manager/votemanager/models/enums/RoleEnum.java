package com.manager.votemanager.models.enums;


public enum RoleEnum {
    ADMIN("System admin"),
    COOPERATE("Commom Cooperate");

    private String label;
    RoleEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
