package com.manager.votemanager.models.enums;


import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ADMIN("System admin"),
    COOPERATE("Commom Cooperate");

    private String label;
    RoleEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String getAuthority() {
        return getLabel();
    }
}
