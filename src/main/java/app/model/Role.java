package app.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER;

    Role() {
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
