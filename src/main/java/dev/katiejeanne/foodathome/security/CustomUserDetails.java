package dev.katiejeanne.foodathome.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUserDetails extends User {

    private Long householdId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, long householdId) {
        super(username, password, authorities);
        this.householdId = householdId;
    }

    public Long getHouseholdId() {
        return householdId;
    }
}
