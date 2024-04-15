package dev.katiejeanne.foodathome.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void defaultConstructor_givesAdminRole() {
        User user = new User();
        assertEquals(HouseholdRole.ROLE_ADMIN, user.getHouseholdRole());
    }

    @Test
    public void constructorWithUsernamePassword_correctlyInitiatesUser() {
        String username = "test_user";
        String password = "{noop}test";
        User user = new User(username, password);

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(HouseholdRole.ROLE_ADMIN, user.getHouseholdRole());
    }

    @Test
    public void constructorWithUsernamePasswordHousehold_correctlyInitiatesUserAndMirrorsRelationship() {
        Household household = new Household();
        String username = "test_user";
        String password = "{noop}test";
        User user = new User(username, password, household);


        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(household, user.getHousehold());
        assertTrue(household.getUsers().contains(user));
        assertEquals(HouseholdRole.ROLE_ADMIN, user.getHouseholdRole());
    }

    @Test
    public void constructorWithUsernamePasswordHouseholdRole_correctlyInitiatesUserAndMirrorsRelationship() {

        Household household = new Household();
        String username = "test_user";
        String password = "{noop}test";
        User user = new User(username, password, household, HouseholdRole.ROLE_VIEWER);

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(household, user.getHousehold());
        assertTrue(household.getUsers().contains(user));
        assertEquals(HouseholdRole.ROLE_VIEWER, user.getHouseholdRole());

    }

    @Test
    public void getId_multipleUnsavedUsers_idIs0() {

        User user = new User();
        User user2 = new User();

        assertEquals(0, user.getId());
        assertEquals(0, user2.getId());
    }

    @Test
    public void setHousehold_newUser_correctlyMirrorsRelationship() {

        User user = new User();
        Household household = new Household();
        user.setHousehold(household);

        assertEquals(household, user.getHousehold());
        assertTrue(household.getUsers().contains(user));
    }

    @Test
    public void setHousehold_userAssignedToOtherHousehold_raisesError() {

        Household household1 = new Household();
        User user = new User();
        user.setHousehold(household1);

        Household household2 = new Household();

        assertThrows(IllegalArgumentException.class, () -> user.setHousehold(household2));
        assertEquals(household1, user.getHousehold());
        assertTrue(household1.getUsers().contains(user));
        assertFalse(household2.getUsers().contains(user));
    }

    @Test
    public void setHousehold_userAlreadyBelongsToHousehold_passesWithoutDuplication() {

        Household household = new Household();
        User user = new User();
        user.setHousehold(household);
        user.setHousehold(household);

        assertEquals(1, household.getUsers().size());
        assertEquals(household, user.getHousehold());
        assertTrue(household.getUsers().contains(user));
    }

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
    public void setHouseholdRole_viewerToAdmin_setsRole() {

        User user = new User();
        user.setHouseholdRole(HouseholdRole.ROLE_VIEWER);
        user.setHouseholdRole(HouseholdRole.ROLE_ADMIN);

        assertEquals(HouseholdRole.ROLE_ADMIN, user.getHouseholdRole());
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
