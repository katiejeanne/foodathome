package dev.katiejeanne.foodathome.security;


import dev.katiejeanne.foodathome.domain.HouseholdRole;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomUserDetailsTest {

    @Test
    public void customUserDetailsConstructor_withValidUserData_ReturnsValidObject() {

        HouseholdRole role = HouseholdRole.ROLE_ADMIN;

        CustomUserDetails testUserDetails = new CustomUserDetails("test-user", "{noop}test", Collections.singleton(role), 1);

        assertEquals("test-user", testUserDetails.getUsername());
        assertEquals("{noop}test", testUserDetails.getPassword());
        assertTrue(testUserDetails.getAuthorities().contains(role), "Authorities should contain the assigned role.");
        assertEquals(1, testUserDetails.getAuthorities().size(), "There should be exactly one authority.");
        assertEquals(1, testUserDetails.getHouseholdId());

    }
}
