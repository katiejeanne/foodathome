package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    HouseholdRepository householdRepository;

    @BeforeEach
    public void setup() {
        User testUser = new User();
        testUser.setUsername("test_user");
        testUser.setPassword("{noop}test");
        userRepository.save(testUser);
    }

    @Test
    public void findByUsername_WhenUserExists_ReturnsUser() {

        Optional<User> tempUser = userRepository.findByUsername("test_user");

        assertTrue(tempUser.isPresent());
        assertEquals("test_user", tempUser.get().getUsername());
    }

    @Test
    public void findByUsername_WhenUserDoesNotExist_ReturnsEmpty() {

        Optional<User> tempUser = userRepository.findByUsername("not_a_user");

        assertTrue(tempUser.isEmpty());
    }

    @Test
    public void saveUser_WithNewHousehold_SavesBothUserAndHousehold() {

        // Create a user with a household
        long savedUserId = createUserWithHouseholdSaveReturnId();

        // Check both user and household exist
        Optional<User> user = userRepository.findById(savedUserId);
        assertTrue(user.isPresent());
        assertNotNull(user.get().getHousehold());
    }

    @Test
    public void saveUser_WithModifiedHousehold_SavesChangesToUserAndHousehold() {

        // Create a user with a household
        long savedUserId = createUserWithHouseholdSaveReturnId();

        // Retrieve saved user and household
        User user = userRepository.findById(savedUserId).orElseThrow();
        Household household = user.getHousehold();
        assertNotNull(household);

        // Data to modify
        String userName = "test_user";
        String householdName = "Test Household";

        // Modify user and Household
        user.setUsername(userName);
        household.setName(householdName);

        // Save modifications
        userRepository.save(user);
        testEntityManager.flush();
        testEntityManager.clear();

        // Retrieve modified values from the database
        User modifiedUser = userRepository.findById(savedUserId).orElseThrow();

        assertEquals(userName, user.getUsername());
        assertEquals(householdName, user.getHousehold().getName());
    }

    @Test
    public void deleteUser_WIthHouseholdRemoved_deletesOnlyHousehold() {

        // create household with user
        long savedUserId = createUserWithHouseholdSaveReturnId();

        // Detach user from household - to be handled by service layer
        User userToDelete = userRepository.findById(savedUserId).orElseThrow();
        Household household = userToDelete.getHousehold();
        household.


    }

    private long createUserWithHouseholdSaveReturnId() {

        // Set up a user with a household for testing
        User user = new User();
        Household household = new Household();
        user.setHousehold(household);

        userRepository.save(user);

        return user.getId();
    }
}
