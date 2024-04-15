package dev.katiejeanne.foodathome.domain;

import org.springframework.security.core.GrantedAuthority;

public enum HouseholdRole implements GrantedAuthority {

    ROLE_ADMIN,
    ROLE_VIEWER;

    @Override
    public String getAuthority() {
        return name();
    }
}
