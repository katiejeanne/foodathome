package dev.katiejeanne.foodathome.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HouseholdTest {

    @Test
    public void argumentConstructor_withNewUser_correctlyCreatesCategoryAndMirrorsRelationship() {
        User user = new User();
        Household testHousehold = new Household("Test Household", user);

        assertTrue(testHousehold.getUsers().contains(user));
        assertEquals(testHousehold, user.getHousehold());
    }

    @Test
    public void argumentConstructor_withPreAssignedUser_ThrowsException() {
        User user = new User();

        // First assign user to a household
        Household testHousehold = new Household("Test Household", user);

        // Now an exception is thrown if a new Household is tentatively made with the same user
        assertThrows(IllegalArgumentException.class, () -> new Household("Test Household 2", user));

    }

    @Test
    public void addUser_withNewUser_CorrectlyMirrorsRelationship() {
        User user = new User();
        Household testHousehold = new Household();

        testHousehold.addUser(user);

        assertTrue(testHousehold.getUsers().contains(user));
        assertEquals(testHousehold, user.getHousehold());
    }

    @Test
    public void addUser_withUserPreAssignedToOtherHousehold_ThrowsException() {

        User user = new User();
        Household testHousehold1 = new Household("Test Household", user);
        Household testHousehold2 = new Household();

        assertThrows(IllegalArgumentException.class, () -> testHousehold2.addUser(user));
        assertFalse(testHousehold2.getUsers().contains(user));
        assertNotEquals(testHousehold2, user.getHousehold());
    }

    @Test
    public void addUser_UserAlreadyAssignedToHousehold_PassesWithNoErrorsOrDuplicates() {

        User user = new User();
        Household testHousehold = new Household("Test Household", user);
        testHousehold.addUser(user);

        assertTrue(testHousehold.getUsers().contains(user));
        assertEquals(1, testHousehold.getUsers().size());
        assertEquals(testHousehold, user.getHousehold());
    }

    @Test
    public void addItem_newItem_CorrectlyMirrorsRelationship() {
        Household testHousehold = new Household();
        Category category = new Category();
        testHousehold.addCategory(category);

        assertTrue(testHousehold.getCategories().contains(category));
        assertEquals(testHousehold, category.getHousehold());
    }

    @Test
    public void addCategory_CategoryPreAssignedToOtherHousehold_CorrectlyThrowsException() {
        Household testHousehold1 = new Household();
        Household testHousehold2 = new Household();
        Category category = new Category();
        testHousehold1.addCategory(category);

        assertThrows(IllegalArgumentException.class, () -> testHousehold2.addCategory(category));
        assertFalse(testHousehold2.getCategories().contains(category));
        assertNotEquals(testHousehold2, category.getHousehold());
    }

    @Test
    public void addCategory_AlreadyAssignedCategory_CorrectlyPassesWithoutErrorOrDuplication() {

        Household testHousehold = new Household();
        Category category = new Category();
        testHousehold.addCategory(category);
        testHousehold.addCategory(category);

        assertTrue(testHousehold.getCategories().contains(category));
        assertEquals(1, testHousehold.getCategories().size());
        assertEquals(testHousehold, category.getHousehold());
    }
}
