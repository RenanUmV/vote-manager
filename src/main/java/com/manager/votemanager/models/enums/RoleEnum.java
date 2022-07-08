package com.manager.votemanager.models.enums;


import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ROLE_ADMIN("System admin"),
    ROLE_COOPERATE("Commom Cooperate");

    private final String label;
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
