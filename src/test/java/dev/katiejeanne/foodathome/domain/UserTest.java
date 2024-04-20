package dev.katiejeanne.foodathome.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void setUsername_newUser_setsUsername() {
        String username = "test_user";
        User user = new User();
        user.setUsername(username);

        assertEquals(username, user.getUsername());
    }

    @Test
    public void setPassword_newUser_setsPassword() {

        String password = "{noop}test";
        User user = new User();
        user.setPassword(password);

        assertEquals(password, user.getPassword());
    }

    @Test
    public void setPassword_existingPassword_setsPassword() {

        String password1 = "{noop}test";
        User user = new User();
        user.setPassword(password1);

        String password2 = "{noop}test2";
        user.setPassword(password2);

        assertEquals(password2, user.getPassword());
    }


    @Test
    public void setHouseholdRole_defaultToViewer_setsRole() {

        User user = new User();
        user.setHouseholdRole(HouseholdRole.ROLE_VIEWER);

        assertEquals(HouseholdRole.ROLE_VIEWER, user.getHouseholdRole());
    }

    @Test
    public void setEmail_setsEmail() {

        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);

        assertEquals(email, user.getEmail());

    }

    @Test
    public void setDisplayName_setsDisplayName() {

        String displayName = "Test User";
        User user = new User();
        user.setDisplayName(displayName);

        assertEquals(displayName, user.getDisplayName());
    }

}
