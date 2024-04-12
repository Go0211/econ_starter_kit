package com.econstarterkit.econstarterkit.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String userRoleText;

    UserRole(String userRoleText) {
        this.userRoleText = userRoleText;
    }

    public static UserRole fromString(String userRoleText) {
        return UserRole.valueOf(userRoleText.toUpperCase());
    }

    @JsonValue
    public String getText() {
        return userRoleText;
    }
}
