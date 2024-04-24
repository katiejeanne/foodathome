package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.HouseholdRole;
import dev.katiejeanne.foodathome.domain.User;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.HouseholdRepository;
import dev.katiejeanne.foodathome.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class HouseholdManagementServiceTests {

    @Autowired
    private HouseholdManagementService householdManagementService;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveNewStandaloneUserAndCreateTheirHousehold_newUser_savesUserAndCreatesHousehold() {
        // Create new user
        User user = new User();

        // Save the user and create the household
        householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(user);
        Long userId = user.getId();

        // Retrieve the saved user and household
        Optional<User> savedUser = userRepository.findById(userId);
        assertTrue(savedUser.isPresent());

        Household newHousehold = savedUser.get().getHousehold();
        assertNotNull(newHousehold);

        assertNotEquals(0, newHousehold.getId());
        assertEquals("My Household Inventory", newHousehold.getName());
    }

    @Test
    public void saveNewStandaloneUser_userAlreadyBelongsToHousehold_throwsException() {

        User user = new User();
        Household household = new Household();
        user.setHousehold(household);

        assertThrows(IllegalArgumentException.class, () -> householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(user));

        // Assert neither household nor user were saved
        assertEquals(0, user.getId());
        assertEquals(0, household.getId());
    }

    @Test
    public void saveNewStandaloneUser_nullUser_throwsException() {
        User user = null;

        assertThrows(IllegalArgumentException.class, () -> householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(user));

        assertNull(user);
    }

    @Test
    public void addNewUserToHousehold_validUser_savesAndAddsUser() {

        // Create entities
        Household household = new Household();
        User user = new User();

        // Add user to Household
        householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN);
        Long householdId = household.getId();

        // Retrieve from repository and verify household
        Optional<Household> retrievedHousehold = householdRepository.findById(householdId);
        assertTrue(retrievedHousehold.isPresent());
        assertEquals(1, retrievedHousehold.get().getUsers().size());

        // Verify user
        User retrievedUser = retrievedHousehold.get().getUsers().get(0);
        assertNotEquals(0, retrievedUser.getId());
        assertEquals(HouseholdRole.ROLE_ADMIN, retrievedUser.getHouseholdRole());

    }

    @Test
    public void addNewUserToHousehold_userAlreadyAssigned_throwsException() {

        // Create households and user
        Household previousHousehold = new Household();
        User user = new User();
        user.setHousehold(previousHousehold);

        Household addingHousehold = new Household();

        // Attempt to add user
        assertThrows(IllegalStateException.class, () -> householdManagementService.addNewUserToHousehold(user, addingHousehold, HouseholdRole.ROLE_ADMIN));

        // Assert no entities were saved to repository
        assertEquals(0, previousHousehold.getId());
        assertEquals(0, addingHousehold.getId());
        assertEquals(0, user.getId());
    }

    @Test
    public void addNewUserToHousehold_userAlreadyBelongsToHousehold_passesWithoutDuplication() {

        // Set up user and household
        User user = new User();
        Household household = new Household();
        user.setHousehold(household);
        household.addUser(user);

        // Add user to a household where it already belongs
        householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN);
        Long householdId = household.getId();

        // All information should be accurate without duplication of user
        Optional<Household> retrievedHousehold = householdRepository.findById(householdId);
        assertTrue(retrievedHousehold.isPresent());

        assertEquals(1, retrievedHousehold.get().getUsers().size());
        User retrivedUser = retrievedHousehold.get().getUsers().get(0);

        assertEquals(HouseholdRole.ROLE_ADMIN, retrivedUser.getHouseholdRole());
        assertEquals(retrievedHousehold.get(), retrivedUser.getHousehold());
        assertNotEquals(0, user.getId());
    }

    @Test
    public void addNewUserToHousehold_nullUser_throwsException() {

        User user = null;
        Household household = new Household();

        // Null user throws exception
        assertThrows(IllegalArgumentException.class, () -> householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN));

        // Household was not saved
        assertEquals(0, household.getId());
    }

    @Test
    public void addNewUserToHousehold_nullHousehold_throwsException() {

        User user = new User();
        Household household = null;

        // Null household throws exception
        assertThrows(IllegalArgumentException.class, () -> householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN));

        // User was not saved
        assertEquals(0, user.getId());
    }

}
