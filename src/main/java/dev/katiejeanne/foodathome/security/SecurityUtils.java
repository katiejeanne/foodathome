package dev.katiejeanne.foodathome.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    // Utility method for getting the current user's household ID
    public static Long getCurrentHouseholdId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                    return customUserDetails.getHouseholdId();
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
