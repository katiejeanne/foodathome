package dev.katiejeanne.foodathome.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    private Household household;

    private String testDisplayName = "Test User";

    private String testUsername = "test_user";

    private String testPassword = "{noop}test";

    private String testEmail = "user@example.com";


    @BeforeEach
    public void setUpUser() {
        user = new User();
        user.setPassword(testPassword);
        user.setUsername(testUsername);
        user.setEmail(testEmail);
        user.setDisplayName(testDisplayName);

        household = new Household();
        household.addUser(user);
        user.setHousehold(household);

    }


    @Test
    public void getAndSetHousehold_whenSet_correctlyReturnsHousehold() {

        assertEquals(household, user.getHousehold());
    }

    @Test
    public void getAndSetUsername_whenSet_returnsUsername() {

        assertEquals(testUsername, user.getUsername());
    }

    @Test
    public void setAndGetPassword_whenSet_correctlyReturnsPassword() {

        assertEquals(testPassword, user.getPassword());
    }


    @Test
    public void setHouseholdRole_defaultToViewer_setsRole() {

        user.setHouseholdRole(HouseholdRole.ROLE_VIEWER);

        assertEquals(HouseholdRole.ROLE_VIEWER, user.getHouseholdRole());
    }

    @Test
    public void setAndGetEmail_whenSet_correctlyReturnsEmail() {

        assertEquals(testEmail, user.getEmail());
    }

    @Test
    public void setAndGetDisplayName_whenSet_correctlyReturnsDisplayName() {

        assertEquals(testDisplayName, user.getDisplayName());
    }

}
