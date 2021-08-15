package com.ld.usersnews.models;

import org.springframework.security.core.GrantedAuthority;

// Enum Role, содержит возможные варианты ролей пользователей.

public enum Role implements GrantedAuthority {
    USER, AUTHORIZED_AUTHOR, EDITOR, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
