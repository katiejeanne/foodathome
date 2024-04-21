package dev.katiejeanne.foodathome.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HouseholdTest {

    private Household household;

    private String testName = "My Household";

    private User user;

    private Category category;

    @BeforeEach
    public void setupHousehold() {
        household = new Household();
        household.setName(testName);

        user = new User();
        household.addUser(user);
        user.setHousehold(household);

        category = new Category();
        household.addCategory(category);
        category.setHousehold(household);
    }


    @Test
    public void getHouseholdName_whenSet_returnsNameCorrectly() {

        String retrievedName = household.getName();

        assertEquals(testName, retrievedName);
    }

    @Test
    public void addAndGetUser_whenSet_returnsAddedUser() {

        assertEquals(1, household.getUsers().size());
        assertTrue(household.getUsers().contains(user));

    }

    @Test
    public void removeUser_whenRemoved_removesUser() {
        household.removeUser(user);

        assertEquals(0, household.getUsers().size());
        assertFalse(household.getUsers().contains(user));
    }

    @Test
    public void addAndGetCategory_whenSet_returnsAddedCategory() {

        assertTrue(household.getCategories().contains(category));
        assertEquals(1, household.getCategories().size());
    }

}
